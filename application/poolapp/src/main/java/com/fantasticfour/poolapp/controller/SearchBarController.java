package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.domain.ZipData;
import com.fantasticfour.poolapp.repository.PoolRepository;
import com.fantasticfour.poolapp.repository.UserRepository;
import com.fantasticfour.poolapp.repository.ZipDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class SearchBarController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PoolRepository poolRepository;

    @Autowired
    private ZipDataRepository zipDataRepository;

    @GetMapping("/api/searchbar")
//    List<Map<String, Object>>
    public ResponseEntity<List<Object>> searchBar (@RequestParam String filter, @RequestParam String value) {
        //http://localhost:8080/api/searchbar?filter=[columnNameInDB]&value=[stringTobesearched]

//        Map<String, Object>
        List<Object> results = new ArrayList<>();
        //value to be searched in db
        String regex = "^" + value;

        switch (filter) {
            case "name":
                matchByUserName(regex, results);
                break;
            case "city":
                matchByStartCity(regex, results);
                break;
            case "startZip":
//                System.out.println("start zip to be implemented");
                matchByStartZip(value, results);
                break;
            case "endZip":
                matchByEndZip(value, results);
                break;
            case "zip":
                loadZipData();
                break;
            case "modify":
                fixlonglat(regex,results);
         }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

//    List<Map<String, Object>>
    private void matchByUserName (String regex, List<Object> results) {
        List<User> userList =  userRepository.findByNameMatchesRegex(regex);
        results.addAll(userList);
        }


    private void matchByStartCity (String regex, List<Object> results) {
        List<Pool> poolList =  poolRepository.findByCityMatchesRegex(regex);

        //default behaviour if there are no matches
        if (poolList.isEmpty()) {
            regex = "^san Francisco";
            poolList = poolRepository.findByCityMatchesRegex(regex);
        }else {
            results.addAll(poolList);
        }
    }

    private void matchByStartZip (String value, List<Object> results) {
      Optional<ZipData> zipOptionalEnity=  zipDataRepository.findById(value);

        ZipData zipDataEntity;
        if (zipOptionalEnity.isPresent()) {
            zipDataEntity = zipOptionalEnity.get();
            // Now you can manipulate the entity object as needed.
            // Distance = 20;
  //        Radius = 6371; // earth radius in km
//          Radius = 3958; // earth radius in miles
//          Lat = x.y; // Your position latitude
//          Lon = x.y; // Your position longitude
            double longitude = zipDataEntity.getLongitude();
            double latitude = zipDataEntity.getLatitude();
            double radius = 3958.00; //earth radius in miles
            double distance = 5;

            List<Pool> poolList =  poolRepository.findWithinDistanceUsingStartZip(latitude, radius, longitude, distance);

            // default behaviour if there are no matches
            if (poolList.isEmpty()) {
                //returns san francisco entries to results list.
                defaaultResultsReturned(results);
            }else {
                //send back results
                results.addAll(poolList);
            }
        } else {
            //empyt value or no matching zip
            defaaultResultsReturned(results);
        }
    }

    private void matchByEndZip (String value, List<Object> results) {
        Optional<ZipData> zipOptionalEnity=  zipDataRepository.findById(value);

        ZipData zipDataEntity;
        if (zipOptionalEnity.isPresent()) {
            zipDataEntity = zipOptionalEnity.get();
            // Now you can manipulate the entity object as needed.

            //        $iDistance = 20;
//        $iRadius = 6371; // earth radius in km
//        $iRadius = 3958; // earth radius in miles
//        $fLat = x.y; // Your position latitude
//        $fLon = x.y; // Your position longitude
            double longitude = zipDataEntity.getLongitude();
            double latitude = zipDataEntity.getLatitude();
            double radius = 3958.00; //earth radius in miles
            double distance = 5;

            System.out.println("zip: " + zipDataEntity.getZipCode() + " " + "long: " + longitude+ " " + "lat: " + latitude);

            List<Pool> poolList =  poolRepository.findWithinDistanceUsingEndZip(latitude, radius, longitude, distance);

            // default behaviour if there are no matches
            if (poolList.isEmpty()) {
                //returns san francisco entries to results list.
                defaaultResultsReturned(results);
            }else {
                //send back results
                results.addAll(poolList);
            }
        } else {
            defaaultResultsReturned(results);
        }
    }

    private void loadZipData () {
        // File to read
        String fileName = "application/poolapp/src/main/java/com/fantasticfour/poolapp/config/zipData.txt"; // Replace with your file path

        try {
            // Open the file using FileReader wrapped in a BufferedReader
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            String line;  // To hold each line read from the file

            // Read the file line by line
            while ((line = bufferedReader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split the line by comma
                String[] fields = line.split(",");

                // Ensure there are enough fields before parsing
                if (fields.length < 3) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                // Parse the fields
                String postalCode = fields[0].trim();
                double latitude = Double.parseDouble(fields[1].trim());
                double longitude = Double.parseDouble(fields[2].trim());

                ZipData zipData = new ZipData();
                zipData.setZipCode(postalCode);
                zipData.setLatitude(latitude);
                zipData.setLongitude(longitude);

                zipDataRepository.save(zipData);

                // Display the parsed data (or store it as needed)
                System.out.println("Postal Code: " + postalCode + ", Latitude: " + latitude + ", Longitude: " + longitude);
            }

            // Close the BufferedReader
            bufferedReader.close();
        } catch (IOException e) {
            // Handle the exception
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void fixlonglat (String value, List<Object> results) {
        List<Pool> poollist = poolRepository.findAll();

        for (Pool pool: poollist) {
            Optional<ZipData> startzip=  zipDataRepository.findById(pool.getStartZip());
            Optional<ZipData> endzip=  zipDataRepository.findById(pool.getEndZip());
            if(startzip.isPresent()) {
                ZipData zipData = startzip.get();
                pool.setStartLatitude(zipData.getLatitude());
                pool.setStartLongitude(zipData.getLongitude());
            }
            if(endzip.isPresent()) {
                ZipData zipData =endzip.get();
                pool.setEndLatitude(zipData.getLatitude());
                pool.setEndLongitude(zipData.getLongitude());
            }

            poolRepository.saveAndFlush(pool);
        }
    }


    private void defaaultResultsReturned (List<Object> results) {
        String regex = "^san Francisco";
        List<Pool> poolList =  poolRepository.findByCityMatchesRegex(regex);
        results.addAll(poolList);
    }

}

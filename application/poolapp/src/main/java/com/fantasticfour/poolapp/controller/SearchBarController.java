package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.domain.ZipData;
import com.fantasticfour.poolapp.repository.PoolRepository;
import com.fantasticfour.poolapp.repository.UserRepository;
import com.fantasticfour.poolapp.repository.ZipDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Map<String, Object>> searchBar (@RequestParam String filter, @RequestParam String value) {
        //http://localhost:8080/api/searchbar?filter=[columnNameInDB]&value=[stringTobesearched]

        List<Map<String, Object>> results = new ArrayList<>();
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
                System.out.println("start zip to be implemented");
                break;
            case "endZip":
                System.out.println("end zip to be implemented");
                break;
            case "zip":
                loadZipData();
                break;
         }

        return results;
    }

    private void matchByUserName (String regex, List<Map<String, Object>> results) {
        List<User> userList =  userRepository.findByNameMatchesRegex(regex);

        //hash map for each user that will be returned in a list
        for(User user : userList) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("User", user);
            results.add(userMap);
        }
    }

    private void matchByStartCity (String regex, List<Map<String, Object>> results) {
        List<Pool> poolList =  poolRepository.findByCityMatchesRegex(regex);

        //default behaviour if there are no matches
        if (poolList.isEmpty()) {
            regex = "^san Francisco";
            poolList = poolRepository.findByCityMatchesRegex(regex);
        }

        for(Pool pool: poolList) {
            Map<String, Object> poolMap = new HashMap<>();
            poolMap.put("Pool", pool);
            results.add(poolMap);
        }
    }

    private void matchByStartZip (String regex, List<Map<String, Object>> results) {
        //TODO:
    }

    private void matchByEndZip (String regex, List<Map<String, Object>> results) {
        //TODO:
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


}

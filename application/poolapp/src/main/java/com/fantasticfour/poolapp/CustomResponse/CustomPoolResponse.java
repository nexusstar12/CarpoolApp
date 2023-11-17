package com.fantasticfour.poolapp.CustomResponse;

import com.fantasticfour.poolapp.domain.Driver;
import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.DriverRepository;
import com.fantasticfour.poolapp.repository.PassengerRepository;
import com.fantasticfour.poolapp.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomPoolResponse {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PassengerRepository passengerRepository;


    public  List<PoolResponse> buildPoolResponseList (List<Pool> pools) {
        List<PoolResponse> poolResponseList = new ArrayList<>();

        for (Pool pool : pools) {
            PoolResponse poolResponse = new PoolResponse();

            poolResponse.setPoolId(pool.getPoolId());
            poolResponse.setStartLocation(pool.getStartLocation());
            poolResponse.setEndLocation(pool.getEndLocation());
            poolResponse.setStartTime(pool.getStartTime());
            poolResponse.setDescription(pool.getDescription());
            poolResponse.setCrewCreated(pool.isCrewCreated());


            //get the driver from profile -> userid -> driver
            CustomDriver customDriver = new CustomDriver();

            int creatorProfileId = pool.getCreator().getProfileId();
            //add driver to response
            profileRepository.findProfileByProfileId(creatorProfileId)
                    .ifPresentOrElse(driverProfile -> {

                        User userDriver = driverProfile.getUserId();

                        driverRepository.findByUser_UserId(userDriver.getUserId())
                                .ifPresentOrElse(driver -> {
                                    customDriver.setDriverId(driver.getDriverId());
                                    customDriver.setName(userDriver.getName());
                                    poolResponse.setDriver(customDriver);
                                }, () -> {
                                    System.out.println("creator profile id = " + creatorProfileId);
                                } );

                    }, () -> {
                        System.out.println("creator profile id = " + creatorProfileId);
                    } );

            //get passengers

            List<Profile> memberList = new ArrayList<>();
            memberList.add(pool.getMember1());
            memberList.add(pool.getMember2());
            memberList.add(pool.getMember3());

            for (Profile member: memberList) {
                CustomPassenger customPassenger = new CustomPassenger();
                if (member != null) {
                    User userPassenger = member.getUserId();
                    passengerRepository.findPassengerByUserId(userPassenger.getUserId())
                            .ifPresentOrElse(passenger -> {
                                customPassenger.setPassengerId(passenger.getPassengerId());
                                customPassenger.setName(userPassenger.getName());
                                poolResponse.addPassenger(customPassenger);
                            }, () -> {
                                System.out.println("passenger userId not found = " + userPassenger.getUserId()
                                + " poolid: " + pool.getPoolId());
                            } );
                }
            }
             poolResponseList.add(poolResponse);
        }//End For Each
        return poolResponseList;
    }
}

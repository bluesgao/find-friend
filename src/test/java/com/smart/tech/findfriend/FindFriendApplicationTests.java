package com.smart.tech.findfriend;

import com.smart.tech.findfriend.domain.po.UserLocation;
import com.smart.tech.findfriend.service.LocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FindFriendApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(FindFriendApplicationTests.class);

    @Autowired
    private LocationService locationService;

    @Test
    public void saveUserLocationTest() {
        for (int i = 0; i < 100000; i++) {
            locationService.saveUserLocation(new UserLocation(randomDouble(-90, 90), randomDouble(-45, 45), "u000" + i, "smart" + i));
        }
    }

    @Test
    public void nearByTest(){
        UserLocation userLocation = new UserLocation(12.0, 40.0, null, null);
        locationService.getNearbyUserByRadius(userLocation, 100L);
    }

    @Test
    public void randomUserTest(){
        locationService.getRandomUser();
    }

    private double randomDouble(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

}

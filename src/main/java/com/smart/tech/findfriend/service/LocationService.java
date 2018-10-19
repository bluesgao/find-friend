package com.smart.tech.findfriend.service;

import com.smart.tech.findfriend.domain.po.Coordinate;
import com.smart.tech.findfriend.domain.po.UserLocation;

import java.util.List;

public interface LocationService {
    Long saveUserLocation(UserLocation userLocation);
    List<UserLocation> getNearbyUserByRadius(Coordinate coordinate, Long distance);
    UserLocation getRandomUser();
}

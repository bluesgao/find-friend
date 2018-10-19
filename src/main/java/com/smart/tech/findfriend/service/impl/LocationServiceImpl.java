package com.smart.tech.findfriend.service.impl;

import com.alibaba.fastjson.JSON;
import com.smart.tech.findfriend.domain.po.UserLocation;
import com.smart.tech.findfriend.service.LocationService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Slf4j
@Service("locationService")
public class LocationServiceImpl implements LocationService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户位置信息缓存key值，p1-城市编号
     **/
    private static final String USER_LOCATION_KEY = "user_location_%s";


    @Override
    public Long saveUserLocation(UserLocation userLocation) {
        log.info("saveUserLocation input:{}", JSON.toJSONString(userLocation));

        String key = String.format(USER_LOCATION_KEY, "001");
        Point point = new Point(userLocation.getCoordinate().getLongitude(), userLocation.getCoordinate().getLatitude());
        Long ret = redisTemplate.opsForGeo().add(key, point, JSON.toJSONString(userLocation.getUser()));

        log.info("saveUserLocation output:{}", ret);
        return ret;
    }

    @Override
    public List<UserLocation> getNearbyUserByRadius(UserLocation userLocation, Long radius) {
        log.info("getNearbyUserByRadius input:{},{}", JSON.toJSONString(userLocation), radius);

        String key = String.format(USER_LOCATION_KEY, "001");
        Point center = new Point(userLocation.getCoordinate().getLongitude(), userLocation.getCoordinate().getLatitude());
        Distance distance = new Distance(radius, Metrics.MILES);
        Circle circle = new Circle(center, distance);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeCoordinates().includeDistance();

        GeoResults<RedisGeoCommands.GeoLocation<String>> getResult = redisTemplate.opsForGeo().radius(key, circle, args);

        log.info("getNearbyUserByRadius output:{}", JSON.toJSONString(getResult));
        return null;
    }
}

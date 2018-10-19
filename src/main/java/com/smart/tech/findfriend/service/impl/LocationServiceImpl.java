package com.smart.tech.findfriend.service.impl;

import com.alibaba.fastjson.JSON;
import com.smart.tech.findfriend.domain.po.Coordinate;
import com.smart.tech.findfriend.domain.po.User;
import com.smart.tech.findfriend.domain.po.UserLocation;
import com.smart.tech.findfriend.service.LocationService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

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
    /**
     * 保存用户位置信息
     */
    public Long saveUserLocation(UserLocation userLocation) {
        log.info("saveUserLocation input:{}", JSON.toJSONString(userLocation));

        String key = String.format(USER_LOCATION_KEY, "001");
        Point point = new Point(userLocation.getCoordinate().getLongitude(), userLocation.getCoordinate().getLatitude());
        Long ret = redisTemplate.opsForGeo().add(key, point, JSON.toJSONString(userLocation.getUser()));

        log.info("saveUserLocation output:{}", ret);
        return ret;
    }

    @Override
    /**
     * 随机生成经纬度作为圆心，100m半径内的所有用户，取第一条
     */
    public UserLocation getRandomUser() {
        log.info("getRandomUser input:{},{}");

        String key = String.format(USER_LOCATION_KEY, "001");
        //圆点
        Point center = new Point(randomDouble(-90, 90), randomDouble(-45, 45));
        //半径
        Distance radius = new Distance(100L, Metrics.MILES);
        //圆
        Circle circle = new Circle(center, radius);
        //命令附加参数
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeCoordinates().includeDistance();

        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = redisTemplate.opsForGeo().radius(key, circle, args);
        log.info("getRandomUser geoResults:{}", JSON.toJSONString(geoResults));

        if (geoResults != null && !CollectionUtils.isEmpty(geoResults.getContent())) {
            //用户信息
            String userStr = geoResults.getContent().get(0).getContent().getName();
            //坐标信息
            Point point = geoResults.getContent().get(0).getContent().getPoint();

            if (!StringUtils.isEmpty(userStr) && point != null) {
                User user = JSON.parseObject(userStr, User.class);
                Coordinate coordinate = new Coordinate(point.getX(), point.getY());
                UserLocation userLocation = new UserLocation(coordinate, user);
                log.info("getRandomUser userLocation:{}", JSON.toJSONString(userLocation));
                return userLocation;
            }
        }

        return null;
    }

    @Override
    /**
     * 以当前用户坐标为圆心，distance为半径内的所有用户
     */
    public List<UserLocation> getNearbyUserByRadius(Coordinate coordinate, Long distance) {
        log.info("getNearbyUserByRadius input:{},{}", JSON.toJSONString(coordinate), distance);

        String key = String.format(USER_LOCATION_KEY, "001");
        //圆点
        Point center = new Point(coordinate.getLongitude(), coordinate.getLatitude());
        //半径
        Distance radius = new Distance(distance, Metrics.MILES);
        //圆
        Circle circle = new Circle(center, radius);
        //命令附加参数
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeCoordinates().includeDistance();

        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResult = redisTemplate.opsForGeo().radius(key, circle, args);

        log.info("getNearbyUserByRadius output:{}", JSON.toJSONString(geoResult));
        return null;
    }

    private double randomDouble(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }
}

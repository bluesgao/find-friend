package com.smart.tech.findfriend.controller;

import com.alibaba.fastjson.JSON;
import com.smart.tech.findfriend.domain.Result;
import com.smart.tech.findfriend.domain.po.Coordinate;
import com.smart.tech.findfriend.domain.po.UserLocation;
import com.smart.tech.findfriend.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/nearby.json", method = RequestMethod.POST)
    public Result<List<UserLocation>> nearBy(@Valid Coordinate coordinate, Long distance) {
        log.info("nearBy input:{},{}", JSON.toJSONString(coordinate), distance);
        //距离为空或者为0时，默认距离500m
        if (distance == null || distance.equals(0)) {
            distance = 100L;
        }
        List<UserLocation> userLocations = locationService.getNearbyUserByRadius(coordinate, distance);
        return new Result(true, "查询成功", userLocations);
    }
}

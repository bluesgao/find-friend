package com.smart.tech.findfriend.domain.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLocation implements Serializable {
    //坐标信息
    private Coordinate coordinate;
    //用户信息
    private User user;

    public UserLocation(Coordinate coordinate, User user) {
        this.coordinate = coordinate;
        this.user = user;
    }

    public UserLocation() {
    }

    public UserLocation(Double x, Double y, String userId, String userName) {
        this(new Coordinate(x, y), new User(userId, userName));
    }
}

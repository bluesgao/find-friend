package com.smart.tech.findfriend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Serializable {
    //经度
    private Double longitude;
    //纬度
    private Double latitude;
    //用户信息
    private User user;
}

package com.smart.tech.findfriend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate implements Serializable {
    //经度
    private Double longitude;
    //纬度
    private Double latitude;
}

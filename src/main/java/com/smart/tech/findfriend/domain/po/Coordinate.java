package com.smart.tech.findfriend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate implements Serializable {
    //经度
    @NotNull(message = "经度不能为空")
    private Double longitude;
    //纬度
    @NotNull(message = "纬度不能为空")
    private Double latitude;
}

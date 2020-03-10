package com.datax.sunset.model.dto;

import com.datax.sunset.controller.validation.UniqueCityName;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityRequestDto {

    @NotEmpty
    @UniqueCityName
    private String name;
    @Min(value = -90, message = "Minimum latitude value is -90")
    @Max(value = 90, message = "Maximum latitude value is 90")
    private float latitude;
    @Min(value = -180, message = "Minimum longitude value is -180")
    @Max(value = 180, message = "Maximum longitude value is 180")
    private float longitude;
}

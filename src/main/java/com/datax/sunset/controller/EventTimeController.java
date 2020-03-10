package com.datax.sunset.controller;

import com.datax.sunset.model.City;
import com.datax.sunset.model.dto.CityResponseDto;
import com.datax.sunset.service.CityService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event-time")
public class EventTimeController {

    private final CityService cityService;

    public EventTimeController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<CityResponseDto> getEventTime() {
        List<City> cities = cityService.getAllCities();
        return cities
                .stream()
                .map(this::getCityDto)
                .collect(Collectors.toList());
    }

    private CityResponseDto getCityDto(City city) {
        CityResponseDto cityResponseDto = new CityResponseDto();
        cityResponseDto.setName(city.getName());
        cityResponseDto.setSunrise(city.getSunrise().toString());
        cityResponseDto.setSunset(city.getSunset().toString());
        return cityResponseDto;
    }
}

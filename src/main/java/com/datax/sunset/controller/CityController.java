package com.datax.sunset.controller;

import com.datax.sunset.model.City;
import com.datax.sunset.model.dto.CityRequestDto;
import com.datax.sunset.model.dto.CityResponseDto;
import com.datax.sunset.service.CityService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public String add(@RequestBody @Valid CityRequestDto cityRequestDto) {
        City city = new City();
        city.setName(cityRequestDto.getName());
        city.setLatitude(cityRequestDto.getLatitude());
        city.setLongitude(cityRequestDto.getLongitude());
        cityService.add(city);
        return "New city was added";
    }

    @GetMapping
    public List<CityResponseDto> getAllCities() {
        return cityService.getAllCities()
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

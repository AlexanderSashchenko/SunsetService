package com.datax.sunset.controller;

import com.datax.sunset.model.City;
import com.datax.sunset.model.dto.CityRequestDto;
import com.datax.sunset.service.CityService;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CityControllerTest {

    private CityController cityController;

    @BeforeEach
    void setUp() {
        cityController = new CityController(null);
    }


    @Test
    void makeSureCityEndPointIsUp() {
        CityRequestDto requestDto = new CityRequestDto();
        requestDto.setName("Kyiv");
        requestDto.setLatitude(50.4501F);
        requestDto.setLongitude(30.5234F);
        RestAssured.given()
                .contentType("application/json")
                .body(requestDto)
                .when().post("/city").then()
                .statusCode(200);
    }

    @Test
    void makeSureCityIsAdded() {
        City city = new City();
        city.setName("Kyiv");

        CityRequestDto requestDto = new CityRequestDto();
        requestDto.setName("Kyiv");

        CityService cityServiceMock = Mockito.mock(CityService.class);
        Mockito.when(cityServiceMock.add(city)).thenReturn(city);
        cityController = new CityController(cityServiceMock);
        String response = cityController.add(requestDto);
        Assert.assertEquals("New city was added", response);

    }

    @Test
    void makeSureGetAllCitiesReturnsCorrectData() {
        City city = new City();
        city.setName("Kyiv");
        city.setSunrise(LocalTime.of(6, 30));
        city.setSunset(LocalTime.of(19, 30));
        List<City> cities = new ArrayList<>();
        cities.add(city);

        CityService cityServiceMock = Mockito.mock(CityService.class);
        Mockito.when(cityServiceMock.getAllCities()).thenReturn(cities);
        cityController = new CityController(cityServiceMock);

        Assert.assertEquals("06:30", cityController.getAllCities().get(0).getSunrise());
    }

    @Test
    void makeSureGetAllCitiesReturnsEmptyList() {
        List<City> cities = new ArrayList<>();

        CityService cityServiceMock = Mockito.mock(CityService.class);
        Mockito.when(cityServiceMock.getAllCities()).thenReturn(cities);
        cityController = new CityController(cityServiceMock);

        Assert.assertTrue(cityController.getAllCities().isEmpty());
    }
}
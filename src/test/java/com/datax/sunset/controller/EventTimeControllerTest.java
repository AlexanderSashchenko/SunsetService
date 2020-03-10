package com.datax.sunset.controller;

import com.datax.sunset.model.City;
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
class EventTimeControllerTest {

    private CityController cityController;

    @BeforeEach
    void setUp() {
        cityController = new CityController(null);
    }

    @Test
    void makeSureEventTimeEndpointIsUp() {
        RestAssured.given()
                .when().get("/event-time")
                .then()
                .statusCode(200);
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
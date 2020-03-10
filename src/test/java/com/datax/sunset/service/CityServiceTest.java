package com.datax.sunset.service;

import com.datax.sunset.model.City;
import com.datax.sunset.repository.CityRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
class CityServiceTest {

    private CityService cityService;

    @BeforeEach
    void setUp() {
        cityService = new CityServiceImpl(null, null);
    }

    @Test
    void makeSureAddCityReturnsCorrectData() {
        City city = new City();
        city.setName("Kyiv");

        Map<String, LocalTime> timeMap = new HashMap<>();
        timeMap.put("sunrise", LocalTime.of(6, 30));

        SunriseSunsetApiCommunicationService sunriseSunsetMock
                = Mockito.mock(SunriseSunsetApiCommunicationService.class);
        Mockito.when(sunriseSunsetMock.getSunriseSunsetTime(city)).thenReturn(timeMap);
        CityRepository cityRepositoryMock = Mockito.mock(CityRepository.class);
        Mockito.when(cityRepositoryMock.save(city)).thenReturn(city);
        cityService = new CityServiceImpl(cityRepositoryMock, sunriseSunsetMock);

        Assert.assertEquals("06:30",cityService.add(city).getSunrise().toString());

    }

    @Test
    void makeSureFindByNameReturnsCorrectData() {
        City city = new City();
        city.setName("Kyiv");
        Optional<City> cityOptional = Optional.of(city);

        CityRepository cityRepositoryMock = Mockito.mock(CityRepository.class);
        Mockito.when(cityRepositoryMock.findByName(city.getName())).thenReturn(cityOptional);
        cityService = new CityServiceImpl(cityRepositoryMock, null);

        Assert.assertEquals("Kyiv",
                cityService.findByName(city.getName()).get().getName());
    }

    @Test
    void makeSureFindByNameReturnsEmptyOptionalOfNonExistentEntity() {
        City city = new City();
        city.setName("Kyiv");
        Optional<City> cityOptional = Optional.of(city);

        CityRepository cityRepositoryMock = Mockito.mock(CityRepository.class);
        Mockito.when(cityRepositoryMock.findByName(city.getName())).thenReturn(cityOptional);
        cityService = new CityServiceImpl(cityRepositoryMock, null);

        Assert.assertEquals(Optional.empty(), cityService.findByName("Dnipro"));
    }

    @Test
    void makeSureGetAllCitiesReturnsCorrectData() {
        List<City> cities = new ArrayList<>();
        City city = new City();
        city.setName("Kyiv");
        cities.add(city);

        CityRepository cityRepositoryMock = Mockito.mock(CityRepository.class);
        Mockito.when(cityRepositoryMock.findAll()).thenReturn(cities);
        cityService = new CityServiceImpl(cityRepositoryMock, null);

        Assert.assertEquals("Kyiv", cityService.getAllCities().get(0).getName());
    }
}
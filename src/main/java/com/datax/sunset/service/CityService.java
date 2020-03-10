package com.datax.sunset.service;

import com.datax.sunset.model.City;
import java.util.List;
import java.util.Optional;

public interface CityService {
    City add(City city);

    Optional<City> findByName(String cityName);

    List<City> getAllCities();
}

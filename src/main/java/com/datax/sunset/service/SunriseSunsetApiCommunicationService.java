package com.datax.sunset.service;

import com.datax.sunset.model.City;
import java.time.LocalTime;
import java.util.Map;

public interface SunriseSunsetApiCommunicationService {
    Map<String, LocalTime> getSunriseSunsetTime(City city);
}

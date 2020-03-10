package com.datax.sunset.service;

import com.datax.sunset.model.City;
import com.datax.sunset.repository.CityRepository;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    private static final String SUNRISE = "sunrise";
    private static final String SUNSET = "sunset";
    private final CityRepository cityRepository;
    private final SunriseSunsetApiCommunicationService sunriseSunsetService;

    public CityServiceImpl(CityRepository cityRepository,
                           SunriseSunsetApiCommunicationService sunriseSunsetService) {
        this.cityRepository = cityRepository;
        this.sunriseSunsetService = sunriseSunsetService;
    }

    @Override
    public City add(City city) {
        Map<String, LocalTime> sunriseSunsetTime
                = sunriseSunsetService.getSunriseSunsetTime(city);
        city.setSunrise(sunriseSunsetTime.get(SUNRISE));
        city.setSunset(sunriseSunsetTime.get(SUNSET));
        return cityRepository.save(city);
    }

    @Override
    public Optional<City> findByName(String cityName) {
        return cityRepository.findByName(cityName);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Scheduled(cron = "1 * * * * ?")
    private void updateSunsetSunriseTime() {
        getAllCities().forEach(this::add);
    }
}

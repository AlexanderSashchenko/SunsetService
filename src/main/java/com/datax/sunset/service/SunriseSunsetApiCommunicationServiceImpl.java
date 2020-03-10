package com.datax.sunset.service;

import com.datax.sunset.model.City;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class SunriseSunsetApiCommunicationServiceImpl
        implements SunriseSunsetApiCommunicationService {

    private static final String SUNRISE = "sunrise";
    private static final String SUNSET = "sunset";
    private static final String STATUS = "status";
    private static final String STATUS_OK = "OK";
    private static final String RESULTS = "results";
    private static final String BASE_URL = "https://api.sunrise-sunset.org";
    private static final ZoneId KIEV_ZONE_ID = ZoneId.of("Europe/Kiev");
    private final WebClient webClient;

    public SunriseSunsetApiCommunicationServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Map<String, LocalTime> getSunriseSunsetTime(City city) {
        String rawJson = webClient.get()
                .uri("/json?lat=" + city.getLatitude()
                        + "&lng=" + city.getLongitude()
                        + "&date=today&formatted=0")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return parseAdjustedSunsetSunriseTime(rawJson);
    }

    private Map<String, LocalTime> parseAdjustedSunsetSunriseTime(String rawJson) {
        Map<String, LocalTime> sunriseSunsetTimeMap = new HashMap<>();
        try {
            JSONObject sunsetSunriseObject = new JSONObject(rawJson);
            if (sunsetSunriseObject.get(STATUS).equals(STATUS_OK)) {
                JSONObject results = sunsetSunriseObject.getJSONObject(RESULTS);

                TemporalAccessor sunriseTemporal
                        = ZonedDateTime.parse(results.get(SUNRISE).toString());
                Instant sunriseInstant = Instant.from(sunriseTemporal);
                ZonedDateTime zonedSunriseTime
                        = ZonedDateTime.ofInstant(sunriseInstant, KIEV_ZONE_ID);
                sunriseSunsetTimeMap.put(SUNRISE, zonedSunriseTime.toLocalTime());

                TemporalAccessor sunsetTemporal
                        = ZonedDateTime.parse(results.get(SUNSET).toString());
                Instant sunsetInstant = Instant.from(sunsetTemporal);
                ZonedDateTime zonedSunsetTime
                        = ZonedDateTime.ofInstant(sunsetInstant, KIEV_ZONE_ID);
                sunriseSunsetTimeMap.put(SUNSET, zonedSunsetTime.toLocalTime());
            }
        } catch (JSONException e) {
            log.error("Error during JSON object parsing" + e);
            throw new RuntimeException(e);
        }
        return sunriseSunsetTimeMap;
    }
}

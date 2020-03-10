package com.datax.sunset.repository;

import com.datax.sunset.model.City;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String cityName);
}

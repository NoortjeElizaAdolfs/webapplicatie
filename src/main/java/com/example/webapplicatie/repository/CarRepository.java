package com.example.webapplicatie.repository;

import com.example.webapplicatie.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

// Brug tussen JPA repository functies en car entiteit
import java.util.List;
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByPublished(boolean published);
    List<Car> findByTitleContaining(String title);
}

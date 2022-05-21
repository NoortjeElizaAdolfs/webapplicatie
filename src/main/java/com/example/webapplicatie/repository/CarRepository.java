package com.example.webapplicatie.repository;
import java.util.List;

import com.example.webapplicatie.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByPublished(boolean published);
    List<Car> findByTitleContaining(String title);
}

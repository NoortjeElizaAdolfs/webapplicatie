package com.example.webapplicatie.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.webapplicatie.models.Car;
import com.example.webapplicatie.models.User;
import com.example.webapplicatie.repository.CarRepository;
import com.example.webapplicatie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CarController {
    @Autowired
    CarRepository carRepository;
    UserRepository userRepository;
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<Car> cars = new ArrayList<Car>();
            if (title == null)
                cars.addAll(carRepository.findAll());
            else
                carRepository.findByTitleContaining(title).forEach(cars::add);
            if (cars.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") long id) {
        Optional<Car> carData = carRepository.findById(id);
        if (carData.isPresent()) {
            return new ResponseEntity<>(carData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        try {
            // User _user = userRepository.getById(car.getUser().getId());
            Car _car = carRepository
                    .save(new Car(
                            car.getTitle(),
                            car.getDescription(),
                            car.getUser(),
                            false
                    ));
            return new ResponseEntity<>(_car, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/{user_id}/add_photo")
//    public @ResponseBody String addNewPhoto(@Valid @PathVariable (value = "user_id") Integer userId, @RequestParam String photoUUID) {
//        Photo p = new Photo();
//
//        userRepository.findById(userId).map( user -> {
//            p.setUser(user);
//            p.setPhotoUUID(photoUUID);
//            return photoRepository.save(p);
//        });
//        return "Photo Saved";
//    }


////    @RequestMapping(method = RequestMethod.POST)
//    @PostMapping("/cars")
//    public ResponseEntity<?> createCar(@RequestBody Car car) {
//
//        try {
//            return new ResponseEntity<>(carRepository.save(car), HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable("id") long id, @RequestBody Car car) {
        Optional<Car> carData = carRepository.findById(id);
        if (carData.isPresent()) {
            Car _car = carData.get();
            _car.setTitle(car.getTitle());
            _car.setDescription(car.getDescription());
            _car.setPublished(car.isPublished());
            return new ResponseEntity<>(carRepository.save(_car), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable("id") long id) {
        try {
            carRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/cars")
    public ResponseEntity<HttpStatus> deleteAllCars() {
        try {
            carRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/cars/published")
    public ResponseEntity<List<Car>> findByPublished() {
        try {
            List<Car> cars = carRepository.findByPublished(true);
            if (cars.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

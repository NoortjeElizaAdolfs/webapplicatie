package com.example.webapplicatie.controller;

import com.example.webapplicatie.models.Car;
import com.example.webapplicatie.models.User;
import com.example.webapplicatie.payLoad.response.PdfExporter;
import com.example.webapplicatie.repository.CarRepository;
import com.example.webapplicatie.repository.UserRepository;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")

@RestController

@RequestMapping("/api")
public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars(@RequestParam(required = false) String title) {

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
            User _user = userRepository.getById(car.getUser().getId());
            Car _car = carRepository
                    .save(new Car(
                            car.getTitle(),
                            car.getDescription(),
                            _user,
                            false
                    ));
            return new ResponseEntity<>(_car, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/cars")
//    public Car createUser(@Valid @RequestBody User user) { return userService.addUser(user); }

    @PostMapping("/cars/user/{id}")

    public ResponseEntity<Car> createCar(@RequestBody Car car, @PathVariable(value = "id")long id) {
        try {
            //User _user = userRepository.getById(id);
            User _user = userRepository.getById(car.getUser().getId());
            System.out.println(_user);
            car.setUser(_user);
            Car _car = carRepository
                    .save(
                    car);
            return new ResponseEntity<>(_car, HttpStatus.CREATED);
        } catch (Exception e) {
              return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/cars/{id}/user/{userId}")
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
    @GetMapping("/cars/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException
    {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue= "attachment; filename=classes_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Car> carList = carRepository.findAll();
        PdfExporter exporter = new PdfExporter(carList);
        exporter.export(response);
    }
}

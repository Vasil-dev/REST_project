package ua.foxminded.vasilmartsyniuk.car_rest_project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.vasilmartsyniuk.car_rest_project.entity.Car;
import ua.foxminded.vasilmartsyniuk.car_rest_project.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/show")
    public Car getCarById(@RequestParam("id") int id) {
        LOGGER.info("Getting car by id: {}", id);
        return carService.getById(id);
    }

    @GetMapping("/all")
    public List<Car> getAllCars() {
        LOGGER.info("Getting all cars");
        return carService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> createCar(@RequestBody Car car) {
        carService.createCar(car);
        LOGGER.info("Creating car: {}", car);
        return ResponseEntity.ok("Car added successfully");
    }

    @PutMapping("/update")
    public Car updateCar(@RequestParam("id") Integer id, @RequestBody Car car) {
        LOGGER.info("Updating car by id: {}: {}", id, car);
        car.setId(id);
        return carService.updateCar(car);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Car>> searchCars(
            @RequestParam(name = "make", required = false) String make,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "year", required = false) Integer year) {
        LOGGER.info("Searching cars with parameters: {}: {}: {}", make, model, year);
        List<Car> cars = carService.searchCars(make, model, year);
        return ResponseEntity.ok(cars);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCar(@RequestParam("id") int id) {
        Car car = carService.getById(id);
        if (car == null) {
            throw new IllegalArgumentException("Car with ID " + id + " not found.");
        } else {
            carService.deleteCar(id);
        }
        LOGGER.info("Deleting car with id: {}", id);
        return ResponseEntity.ok("Car deleted successfully");
    }
}
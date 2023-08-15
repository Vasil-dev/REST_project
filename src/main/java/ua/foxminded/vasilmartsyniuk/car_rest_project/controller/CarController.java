package ua.foxminded.vasilmartsyniuk.car_rest_project.controller;

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

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/show")
    public Car getCarById(@RequestParam("id") int id) {
        return carService.getById(id);
    }

    @GetMapping("/all")
    public List<Car> getAllCars() {
        return carService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> createCar(@RequestBody Car car) {
        carService.createCar(car);
        return ResponseEntity.ok("Car added successfully");
    }

    @PutMapping("/update")
    public Car updateCar(@RequestParam("id") Integer id, @RequestBody Car car) {
        car.setId(id);
        return carService.updateCar(car);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Car>> searchCars(
            @RequestParam(name = "make", required = false) String make,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "year", required = false) Integer year) {

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
        return ResponseEntity.ok("Car deleted successfully");
    }
}
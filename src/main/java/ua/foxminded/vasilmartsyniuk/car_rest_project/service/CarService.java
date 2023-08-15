package ua.foxminded.vasilmartsyniuk.car_rest_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.vasilmartsyniuk.car_rest_project.entity.Car;
import ua.foxminded.vasilmartsyniuk.car_rest_project.repository.CarRepository;
import ua.foxminded.vasilmartsyniuk.car_rest_project.repository.CategoryRepository;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car getById(Integer id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public void createCar(Car car) {
        carRepository.save(car);
    }

    public Car updateCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }

    public List<Car> searchCars(String make, String model, Integer year) {
        if (year != null) {
            return searchCarsByYear(year);
        } else if (model != null) {
            return searchCarsByModel(model);
        } else if (make != null) {
            return searchCarsByMake(make);
        } else {
            return null;
        }
    }

    private List<Car> searchCarsByMake(String make) {
        return carRepository.findByMake(make);
    }

    private List<Car> searchCarsByModel(String model) {
        return carRepository.findByModel(model);
    }

    private List<Car> searchCarsByYear(Integer year) {
        return carRepository.findByYear(year);
    }
}

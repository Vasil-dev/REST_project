package ua.foxminded.vasilmartsyniuk.car_rest_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.vasilmartsyniuk.car_rest_project.entity.Car;


import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByYear(Integer year);

    List<Car> findByModel(String model);

    List<Car> findByMake(String make);
}

package ua.foxminded.vasilmartsyniuk.car_rest_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ua.foxminded.vasilmartsyniuk.car_rest_project.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
}

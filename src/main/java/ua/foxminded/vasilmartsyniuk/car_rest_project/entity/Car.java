package ua.foxminded.vasilmartsyniuk.car_rest_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Size(min = 1, max = 999999999)
    private int id;

    @Column(name = "make")
    @Size(min = 1, max = 30)
    @NotBlank
    private String make;

    @Column(name = "year")
    @Size(min = 1, max = 4)
    @NotBlank
    private Integer year;

    @Column(name = "model")
    @Size(min = 1, max = 30)
    @NotBlank
    private String model;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "car_category",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    public Car(int id, String make, Integer year, String model) {
        this.id = id;
        this.make = make;
        this.year = year;
        this.model = model;
    }

    public Car() {

    }
}
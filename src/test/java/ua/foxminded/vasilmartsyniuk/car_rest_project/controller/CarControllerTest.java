package ua.foxminded.vasilmartsyniuk.car_rest_project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ua.foxminded.vasilmartsyniuk.car_rest_project.entity.Car;
import ua.foxminded.vasilmartsyniuk.car_rest_project.service.CarService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
@ExtendWith(SpringExtension.class)
class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void testGetAllCars() throws Exception {
        List<Car> cars = Arrays.asList(
                new Car(1, "Audi", 2020, "Q5"),
                new Car(2, "Audi", 2018, "A4"),
                new Car(3, "BMW", 2021, "X5")
        );

        when(carService.getAll()).thenReturn(cars);

        mvc.perform(MockMvcRequestBuilders.get("/cars/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(cars.size()));
    }

    @Test
    @WithMockUser
    void testGetCarById() throws Exception {
        final int ID = 1;
        Car car = new Car(ID, "BMW", 2023, "M3");

        when(carService.getById(ID)).thenReturn(car);

        mvc.perform(MockMvcRequestBuilders.get("/cars/show")
                        .param("id", String.valueOf(ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.make").value(car.getMake()))
                .andExpect(jsonPath("$.year").value(car.getYear()))
                .andExpect(jsonPath("$.model").value(car.getModel()));
    }

    @Test
    @WithMockUser
    void testCreateCar() throws Exception {

        Car car = new Car(1, "Audi", 2020, "Q5");
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(car);

        mvc.perform(MockMvcRequestBuilders.post("/cars/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andExpect(status().isOk());

        verify(carService, times(1)).createCar(car);
    }

    @Test
    @WithMockUser
    void testUpdateCar() throws Exception {
        int carId = 1;
        Car updatedCar = new Car(carId, "Audi", 2020, "Q5");

        when(carService.updateCar(any(Car.class))).thenReturn(updatedCar);

        mvc.perform(MockMvcRequestBuilders.put("/cars/update")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", String.valueOf(carId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"make\": \"Audi\", \"year\": 2020, \"model\": \"Q5\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carId))
                .andExpect(jsonPath("$.make").value(updatedCar.getMake()))
                .andExpect(jsonPath("$.year").value(updatedCar.getYear()))
                .andExpect(jsonPath("$.model").value(updatedCar.getModel()));

        verify(carService, times(1)).updateCar(any(Car.class));
    }

    @Test
    @WithMockUser
    void testSearchCar() throws Exception {

        List<Car> cars = Arrays.asList(
                new Car(1, "Audi", 2020, "Q5"),
                new Car(2, "BMW", 2021, "M3")
        );

        when(carService.searchCars("Audi", "Q5", null)).thenReturn(cars);

        mvc.perform(MockMvcRequestBuilders.get("/cars/search")
                        .param("make", "Audi")
                        .param("model", "Q5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(cars.size()));

    }

    @Test
    @WithMockUser
    void testDeleteCar() throws Exception {
        int carId = 1;
        Car car = new Car();
        car.setId(carId);

        when(carService.getById(carId)).thenReturn(car);

        mvc.perform(MockMvcRequestBuilders.delete("/cars/delete")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", String.valueOf(carId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Car deleted successfully"));

        verify(carService, times(1)).deleteCar(carId);
    }

}
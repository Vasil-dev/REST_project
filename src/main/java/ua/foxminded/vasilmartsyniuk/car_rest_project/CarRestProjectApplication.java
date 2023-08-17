package ua.foxminded.vasilmartsyniuk.car_rest_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CarRestProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRestProjectApplication.class, args);
	}

}

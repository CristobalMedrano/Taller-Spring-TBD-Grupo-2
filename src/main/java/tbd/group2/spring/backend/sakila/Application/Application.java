package tbd.group2.spring.backend.sakila.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"tbd.group2.spring.backend.sakila.Application", "tbd.group2.spring.backend.sakila.Rest"})
@EntityScan("tbd.group2.spring.backend.sakila.Entities")
@EnableJpaRepositories("tbd.group2.spring.backend.sakila.Repository")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

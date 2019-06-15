package com.gabriel.box.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gabriel.box.application.repository.UserRepository;
import com.gabriel.box.application.repository.entity.User;

@SpringBootApplication
public class BoxRestApplication {
	public static void main(String[] args) {
        SpringApplication.run(BoxRestApplication.class, args);
    }
	
	@Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
        	User userDefault = new User();
        	userDefault.setName("User");
        	userDefault.setEmail("email@usuario.com");
            repository.save(userDefault);
        };
    }
}

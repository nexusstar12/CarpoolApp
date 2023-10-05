package com.fantasticfour.poolapp;

import com.fantasticfour.poolapp.repository.UserRepository;
import com.fantasticfour.poolapp.domain.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PoolappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoolappApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			// Create and save a new user
			User user = new User();
			user.setName("John Doe");
			user.setEmail("john.doe@example.com");
			userRepository.save(user);

			// Fetch and print all users from the database
			for (User fetchedUser : userRepository.findAll()) {
				System.out.println(fetchedUser.getName());
			}
		};
	}
}
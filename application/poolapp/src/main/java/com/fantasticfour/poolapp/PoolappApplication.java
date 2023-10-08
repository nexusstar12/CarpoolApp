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
	private UserRepository userRepository;

	@Autowired
	public PoolappApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			// Create and save a new user
			User user = new User();
			user.setFirstName("John");
			user.setLastName("Doe");
			user.setPhoneNumber("123-456-7890");
			user.setEmail("john.doe@example.com");
			userRepository.save(user);

			// Fetch and print all users from the database
			for (User fetchedUser : userRepository.findAll()) {
				System.out.println(fetchedUser.getFirstName() + " " + fetchedUser.getLastName());
			}
		};
	}
}
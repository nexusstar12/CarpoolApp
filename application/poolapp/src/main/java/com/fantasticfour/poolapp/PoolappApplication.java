package com.fantasticfour.poolapp;

import com.fantasticfour.poolapp.repository.UserRepository;
import com.fantasticfour.poolapp.domain.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
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
			//always check if email exists before saving a user.
			//causes error

			// Fetch and print all users from the database
			for (User fetchedUser : userRepository.findAll()) {
				System.out.println(fetchedUser.getFirstName() + " " + fetchedUser.getLastName());
			}
		};
	}
}
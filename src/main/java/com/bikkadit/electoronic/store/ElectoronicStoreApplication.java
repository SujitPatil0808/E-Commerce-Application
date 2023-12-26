package com.bikkadit.electoronic.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ElectoronicStoreApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(ElectoronicStoreApplication.class, args);

		System.out.println("Application Started Successfully");

	}

	// Command Line Runner Mainly use for if we want to run something before main method that time we use it
	// Here We Encode PassWord IN bycreptedForm
	@Override
	public void run(String... args) throws Exception {

		System.out.println( passwordEncoder.encode("4587"));
	}
}

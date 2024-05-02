package com.vikas.hotelmanagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.vikas.hotelmanagment" })
public class HotelmanagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelmanagmentApplication.class, args);
	}

}

package com.neoleaptask.NeoleapTask;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NeoleapTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeoleapTaskApplication.class, args);
	}


}

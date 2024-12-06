package com.neoleaptask.NeoleapTask;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableCaching
public class NeoleapTaskApplication implements CommandLineRunner {

	private final JdbcTemplate jdbcTemplate;

	public NeoleapTaskApplication(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(NeoleapTaskApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		String sql = "INSERT INTO orders (product_name, order_id, description, amount, status, created_at) VALUES " +
				"('Smartphone', UNHEX(REPLACE(UUID(), '-', '')), 'Electronics order for a smartphone', 599.99, 'NEW', CURRENT_TIMESTAMP), " +
				"('Washing Machine', UNHEX(REPLACE(UUID(), '-', '')), 'Home appliance order for a washing machine', 799.99, 'NEW', CURRENT_TIMESTAMP), " +
				"('Novel', UNHEX(REPLACE(UUID(), '-', '')), 'Book order for a novel', 19.99, 'NEW', CURRENT_TIMESTAMP), " +
				"('Vegetables', UNHEX(REPLACE(UUID(), '-', '')), 'Grocery order for fresh vegetables', 25.49, 'NEW', CURRENT_TIMESTAMP), " +
				"('Jacket', UNHEX(REPLACE(UUID(), '-', '')), 'Clothing order for a jacket', 89.99, 'NEW', CURRENT_TIMESTAMP), " +
				"('Running Shoes', UNHEX(REPLACE(UUID(), '-', '')), 'Footwear order for running shoes', 120.00, 'NEW', CURRENT_TIMESTAMP), " +
				"('Gaming Console', UNHEX(REPLACE(UUID(), '-', '')), 'Gaming console order', 299.99, 'NEW', CURRENT_TIMESTAMP), " +
				"('Dining Table', UNHEX(REPLACE(UUID(), '-', '')), 'Furniture order for a dining table', 499.99, 'NEW', CURRENT_TIMESTAMP), " +
				"('Dog Food', UNHEX(REPLACE(UUID(), '-', '')), 'Pet supplies order for dog food', 45.00, 'NEW', CURRENT_TIMESTAMP), " +
				"('Yoga Mat', UNHEX(REPLACE(UUID(), '-', '')), 'Fitness equipment order for a yoga mat', 20.00, 'NEW', CURRENT_TIMESTAMP);";

		jdbcTemplate.update(sql);
		System.out.println("Orders inserted successfully!");
	}
}

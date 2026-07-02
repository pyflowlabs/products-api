package com.pyflowlabs.productsapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Puts a few sample products into the database on startup, but only if it is
 * empty. This gives the API something to return the first time you call it.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository repository;

    public DataSeeder(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Product("Wireless Mouse", 24.99, 120));
            repository.save(new Product("Mechanical Keyboard", 79.50, 45));
            repository.save(new Product("USB-C Hub", 34.00, 80));
            repository.save(new Product("1080p Webcam", 49.90, 30));
        }
    }
}

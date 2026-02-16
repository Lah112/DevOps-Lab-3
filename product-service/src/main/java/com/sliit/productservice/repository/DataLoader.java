package com.sliit.productservice;

import com.sliit.productservice.entity.Product;
import com.sliit.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository repository;

    public DataLoader(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load sample products
        repository.save(new Product("Laptop", 999.99));
        repository.save(new Product("Mouse", 29.99));
        repository.save(new Product("Keyboard", 59.99));
        repository.save(new Product("Monitor", 299.99));
        
        System.out.println("Sample products loaded successfully!");
    }
}
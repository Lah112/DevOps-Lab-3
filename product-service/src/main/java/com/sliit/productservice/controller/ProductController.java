package com.sliit.productservice.controller;

import com.sliit.productservice.entity.Product;
import com.sliit.productservice.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;  // ADD THIS IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {

    private final ProductRepository repository;

    @Autowired  // ADD THIS ANNOTATION
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    // 1️⃣ CREATE (POST)
    @PostMapping
    @Operation(summary = "Create a new product", description = "Adds a new product to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Product createProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    // 2️⃣ GET ALL
    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // 3️⃣ GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a single product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Optional<Product> getProductById(
        @Parameter(description = "ID of the product to retrieve", required = true)
        @PathVariable Long id) {
        return repository.findById(id);
    }

    // 4️⃣ DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public void deleteProduct(
        @Parameter(description = "ID of the product to delete", required = true)
        @PathVariable Long id) {
        repository.deleteById(id);
    }
    
    // 5️⃣ UPDATE (PUT)
    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Product updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable Long id, 
            @RequestBody Product productDetails) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        
        return repository.save(product);
    }
}
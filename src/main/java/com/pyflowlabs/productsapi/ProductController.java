package com.pyflowlabs.productsapi;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

/**
 * REST endpoints for products.
 *
 *   GET    /api/products       list all products
 *   GET    /api/products/{id}  get a single product
 *   POST   /api/products       create a product
 *   PUT    /api/products/{id}  update a product
 *   DELETE /api/products/{id}  delete a product
 *
 * Invalid input (thanks to @Valid) is automatically rejected with 400 Bad Request.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        product.setId(null); // make sure we insert a new row instead of overwriting one
        Product saved = repository.save(product);
        return ResponseEntity.created(URI.create("/api/products/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody Product product) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

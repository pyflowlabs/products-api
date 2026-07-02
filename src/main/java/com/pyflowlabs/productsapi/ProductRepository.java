package com.pyflowlabs.productsapi;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access for products. By extending JpaRepository we get all the common
 * database operations (findAll, findById, save, deleteById, ...) for free.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}

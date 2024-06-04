package com.example.onlineshop.repositories;

import com.example.onlineshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

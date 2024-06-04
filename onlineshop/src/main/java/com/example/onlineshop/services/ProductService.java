package com.example.onlineshop.services;

import com.example.onlineshop.dto.ProductDto;

import java.util.List;

public interface ProductService {
    public ProductDto getProductById(Long productId);
    public ProductDto createProduct(ProductDto productDto);
    public ProductDto updateProduct(Long id, ProductDto productDto);
    public String deleteProductById(Long productId);
    public List<ProductDto> getAllProducts();

}

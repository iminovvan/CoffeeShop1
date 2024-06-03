package com.example.onlineshop.services.impl;

import com.example.onlineshop.dto.ProductDto;
import com.example.onlineshop.entities.Product;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.repositories.ProductRepository;
import com.example.onlineshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        return mapToDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product = productRepository.save(product);
        return mapToDto(product);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found."));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product = productRepository.save(product);
        return mapToDto(product);
    }

    @Override
    public String deleteProductById(Long productId) {
        productRepository.deleteById(productId);
        return "Product Deleted Successfully.";
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public ProductDto mapToDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }
}

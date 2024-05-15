package com.example.catalogue.service;

import com.example.catalogue.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

  List<Product> findAllProducts();

  List<Product> findAllProducts(String filter);

  Product createProduct(String title, String details);

  Optional<Product> findProduct(int productId);

  void updateProduct(Integer id, String title, String details);

  void deleteProduct(Integer id);
}

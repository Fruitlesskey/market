package com.example.catalogue.repository;

import com.example.catalogue.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  @Query("SELECT p FROM Product p WHERE p.title ILIKE :filter")
  List<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
}
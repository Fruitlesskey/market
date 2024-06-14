package com.example.catalogue.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.catalogue.entity.Product;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Sql("/sql/products.sql")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryIT {

  @Autowired
  ProductRepository productRepository;

  @Test
  void findAllByTitleLikeIgnoreCase_ReturnsFilteredProductsList() {
    //given
    String filter = "%шоколадка%";
    //when
    List<Product> products = this.productRepository.findAllByTitleLikeIgnoreCase(
        filter);
    //then
    assertEquals(List.of(new Product(2, "Шоколадка", "Очень вкусная")), products);
  }

}
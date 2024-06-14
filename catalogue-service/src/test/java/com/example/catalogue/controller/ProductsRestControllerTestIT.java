package com.example.catalogue.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class ProductsRestControllerTestIT {

  @Autowired
  MockMvc mockMvc;

  @Sql("/sql/products.sql")
  @Test
  void findProductsReturnsProductsList() throws Exception {
    //given
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
            "/catalogue-api/products")
        .param("filter", "товар")
        .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));
    //when
    this.mockMvc.perform(requestBuilder)
        //then
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json("""
                [
                  {
                    "id": 1,
                    "title": "Товар 1",
                    "details": "Описание 1"
                  },
                  {
                    "id": 3,
                    "title": "Товар 3",
                    "details": "Окрыляет"
                  }
                ]
                """));
  }

  @Test
  void createProduct_RequestIsValid_ReturnsNewProduct() throws Exception {
    //given
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
            "/catalogue-api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
            {
              "title": "Еще один новый товар",
              "details": "Описание 4"}
              """)
        .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

    //when
    this.mockMvc.perform(requestBuilder)
        //then
        .andDo(print())
        .andExpectAll(
            status().isCreated(),
            header().string(HttpHeaders.LOCATION, "http://localhost/catalogue-api/products/1"),
            content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
            content().json("""
                {
                  "id": 1,
                  "title": "Еще один новый товар",
                  "details": "Описание 4"
                }
                """));
  }

  @Test
  void createProduct_RequestIsValid_ReturnsProblemDetail() throws Exception {
    // Given
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/catalogue-api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
            {
              "title": "",
              "details": null
            }
            """)
        .locale(Locale.of("ru", "RU"))
        .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

    // When
    this.mockMvc.perform(requestBuilder)
        // Then
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
            content().json("""
                {
                  "errors": [
                    "Название товара должно быть от 3 до 50 символов"
                  ]
                }
                """));
  }

  @Test
  void createProduct_UserIsNotAuthorized_ReturnsForbidden() throws Exception {
    // Given
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/catalogue-api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
              "title": "",
              "details": null
            }
            """)
            .locale(Locale.of("ru", "RU"))
            .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

    // When
    this.mockMvc.perform(requestBuilder)
        // Then
        .andDo(print())
        .andExpectAll(
            status().isForbidden());
  }

}
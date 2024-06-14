//package com.example.manager.controller;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import com.example.manager.entity.Product;
//import com.github.tomakehurst.wiremock.client.WireMock;
//import com.github.tomakehurst.wiremock.junit5.WireMockTest;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//@SpringBootTest
//@AutoConfigureMockMvc()
//@WireMockTest(httpPort = 54321)
////@AutoConfigureMockMvc(printOnlyOnFailure = false)
//class ProductsControllerIT {
//
//  @Autowired
//  MockMvc mockMvc;
//
//  @Test
//  void getProductsList_ReturnsProductsListPage() throws Exception {
//    //given
//    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
//            "/catalogue/products/list")
//        .queryParam("filter", "товар")
//        .with(user("albert").roles("MANAGER"));
//
//    WireMock.stubFor(WireMock.get(WireMock.urlPathMatching("/catalogue-api/products"))
//        .withQueryParam("filter", WireMock.equalTo("товар"))
//        .willReturn(WireMock.ok("""
//            [
//            {"id": 1, "title": "Товар №1", "details": "Описание товара №1"},
//            {"id": 2, "title": "Товар №2", "details": "Описание товара №2"},
//            {"id": 3, "title": "Товар №3", "details": "Описание товара №3"}
//            ]
//            """).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));
//
//    //when
//    this.mockMvc.perform(requestBuilder)
//        //then
//        .andDo(print())
//        .andExpectAll(
//            status().isOk(),
//            view().name("catalogue/products/products/list"),
//            model().attribute("filter", "товар"),
//            model().attribute("products", List.of(
//                new Product(1, "Описание товара №1","Товар №1" ),
//                new Product(2, "Описание товара №2", "Товар №2"),
//                new Product(3, "Описание товара №3", "Товар №3")
//            ))
//        );
//
//    WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathMatching("/catalogue-api/products"))
//        .withQueryParam("filter", WireMock.equalTo("товар")));
//  }
//
//  @Test
////  @WithMockUser()
//  void getNewProductPage_ReturnsProductPage() throws Exception {
//    //given
//    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/catalogue/products/create")
//        .with(user("Albert").roles("MANAGER"));
//
//    //when
//    this.mockMvc.perform(requestBuilder)
//        //then
//        .andDo(print())
//        .andExpectAll(
//            //status
//            status().isOk(),
//            view().name("catalogue/products/new_product"));
//  }
//
//}

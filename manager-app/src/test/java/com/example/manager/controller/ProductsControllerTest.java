//package com.example.manager.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//
//import com.example.manager.client.BadRequestException;
//import com.example.manager.client.ProductsRestClient;
//import com.example.manager.controller.payload.NewProductPayload;
//import com.example.manager.entity.Product;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.ui.ConcurrentModel;
//import org.springframework.validation.BindingResult;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("Модульные тесты ProductsController")
//class ProductsControllerTest {
//
//  @Mock
//  ProductsRestClient productsRestClient;
//
//  @InjectMocks
//  ProductsController productsController;
//
//  @Mock
//  BindingResult bindingResult;
//
//  @Test
//  @DisplayName("createProduct создаст новый товар и перенаправит на страницу товара")
//  void createProduct_RequestIsValid_ReturnsRedirectionToProductPage() {
//    //given
//    NewProductPayload payload = new NewProductPayload("Новый товар", "описание нового товара");
//    ConcurrentModel model = new ConcurrentModel();
//
//    doReturn(new Product("Новый товар", "описание нового товара", 1))
//        .when(this.productsRestClient)
//        .createProduct(eq("Новый товар"), eq("описание нового товара"));
//
//    //when
//    String result = this.productsController.createProduct(payload, bindingResult, model);
//    //then
//    assertEquals("redirect:/catalogue/products/1", result);
//    verify(this.productsRestClient).createProduct("Новый товар", "описание нового товара");
//    verifyNoMoreInteractions(this.productsRestClient);
//  }
//
//  @Test
//  @DisplayName("createProduct вернет страницу с ошибками если запрос не валиден")
//  void createProduct_RequestIsInvalid_ReturnsProductFormWithErrors() {
//    //given
//    NewProductPayload payload = new NewProductPayload(" ", null);
//    ConcurrentModel model = new ConcurrentModel();
//
//    doThrow(new BadRequestException(List.of("Ошибки 1", "ошибка 2")))
//        .when(this.productsRestClient)
//        .createProduct(" ", null);
//    //when
//    String result = this.productsController.createProduct(payload, bindingResult, model);
//    //then
//    assertEquals("catalogue/products/new_product", result);
//    assertEquals(payload, model.getAttribute("payload"));
//    assertEquals(List.of("Ошибки 1", "ошибка 2"), model.getAttribute("errors"));
//
//    verify(this.productsRestClient).createProduct(" ", null);
//    verifyNoMoreInteractions(this.productsRestClient);
//  }
//
//}
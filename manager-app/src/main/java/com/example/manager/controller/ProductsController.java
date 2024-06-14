package com.example.manager.controller;

import com.example.manager.client.BadRequestException;
import com.example.manager.client.ProductsRestClient;
import com.example.manager.controller.payload.NewProductPayload;
import com.example.manager.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

  private final ProductsRestClient productsRestClient;

  @GetMapping("list")
  public String getProductsList(Model model, @RequestParam(name = "filter", required = false) String filter) {
    model.addAttribute("products", this.productsRestClient.findAllProducts(filter));
    model.addAttribute("filter", filter);
    return "catalogue/products/list";
  }

  @GetMapping("create")
  public String getNewProductPage() {
    return "catalogue/products/new_product";
  }

  @PostMapping("create")
  public String createProduct(NewProductPayload payload,
      Model model,
      HttpServletResponse response) {
    try {
      Product product = this.productsRestClient.createProduct(payload.title(), payload.details());
      return "redirect:/catalogue/products/%d".formatted(product.id());
    } catch (BadRequestException exception) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      model.addAttribute("payload", payload);
      model.addAttribute("errors", exception.getErrors());
      return "catalogue/products/new_product";
    }
  }
}
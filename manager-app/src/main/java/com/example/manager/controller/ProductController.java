package com.example.manager.controller;

import com.example.manager.client.BadRequestException;
import com.example.manager.client.ProductsRestClient;
import com.example.manager.controller.payload.UpdateProductPayload;
import com.example.manager.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(("catalogue/products/{productId:\\d+}"))
@RequiredArgsConstructor
public class ProductController {

  private final ProductsRestClient productsRestClient;

  private final MessageSource messageSource;

  @ModelAttribute("product")
  public Product product(@PathVariable("productId") int productId) {
    return this.productsRestClient.findProduct(productId)
        .orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
  }

  @GetMapping
  public String getProduct() {
    return "catalogue/products/product";
  }

  @GetMapping("edit")
  public String getProductEditPage() {
    return "catalogue/products/edit";
  }

  @PostMapping("edit")
  public String updateProduct(@ModelAttribute(name = "product", binding = false) Product product,
      UpdateProductPayload payload,
      Model model) {
      try {
      this.productsRestClient.updateProduct(product.id(), payload.title(), payload.details());
      return "redirect:/catalogue/products/%d".formatted(product.id());
    } catch (BadRequestException exception) {
        model.addAttribute("payload", payload);
        model.addAttribute("errors", exception.getErrors());
        return "catalogue/products/edit";
    }
  }

  @PostMapping("delete")
  public String deleteProduct(@ModelAttribute("product") Product product) {
    this.productsRestClient.deleteProduct(product.id());
    return "redirect:/catalogue/products/list";
  }

  @ExceptionHandler(NoSuchElementException.class)
  public String handleNoSuchElementException(NoSuchElementException exception, Model model,
      HttpServletResponse response, Locale locale) {
    response.setStatus(HttpStatus.NOT_FOUND.value());
    model.addAttribute("error",
        this.messageSource.getMessage(exception.getMessage(), new Object[0],
            exception.getMessage(), locale));
    return "errors/404";
  }
}

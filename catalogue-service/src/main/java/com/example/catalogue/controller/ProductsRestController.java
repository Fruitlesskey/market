package com.example.catalogue.controller;

import com.example.catalogue.controller.payload.NewProductPayload;
import com.example.catalogue.entity.Product;
import com.example.catalogue.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalogue-api/products")
public class ProductsRestController {

  private final ProductService productService;

  @GetMapping
  public List<Product> findProducts(
      @RequestParam(name = "filter", required = false) String filter) {
    return this.productService.findAllProducts(filter);
  }

  @PostMapping
  @Operation(
      security = @SecurityRequirement(name = "keycloak"),
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(
                  type = "object",
                  properties = {
                      @StringToClassMapItem(key = "title", value = String.class),
                      @StringToClassMapItem(key = "details", value = String.class)
                  }
              )
          )
      ),
      responses = {
          @ApiResponse(
              responseCode = "201",
              headers = @Header(name = "Content-Type", description = "Тип данных"),
              content = {
                  @Content(
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(
                          type = "object",
                          properties = {
                              @StringToClassMapItem(key = "id", value = Integer.class),
                              @StringToClassMapItem(key = "title", value = String.class),
                              @StringToClassMapItem(key = "details", value = String.class)
                          }
                      )
                  )
              }
          )
      })
  public ResponseEntity<?> createProduct(
      @RequestBody @Valid NewProductPayload payload,
      BindingResult bindingResult,
      UriComponentsBuilder uriComponentsBuilder)
      throws BindException {
    if (bindingResult.hasErrors()) {
      if (bindingResult instanceof BindException bindException) {
        throw bindException;
      } else {
        throw new BindException(bindingResult);
      }
    } else {
      Product product = this.productService.createProduct(payload.title(), payload.details());
      return ResponseEntity.created(
              uriComponentsBuilder
                  .replacePath("/catalogue-api/products/{productId}")
                  .build(Map.of("productId", product.getId())))
          .body(product);
    }
  }
}

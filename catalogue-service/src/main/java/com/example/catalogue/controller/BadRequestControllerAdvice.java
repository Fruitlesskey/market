package com.example.catalogue.controller;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class BadRequestControllerAdvice {

  private final MessageSource messageSource;

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ProblemDetail> handleBindException(
      BindException exception, Locale locale) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST,
        this.messageSource.getMessage("errors.400.title", new Object[0], locale));
    problemDetail.setProperty("errors",
        exception.getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .toList());
    return ResponseEntity.badRequest().body(problemDetail);
  }

}

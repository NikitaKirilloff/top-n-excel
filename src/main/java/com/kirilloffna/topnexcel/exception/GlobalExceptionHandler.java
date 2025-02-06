package com.kirilloffna.topnexcel.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleIllegalArgumentException(IllegalArgumentException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(XlsxProcessingException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleXlsxProcessingException(XlsxProcessingException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(XlsxFileNotFoundException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleFileNotFound(XlsxFileNotFoundException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleFileNotFound(ConstraintViolationException ex) {
    return ex.getMessage();
  }
}

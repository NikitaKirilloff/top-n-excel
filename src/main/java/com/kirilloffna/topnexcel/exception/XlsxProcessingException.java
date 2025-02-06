package com.kirilloffna.topnexcel.exception;

public class XlsxProcessingException extends RuntimeException {

  public XlsxProcessingException(String message) {
    super(message);
  }

  public XlsxProcessingException(String message, Throwable cause) {
    super(message, cause);
  }
}

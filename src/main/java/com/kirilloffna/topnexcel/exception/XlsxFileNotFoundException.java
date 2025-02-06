package com.kirilloffna.topnexcel.exception;

public class XlsxFileNotFoundException extends RuntimeException {

  public XlsxFileNotFoundException(String filePath) {
    super("Файл не найден, по пути: " + filePath);
  }
}

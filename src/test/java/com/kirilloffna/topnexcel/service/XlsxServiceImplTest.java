package com.kirilloffna.topnexcel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kirilloffna.topnexcel.entity.responce.NthMaxNumberResponse;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

@SpringBootTest
class XlsxServiceImplTest {

  @Autowired
  private XlsxService xlsxService;

  @Test
  void testFindNthMaxNumber_EmptyFile_ShouldThrowException() throws IOException {
    File file = new ClassPathResource("files/Empty.xlsx").getFile();
    String filePath = file.getAbsolutePath();

    assertThrows(IllegalArgumentException.class,
        () -> xlsxService.findNthMaxNumber(filePath, 1));
  }

  @Test
  void testFindNthMaxNumber_TextFile_ShouldThrowException() throws IOException {
    File file = new ClassPathResource("files/WithText.xlsx").getFile();
    String filePath = file.getAbsolutePath();

    assertThrows(IllegalArgumentException.class,
        () -> xlsxService.findNthMaxNumber(filePath, 1));
  }

  @ParameterizedTest
  @CsvSource({
      "-1, 1",
      "-2, 2",
      "-3, 3",
      "-4, 4",
      "-5, 5"
  })
  void testFindNthMaxNumberNegativeNumber(int expectedMax, int requestedN) throws IOException {
    String filePath = new ClassPathResource("files/-1,,,-5.xlsx").getFile().getAbsolutePath();
    testFindNthMaxNumber(filePath, expectedMax, requestedN);
  }

  @ParameterizedTest
  @CsvSource({
      "50, 1",
      "49, 2",
      "31, 20",
      "18, 33",
      "0, 51",
      "-2, 53",
      "-4, 55",
      "-50, 101"
  })
  void testFindNthMaxNumberNegativeAndPositiveNumber(int expectedMax, int requestedN)
      throws IOException {
    String filePath = new ClassPathResource("files/-50,,,50.xlsx").getFile().getAbsolutePath();
    testFindNthMaxNumber(filePath, expectedMax, requestedN);
  }

  @ParameterizedTest
  @CsvSource({
      "50, 1",
      "40, 2",
      "30, 3",
      "20, 4",
      "10, 5"
  })
  void testFindNthMaxNumberWithPositiveTens(int expectedMax, int requestedN) throws IOException {
    String filePath = new ClassPathResource("files/10,20,30,40,50.xlsx").getFile().getAbsolutePath();
    testFindNthMaxNumber(filePath, expectedMax, requestedN);
  }

  private void testFindNthMaxNumber(String filePath, int expectedMax, int requestedN) {
    NthMaxNumberResponse expectedResponse
        = new NthMaxNumberResponse(expectedMax, requestedN, filePath);
    assertEquals(expectedResponse, xlsxService.findNthMaxNumber(filePath, requestedN));
  }
}
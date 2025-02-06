package com.kirilloffna.topnexcel.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilloffna.topnexcel.entity.responce.NthMaxNumberResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class XlsxControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  private static final String URL = "/find-nth-max";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void testFindNthMaxNumber_EmptyFile_ShouldReturnBadRequest() throws Exception {
    String filePath = new ClassPathResource("files/Empty.xlsx").getFile().getAbsolutePath();

    ResponseEntity<String> response = restTemplate.getForEntity(buildUrl(filePath, 1),
        String.class);
    assertEquals(400, response.getStatusCodeValue());
  }

  @Test
  void testFindNthMaxNumber_TextFile_ShouldReturnBadRequest() {
    ResponseEntity<String> response
        = restTemplate.getForEntity(buildUrl("file", -1), String.class);
    assertEquals(400, response.getStatusCodeValue());
    assertTrue(response.getBody().contains("N должно быть больше 0"));
  }

  @Test
  void testValidated() {
    ResponseEntity<String> response = restTemplate.getForEntity(buildUrl(" ", 1), String.class);
    assertEquals(400, response.getStatusCodeValue());
    assertTrue(response.getBody().contains("Путь к локальному файлу не должен быть пустым"));
  }

  @ParameterizedTest
  @CsvSource({
      "50, 1",
      "45, 6",
      "28, 23",
      "10, 41",
      "0, 51",
      "-7, 58",
      "-26, 77",
      "-50, 101"
  })
  void testFindNthMaxNumberNegativeAndPositiveNumber(int expectedMax, int requestedN)
      throws Exception {
    String filePath = new ClassPathResource("files/-50,,,50.xlsx").getFile().getAbsolutePath();
    testFindNthMaxNumber(filePath, expectedMax, requestedN);
  }

  private void testFindNthMaxNumber(String filePath, int expectedMax, int requestedN)
      throws JsonProcessingException {
    ResponseEntity<String> response = restTemplate.getForEntity(buildUrl(filePath, requestedN),
        String.class);

    assertEquals(200, response.getStatusCodeValue());

    NthMaxNumberResponse actualResponse = objectMapper.readValue(response.getBody(),
        NthMaxNumberResponse.class);
    NthMaxNumberResponse expectedResponse = new NthMaxNumberResponse(expectedMax, requestedN,
        filePath);

    assertEquals(expectedResponse, actualResponse);
  }


  private String buildUrl(String filePath, int requestedN) {
    return String.format("%s?Путь к локальному файлу=%s&N-ое максимальное число=%d", URL, filePath,
        requestedN);
  }
}
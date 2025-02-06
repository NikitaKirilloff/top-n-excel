package com.kirilloffna.topnexcel.controller;

import com.kirilloffna.topnexcel.entity.responce.NthMaxNumberResponse;
import com.kirilloffna.topnexcel.service.XlsxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "XLSX Controller", description = "API для обработки файлов .xlsx")
@Validated
public class XlsxController {

  private final XlsxService xlsxService;

  @GetMapping("/find-nth-max")
  @Operation(summary = "Найти N-е максимальное число в файле XLSX")
  public ResponseEntity<NthMaxNumberResponse> findNthMax(
      @NotBlank(message = "Путь к локальному файлу не должен быть пустым")
      @RequestParam(name = "Путь к локальному файлу") String filePath,
      @Positive(message = "N должно быть больше 0")
      @RequestParam(name = "N-ое максимальное число") int nMaxNumberFromEnd) {
    return ResponseEntity.ok(xlsxService.findNthMaxNumber(filePath, nMaxNumberFromEnd));
  }
}

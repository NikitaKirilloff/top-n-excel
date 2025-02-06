package com.kirilloffna.topnexcel.service;

import com.kirilloffna.topnexcel.entity.responce.NthMaxNumberResponse;
import com.kirilloffna.topnexcel.exception.XlsxFileNotFoundException;
import com.kirilloffna.topnexcel.exception.XlsxProcessingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.PriorityQueue;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class XlsxServiceImpl implements XlsxService {

  @Override
  public NthMaxNumberResponse findNthMaxNumber(String filePath, int n) {
    File file = new File(filePath);
    if (!file.exists() || !file.isFile()) {
      throw new XlsxFileNotFoundException(filePath);
    }

    try {
      int nthMax = findNthMaxNumberFromFile(file, n).orElseThrow(
          () -> new IllegalArgumentException("Недостаточно данных в файле")
      );
      return new NthMaxNumberResponse(nthMax, n, filePath);
    } catch (IOException e) {
      throw new XlsxProcessingException("Ошибка при обработке файла", e);
    }
  }

  private Optional<Integer> findNthMaxNumberFromFile(File file, int n) throws IOException {
    try (FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis)) {
      Sheet sheet = workbook.getSheetAt(0);
      PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);

      for (Row row : sheet) {
        for (Cell cell : row) {
          if (cell.getCellType() == CellType.NUMERIC) {
            int value = (int) cell.getNumericCellValue();
            if (minHeap.size() < n) {
              minHeap.offer(value);
            } else if (value > minHeap.peek()) {
              minHeap.poll();
              minHeap.offer(value);
            }
          }
        }
      }
      return Optional.ofNullable(minHeap.peek());
    }
  }
}

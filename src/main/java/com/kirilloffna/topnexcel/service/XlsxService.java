package com.kirilloffna.topnexcel.service;

import com.kirilloffna.topnexcel.entity.responce.NthMaxNumberResponse;

public interface XlsxService {
  NthMaxNumberResponse findNthMaxNumber(String filePath, int nMaxNumberFromEnd);
}


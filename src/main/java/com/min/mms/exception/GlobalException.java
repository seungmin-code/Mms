package com.min.mms.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException extends RuntimeException {
  /**
   * NullPointerException 처리
   */
  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", "Null Pointer Exception");
    response.put("message", "널 값이 발생했습니다.");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  /**
   * IllegalArgumentException 처리
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", "Illegal Argument Exception");
    response.put("message", "잘못된 매게변수 값입니다.");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * SQLException 처리
   */
  @ExceptionHandler(SQLException.class)
  public ResponseEntity<Map<String, Object>> handleSQLException(SQLException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", "SQL Exception");
    response.put("message", "데이터베이스 오류가 발생했습니다.");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  /**
   * DataAccessException 처리
   */
  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<Map<String, Object>> handleDataAccessException(DataAccessException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", "Data Access Exception");
    response.put("message", "데이터베이스 접근 오류가 발생했습니다.");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}

package com.malexj.exception;

public class JwtAuthenticationFilterException extends RuntimeException {
  public JwtAuthenticationFilterException(String message) {
    super(message);
  }

  public JwtAuthenticationFilterException(String message, Throwable cause) {
    super(message, cause);
  }
}

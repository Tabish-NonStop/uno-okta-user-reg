package com.learning.okta.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class NoStudentFound extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String message;
  public NoStudentFound(String message) {
    super();
    this.message = message;
  }
}

package com.learning.okta.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NoStudentFound.class)
    public ResponseEntity<ErrorResponse> noStudentFound(NoStudentFound noStudentFound, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.value(), noStudentFound.getMessage(), request.getRequestURI());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

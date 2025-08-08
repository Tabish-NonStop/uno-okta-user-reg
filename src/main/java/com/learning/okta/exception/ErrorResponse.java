package com.learning.okta.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse {
    private Date timestamp;
    private int errorCode;
    private String errorMessage;
    private String uriPath;
    public ErrorResponse(Date timestamp, int errorCode, String errorMessage,  String uriPath) {
        super();
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.uriPath = uriPath;
    }
}

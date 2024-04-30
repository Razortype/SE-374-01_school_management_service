package com.vsproject.VisualProgrammingBackend.core.results;

import org.springframework.http.HttpStatus;

public class SuccessDataResult<T> extends DataResult<T> {

    public SuccessDataResult(T data) {
        super(data, true);
    }

    public SuccessDataResult(T data, String message) {
        super(data, true, message);
    }

    public SuccessDataResult() {
        super(null, true);
    }

    public SuccessDataResult(String message) {
        super(null, true, message);
    }

    public SuccessDataResult(T data, HttpStatus httpStatus) {
        super(data, true, httpStatus);
    }

    public SuccessDataResult(T data, String message, HttpStatus httpStatus) {
        super(data, true, message, httpStatus);
    }

    public SuccessDataResult(HttpStatus httpStatus) {
        super(null, true, httpStatus);
    }

    public SuccessDataResult(String message, HttpStatus httpStatus) {
        super(null, true, message, httpStatus);
    }

}

package com.nhnacademy.edu.exception;

public class NotImageFileException extends RuntimeException {
    public NotImageFileException(String message) {
        super("파일 타입 : " + message);
    }
}

package com.nhnacademy.edu.exception;

public class FailedQueryExecuteException extends RuntimeException {
    public FailedQueryExecuteException() {
        super("데이터가 DB에 정상적으로 저장되지 않았습니다.");
    }
}

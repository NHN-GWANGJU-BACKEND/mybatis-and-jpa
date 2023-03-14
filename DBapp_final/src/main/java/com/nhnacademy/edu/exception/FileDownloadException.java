package com.nhnacademy.edu.exception;

public class FileDownloadException extends RuntimeException{
    public FileDownloadException() {
        super("파일 다운로드 중 오류가 발생했습니다.");
    }
}

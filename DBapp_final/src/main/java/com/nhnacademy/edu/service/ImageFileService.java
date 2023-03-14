package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ImageFileService {
    List<ImageFile> findImageFiles(int articleId);

    void downloadImageFile(String saveFileName, HttpServletResponse resp);

    int insertImageFile(MultipartFile[] multipartFiles, int articleId);
}

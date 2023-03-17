package com.nhnacademy.edu.service;

import com.nhnacademy.edu.entity.Article;
import com.nhnacademy.edu.entity.ImageFile;
import com.nhnacademy.edu.exception.FileDownloadException;
import com.nhnacademy.edu.exception.NotFoundFileException;
import com.nhnacademy.edu.exception.NotImageFileException;

import com.nhnacademy.edu.repository.article.ArticleRepository;
import com.nhnacademy.edu.repository.imageFile.ImageFileRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class JpaImageFileService implements ImageFileService {
    private final String UPLOAD_DIR = "/Users/taewon/Desktop";

    private final ImageFileRepository imageFileRepository;
    private final ArticleRepository articleRepository;

    public JpaImageFileService(ImageFileRepository imageFileRepository, ArticleRepository articleRepository) {
        this.imageFileRepository = imageFileRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ImageFile> findImageFiles(int articleId) {
        return imageFileRepository.findByArticle_ArticleId(articleId);
    }

    @Override
    public void downloadImageFile(String saveFileName, HttpServletResponse resp) {
        try {
            ImageFile imageFile = imageFileRepository.findBySaveFileName(saveFileName);

            String originalName = new String(imageFile.getOriginalFileName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

            String filePath = imageFile.getSaveDirectory();

            File f = new File(filePath + saveFileName);

            if (!f.isFile()) {
                throw new NotFoundFileException("해당 파일이 존재하지 않습니다.");
            }

            resp.setHeader("Content-Type", "application/octet-stream;");
            resp.setHeader("Content-Disposition", "attachment;filename=\"" + originalName + "\";");
            resp.setHeader("Content-Transfer-Encoding", "binary;");
            resp.setContentLength((int) f.length());
            resp.setHeader("Pragma", "no-cache;");
            resp.setHeader("Expires", "-1;");

            FileUtils.copyFile(f, resp.getOutputStream());
            resp.getOutputStream().close();
        } catch (Exception e) {
            throw new FileDownloadException();
        }
    }


    @Override
    @Transactional
    public int insertImageFile(MultipartFile[] multipartFiles, int articleId) {
        int insertFileCount = 0;

        for (MultipartFile file : multipartFiles) {
            if (file.getOriginalFilename().equals("")) {
                return 1;
            }

            if (!file.getContentType().startsWith("image")) {
                throw new NotImageFileException(file.getContentType());
            }

            String originalName = file.getOriginalFilename();
            UUID uuid = UUID.randomUUID();

            String uploadPath = UPLOAD_DIR + File.separator;
            String saveName = uuid + "_" + originalName;

            Path savePath = Paths.get(uploadPath + saveName);

            try {
                file.transferTo(savePath);
                Article article = articleRepository.findById(articleId).get();
                imageFileRepository.save(new ImageFile(null, article, originalName, saveName, uploadPath));
            } catch (IOException e) {
                throw new NotFoundFileException(e.getMessage());
            }
        }

        imageFileRepository.flush();
        return insertFileCount;
    }
}
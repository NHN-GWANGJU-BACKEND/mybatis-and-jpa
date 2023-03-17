package com.nhnacademy.edu.repository.imageFile;

import com.nhnacademy.edu.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile,Integer> {

    List<ImageFile> findByArticle_ArticleId(int articleId);

    ImageFile findBySaveFileName(String saveFileName);
}

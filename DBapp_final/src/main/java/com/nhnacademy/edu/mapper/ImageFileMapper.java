package com.nhnacademy.edu.mapper;

import com.nhnacademy.edu.domain.ImageFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface ImageFileMapper {
    List<ImageFile> findImageFileList(@Param("articleId") int articleId);

    ImageFile findImageFile(@Param("saveFileName") String saveFileName);

    int insertImageFile(ImageFile imageFile);
}

package com.nhnacademy.edu.repository.board;

import com.nhnacademy.edu.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BoardRepositoryCustom {
    List<Board> getUserListGreaterThanVerifyLike(Pageable pageable, String keyword);

    List<Board> getAdminListGreaterThanVerifyLike(Pageable pageable, String keyword);

    List<Board> getHeartPost(Pageable pageable, String userId);
}

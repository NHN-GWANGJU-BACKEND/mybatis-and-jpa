package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.ArticleVO;
import com.nhnacademy.edu.domain.Post;
import com.nhnacademy.edu.mapper.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MybatisArticleService implements ArticleService {
    private final ArticleMapper articleMapper;

    public MybatisArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public List<Post> findPostListUser(int page, int limit, String keyword) {
        return articleMapper.findPostListUser(limit, (page - 1) * limit, keyword);
    }

    @Override
    public List<Post> findPostListAdmin(int page, int limit, String keyword) {
        return articleMapper.findPostListAdmin(limit, (page - 1) * limit, keyword);
    }

    @Override
    public Post findPostByArticleId(int articleId) {
        return articleMapper.findPostByArticleId(articleId);
    }

    @Override
    public int getAdminTotalCount(String keyword) {
        return articleMapper.getAdminTotalCount(keyword);
    }

    @Override
    public int getUserTotalCount(String keyword) {
        return articleMapper.getUserTotalCount(keyword);
    }

    @Override
    public int insertArticle(ArticleVO articleVo) {
        return articleMapper.insertArticle(articleVo);
    }

    @Override
    public int insertPost(int articleId, String userId) {
        return articleMapper.insertPost(articleId, userId);
    }

    @Override
    public int updateArticle(ArticleVO articleVo) {
        return articleMapper.updateArticle(articleVo);
    }

    @Override
    public int updateReplyCountPlus(long articleId) {
        return articleMapper.updateReplyCountPlus(articleId);
    }

    public int updateReplyCountMinus(long articleId) {
        return articleMapper.updateReplyCountMinus(articleId);
    }

    @Override
    public int updatePost(int postId, String userId) {
        return articleMapper.updatePost(postId, userId);
    }

    @Override
    public int deletePost(long articleId) {
        return articleMapper.deletePost(articleId);
    }

    @Override
    public int restorePost(long articleId) {
        return articleMapper.restorePost(articleId);
    }
}

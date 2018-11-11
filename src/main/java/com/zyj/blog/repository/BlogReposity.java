package com.zyj.blog.repository;

import com.zyj.blog.domain.Blog;
import com.zyj.blog.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BlogReposity
 */
public interface BlogReposity extends JpaRepository<Blog, Long>{

    /**
     * 根据用户名、博客标题分页查询博客列表
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据用户名、博客查询博客列表（时间逆序）
     * @param user
     * @param tags
     * @param user2
     * @param pageable
     * @return
     */
    Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrOrderByCreateTimeDesc(User user, String tags, User user2, Pageable pageable);
}
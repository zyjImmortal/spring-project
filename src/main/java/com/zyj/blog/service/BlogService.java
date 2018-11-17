package com.zyj.blog.service;

import java.util.Optional;

import com.zyj.blog.domain.Blog;
import com.zyj.blog.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * BlogService
 */
public interface BlogService {

    Blog saveBlog(Blog blog);

    void remove(Blog blog);

    Optional<Blog> getBlogById(Long id);

    Page<Blog> listBlogByTitleVote(User user, String title, Pageable pageable);

    Page<Blog> listBlogByTitleVoteAndSort(User user, String title, Pageable pageable);

    void readingIncrease(Long id);
}
package com.zyj.blog.service;

import java.util.Optional;

import com.zyj.blog.domain.Blog;
import com.zyj.blog.domain.User;
import com.zyj.blog.repository.BlogReposity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * BlogServiceImpl
 */
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogReposity blogReposity;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog){
        Blog returnBlog = blogReposity.save(blog);
        return returnBlog;
    }

    @Transactional
    @Override
    public void remove(Blog blog) {
        blogReposity.delete(blog);
    }

    @Override
    public Optional<Blog> getBlogById(Long id) {
        return blogReposity.findById(id);
    }

	@Override
	public Page<Blog> listBlogByTitleVote(User user, String title, Pageable pageable) {
        title = "%" + title + "%";
        String tags = title;
        Page<Blog> blogs = blogReposity.findByTitleLikeAndUserOrTagsLikeAndUserOrOrderByCreateTimeDesc(user, tags, user, pageable);
		return blogs;
    }

    @Override
    public Page<Blog> listBlogByTitleVoteAndSort(User user, String title, Pageable pageable) {
        return blogReposity.findByUserAndTitleLike(user, "%" + title + "%", pageable);
    }
    
    @Override
    public void readingIncrease(Long id) {
        Optional<Blog> blog = blogReposity.findById(id);
        Blog newBlog = null;
        if (blog.isPresent()) {
            newBlog = blog.get();
            newBlog.setReadSize(newBlog.getReadSize() + 1);
            this.saveBlog(newBlog);
        }
    }

    
}
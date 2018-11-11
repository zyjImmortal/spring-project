package com.zyj.blog.repository;

import com.zyj.blog.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityReposity extends JpaRepository<Authority, Long> {

}
package com.zyj.blog.service;

import java.util.Optional;

import com.zyj.blog.domain.Authority;

public interface AuthorityService {
    Optional<Authority> getAuthorityById(Long id);
}
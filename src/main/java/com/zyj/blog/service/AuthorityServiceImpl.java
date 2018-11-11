package com.zyj.blog.service;

import java.util.Optional;

import com.zyj.blog.domain.Authority;
import com.zyj.blog.repository.AuthorityReposity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityReposity authority;

    @Override
    public Optional<Authority> getAuthorityById(Long id) {
        return authority.findById(id);
    }

}
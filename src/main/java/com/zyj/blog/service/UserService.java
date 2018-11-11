package com.zyj.blog.service;

import java.util.Optional;

import com.zyj.blog.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User saveOrUpdateUser(User user);
    User registerUser(User user);
    void removeUser(Long id);
    Optional<User> getUserById(Long id);
    Page<User> listUsersByNmaeLike(String username, Pageable pageable);
}
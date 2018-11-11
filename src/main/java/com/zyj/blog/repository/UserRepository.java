package com.zyj.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.zyj.blog.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * @msg: 根据用户姓名分页查询用户列表
     * @param {type} 
     * @return: 
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    User findByUsername(String username);
}

package com.zyj.blog.repository;

import com.zyj.blog.domain.File;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * FileReposity
 */
public interface FileReposity extends MongoRepository<File, String>{
 
}
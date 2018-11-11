package com.zyj.blog.service;

import java.util.List;
import java.util.Optional;

import com.zyj.blog.domain.File;

/**
 * FileService
 */
public interface FileService {

    File saveFile(File file);
    void removeFile(File file);
    Optional<File> getFileById(String id);
    List<File> listFilesByPage(int pageIndex, int pageSize);
}
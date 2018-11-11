package com.zyj.blog.service;

import java.util.List;
import java.util.Optional;

import com.zyj.blog.domain.File;
import com.zyj.blog.repository.FileReposity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * FileServiceImpl
 */
public class FileServiceImpl implements FileService{

    @Autowired
    private FileReposity fileReposity;

    @Override
    public File saveFile(File file) {
        return fileReposity.save(file);
    }

    @Override
    public void removeFile(File file) {
        fileReposity.delete(file);
    }

    @Override
    public Optional<File> getFileById(String id) {
        return fileReposity.findById(id);
    }

    @Override
    public List<File> listFilesByPage(int pageIndex, int pageSize) {
        Page<File> page = null;
        List<File> list = null;
        Sort sort = new Sort(Direction.DESC, "uploadDate");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        page = fileReposity.findAll(pageable);
        list = page.getContent();
        return list;
    }
}
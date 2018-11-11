package com.zyj.blog.domain;

import java.util.Date;
import javax.persistence.Id;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * File
 */

@Document
public class File {

    @Id
    private String id;
    private String name;
    private String contentType; // 文件类型
    private long size;
    private Date uploadDate;
    private String md5;
    private Binary content;
    private String path;

    protected File() {
    }

    public File(String name, String contentType, long size, Binary content) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.content = content;
    }

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return long return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @return Date return the uploadDate
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * @return String return the md5
     */
    public String getMd5() {
        return md5;
    }

    /**
     * @param md5 the md5 to set
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    /**
     * @return Binary return the content
     */
    public Binary getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(Binary content) {
        this.content = content;
    }

    /**
     * @return String return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        File fileInfo = (File) obj;
        return java.util.Objects.equals(size, fileInfo.getSize()) && java.util.Objects.equals(name, fileInfo.getName())
                && java.util.Objects.equals(contentType, fileInfo.getContentType())
                && java.util.Objects.equals(uploadDate, fileInfo.getUploadDate())
                && java.util.Objects.equals(md5, fileInfo.getMd5()) && java.util.Objects.equals(id, fileInfo.getId());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, contentType, size, uploadDate, md5, id);
    }

    @Override
    public String toString() {
        return "File(" + "name='" + name + '\'' + ", contentType='" + contentType + '\'' + ", size='" + size
                + ", uploadDate='" + uploadDate + ", md5='" + md5 + '\'' + ", id='" + id + '\'' + '}';
    }

}
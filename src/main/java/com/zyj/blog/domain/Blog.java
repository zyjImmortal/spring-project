package com.zyj.blog.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.github.rjeschke.txtmark.Processor;

import org.hibernate.annotations.CreationTimestamp;

/**
 * Blog
 */

@Entity
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    @NotEmpty(message = "摘要不能为空")
    @Size(min = 2, max = 500)
    @Column(nullable = false)
    private String summary;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String htmlContent;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY) // 文章和人是一对一的关系
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @CreationTimestamp // 有数据库自动创建时间
    private Timestamp createTime;

    @Column(name = "readSize")
    private Integer readSize;

    @Column(name = "commentSize")
    private Integer commentSize;

    @Column(name = "voteSize")
    private Integer voteSize;

    @Column(name = "tags", length = 100)
    private String tags;

    protected Blog() {
    }

    public Blog(String title, String summary, String content) {
        this.title = title;
        this.content = content;
        this.summary = summary;
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return String return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return String return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
        this.htmlContent = Processor.process(content);
    }

    /**
     * @return String return the htmlContent
     */
    public String getHtmlContent() {
        return htmlContent;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return Timestamp return the createTime
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * @return Integer return the readSize
     */
    public Integer getReadSize() {
        return readSize;
    }

    /**
     * @param readSize the readSize to set
     */
    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    /**
     * @return Integer return the commentSize
     */
    public Integer getCommentSize() {
        return commentSize;
    }

    /**
     * @param commentSize the commentSize to set
     */
    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    /**
     * @return Integer return the voteSize
     */
    public Integer getVoteSize() {
        return voteSize;
    }

    /**
     * @param voteSize the voteSize to set
     */
    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    /**
     * @return String return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

}
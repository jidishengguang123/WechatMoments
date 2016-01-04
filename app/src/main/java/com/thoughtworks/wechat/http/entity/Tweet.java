package com.thoughtworks.wechat.http.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 11:07
 * Description:
 */
public class Tweet implements Serializable{
    private static final long serialVersionUID = 6089913610916224957L;
    /**
     * 发表内容
     */
    private String content;
    /**
     * 上传照片
     */
    private List<UrlEntity> images;
    /**
     * 发布者信息
     */
    private UserInfo sender;
    /**
     * 评论
     */
    private List<Comment> comments;
    /**
     * 错误信息
     */
    private String error;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<UrlEntity> getImages() {
        return images;
    }

    public void setImages(List<UrlEntity> images) {
        this.images = images;
    }

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "content='" + content + '\'' +
                ", images=" + images +
                ", sender=" + sender +
                ", comments=" + comments +
                ", error='" + error + '\'' +
                '}';
    }
}

package com.thoughtworks.wechat.http.entity;

import java.io.Serializable;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 11:28
 * Description:
 */
public class Comment implements Serializable{

    private static final long serialVersionUID = 4328423025809148817L;

    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论发布者
     */
    private UserInfo sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", sender=" + sender +
                '}';
    }
}

package com.thoughtworks.wechat.http.entity;

import java.io.Serializable;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 10:28
 * Description:
 */
public class UserInfo implements Serializable,Cloneable{
    private static final long serialVersionUID = 8716275973843301325L;
    private String profile_image;
    /**
     * 用户图像
     */
    private String avatar;
    /**
     * 用户昵称
     */
    private String nick;
    /**
     * 用户名
     */
    private String username;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() {
        return profile_image;
    }

    public void setProfileImage(String profile_image) {
        this.profile_image = profile_image;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "profile_image='" + profile_image + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nick='" + nick + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public UserInfo clone() throws CloneNotSupportedException {
        UserInfo info = null;
        try {
            info = (UserInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            info =  null;
        }
        return info;
    }

}

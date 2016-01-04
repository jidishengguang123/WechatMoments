package com.thoughtworks.wechat.http.entity;

import java.io.Serializable;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 11:29
 * Description:
 */
public class UrlEntity implements Serializable{

    private static final long serialVersionUID = 8595633843730006415L;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UrlEntity{" +
                "url='" + url + '\'' +
                '}';
    }

}

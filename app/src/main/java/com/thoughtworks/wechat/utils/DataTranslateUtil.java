package com.thoughtworks.wechat.utils;

import com.thoughtworks.wechat.http.entity.UrlEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-21
 * Time: 16:47
 * Description:
 */
public class DataTranslateUtil {
    public static ArrayList<String> translateImageUrls(List<UrlEntity> list){
        ArrayList<String> urls = new ArrayList<String>();
        for (UrlEntity entity:list) {
            urls.add(entity.getUrl());
        }
        return urls;
    }
}

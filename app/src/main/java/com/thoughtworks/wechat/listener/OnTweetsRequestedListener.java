package com.thoughtworks.wechat.listener;

import com.android.volley.VolleyError;
import com.thoughtworks.wechat.http.entity.Tweet;

import java.util.List;

/**
 * Tweet数据获取监听
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 12:27
 */
public interface OnTweetsRequestedListener extends OnRequestListener<List<Tweet>>{
    @Override
    void onRequested(List<Tweet> list, VolleyError error);
}

package com.thoughtworks.wechat.http.request;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.thoughtworks.wechat.http.entity.Tweet;

import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 11:40
 * Description:
 */
public class TweetsRequest extends GetJsonRequest<List>{


    /***
     * 构造以Get方式访问的接口的JsonRequest
     *  @param url
     * @param cls
     * @param listener
     * @param errorListener
     */
    public TweetsRequest(String url, Class<List> cls, Response.Listener<List> listener, Response.ErrorListener errorListener) {
        super(url, cls, listener, errorListener);
    }

    @Override
    public List<Tweet> parse(String json, Class<List> t) {
        return JSON.parseArray(json,Tweet.class);
    }
}

package com.thoughtworks.wechat.utils;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.thoughtworks.wechat.http.entity.UserInfo;
import com.thoughtworks.wechat.http.request.TweetsRequest;
import com.thoughtworks.wechat.http.request.UserInfoRequest;

import java.util.List;

public class DataController {

    private static DataController sInstance;

    private RequestQueue mQueue;

    private DataController(){

    }

    public synchronized static DataController getsInstance() {
        if (sInstance == null){
            sInstance = new DataController();
        }
        return sInstance;
    }

    public void initData(Application app){
        mQueue= Volley.newRequestQueue(app);
    }

    /**
     * 获取用户信息
     * @param url
     * @param listener
     * @param errorListener
     */
    public void requestUserInfo(String url,Response.Listener<UserInfo> listener, Response.ErrorListener errorListener) {
        UserInfoRequest request = new UserInfoRequest(url,UserInfo.class,listener,errorListener);
        mQueue.add(request);
    }

    /**
     * 获取评论
     * @param url
     * @param listener
     * @param errorListener
     */
    public void requestTweets(String url,Response.Listener<List> listener, Response.ErrorListener errorListener){
        TweetsRequest request = new TweetsRequest(url,List.class,listener,errorListener);
        mQueue.add(request);
    }

}

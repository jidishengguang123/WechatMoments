package com.thoughtworks.wechat.http.request;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.thoughtworks.wechat.http.entity.UserInfo;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 11:35
 * Description:
 */
public class UserInfoRequest extends GetJsonRequest<UserInfo>{
    /***
     * 构造以Get方式访问的接口的JsonRequest
     *
     * @param url
     * @param cls
     * @param listener
     * @param errorListener
     */
    public UserInfoRequest(String url, Class<UserInfo> cls, Response.Listener<UserInfo> listener, Response.ErrorListener errorListener) {
        super(url, cls, listener, errorListener);
    }

    @Override
    public UserInfo parse(String json, Class<UserInfo> t) {
        //由于profile-image不符合java命名规范，fastjson无法解析，所以将profile-image替换成profile_image
        if (json.contains("profile-image")){
            Log.d("UserInfoRequest","tag1-----json = "+json);
            json = json.replace("profile-image", "profile_image");
            Log.d("UserInfoRequest", "tag2-----json = " + json);
        }
        return JSON.parseObject(json,t);
    }
}

package com.thoughtworks.wechat.listener;

import com.android.volley.VolleyError;
import com.thoughtworks.wechat.http.entity.UserInfo;

/**
 * 用户信息获取监听
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 12:26
 */
public interface OnUserInfoRequestedListener extends OnRequestListener<UserInfo>{
    @Override
    void onRequested(UserInfo info, VolleyError error);
}

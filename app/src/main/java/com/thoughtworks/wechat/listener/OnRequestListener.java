package com.thoughtworks.wechat.listener;

import com.android.volley.VolleyError;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 13:27
 * Description:
 */
public interface OnRequestListener<T> {
    void onRequested(T t,VolleyError error);
}

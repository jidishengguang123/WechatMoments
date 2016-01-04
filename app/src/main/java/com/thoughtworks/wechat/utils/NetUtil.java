package com.thoughtworks.wechat.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.thoughtworks.wechat.WeChatApp;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-21
 * Time: 17:42
 * Description:
 */
public class NetUtil {
    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) WeChatApp.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}

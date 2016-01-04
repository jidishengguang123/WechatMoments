package com.thoughtworks.wechat.dataprovider;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.thoughtworks.wechat.http.HttpConstants;
import com.thoughtworks.wechat.http.entity.UserInfo;
import com.thoughtworks.wechat.listener.OnRequestListener;
import com.thoughtworks.wechat.utils.DataController;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 12:22
 * Description:
 */
public class UserInfoDataprovider extends BaseDataProvider<UserInfo>{
    protected static UserInfoDataprovider sInstance;

    private UserInfoDataprovider() {

    }

    public synchronized static UserInfoDataprovider getInstance(){
        if (sInstance == null){
            sInstance = new UserInfoDataprovider();
        }
        return sInstance;
    }

    @Override
    protected void onRequested(OnRequestListener<UserInfo> listener, UserInfo info) {
        try {
            listener.onRequested(info != null ? info.clone() : null,mVolleyError);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            listener.onRequested(info, mVolleyError);
        }
    }

    @Override
    public void initData(){
        Log.d(TAG,"UserInfoDataprovider initData");
        DataController.getsInstance().requestUserInfo(HttpConstants.USER_INFO_URL, new Response.Listener<UserInfo>() {
            @Override
            public void onResponse(UserInfo userInfo) {
                Log.d(TAG, "userInfo = " + userInfo);
                t = userInfo;
                notifyAllOnRequestedListener();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "volleyError = " + volleyError);
                mVolleyError = volleyError;
                notifyAllOnRequestedListener();
            }
        });
    }

}

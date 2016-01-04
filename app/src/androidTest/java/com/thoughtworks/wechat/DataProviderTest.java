package com.thoughtworks.wechat;

import android.test.AndroidTestCase;
import android.util.Log;

import com.android.volley.VolleyError;
import com.thoughtworks.wechat.dataprovider.TweetsDataprovider;
import com.thoughtworks.wechat.dataprovider.UserInfoDataprovider;
import com.thoughtworks.wechat.http.entity.Tweet;
import com.thoughtworks.wechat.http.entity.UserInfo;
import com.thoughtworks.wechat.listener.OnRequestListener;
import com.thoughtworks.wechat.listener.OnTweetsRequestedListener;

import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-21
 * Time: 22:46
 * Description:
 */
public class DataProviderTest extends AndroidTestCase {
    private static final String TAG = "DataProviderTest";

    public void testTweetsDataProvider(){
        TweetsDataprovider.getInstance().addOnRequestedListener(new OnTweetsRequestedListener() {
            @Override
            public void onRequested(List<Tweet> list, VolleyError error) {
                Log.d(TAG,"list = "+list+",error = "+error);
            }
        });
        TweetsDataprovider.getInstance().initData();
    }

    public void testUserInfoDataprovider(){
        UserInfoDataprovider.getInstance().addOnRequestedListener(new OnRequestListener<UserInfo>() {
            @Override
            public void onRequested(UserInfo userInfo, VolleyError error) {
                Log.d(TAG,"userInfo = "+userInfo+",error = "+error);
            }
        });
        UserInfoDataprovider.getInstance().initData();
    }
}

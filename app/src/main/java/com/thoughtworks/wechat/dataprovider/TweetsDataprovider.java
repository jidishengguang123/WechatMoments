package com.thoughtworks.wechat.dataprovider;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.thoughtworks.wechat.http.HttpConstants;
import com.thoughtworks.wechat.http.entity.Tweet;
import com.thoughtworks.wechat.listener.OnRequestListener;
import com.thoughtworks.wechat.utils.DataController;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 12:22
 * Description:
 */
public class TweetsDataprovider extends BaseDataProvider<List<Tweet>> {

    protected static TweetsDataprovider sInstance;

    private TweetsDataprovider() {

    }

    public synchronized static TweetsDataprovider getInstance(){
        if (sInstance == null){
            sInstance = new TweetsDataprovider();
        }
        return sInstance;
    }

    @Override
    protected void onRequested(OnRequestListener<List<Tweet>> listener, List<Tweet> list) {
        if (listener == null || list == null){
            return;
        }
        List<Tweet> copyList = new ArrayList<Tweet>();
        Tweet t;
        for (int i = 0; i < list.size(); i++) {
            t = list.get(i);
            if (t.getContent() == null && (t.getImages() == null || t.getImages().size() <= 0)){
                continue;
            }
            copyList.add(t);
        }
        copyList.addAll(list);
        listener.onRequested(copyList,mVolleyError);
    }

    @Override
    public void initData() {
        Log.d(TAG,"TweetsDataprovider initData");
        DataController.getsInstance().requestTweets(HttpConstants.TWEETS_URL, new Response.Listener<List>() {
            @Override
            public void onResponse(List list) {
                Log.d(TAG, "list = " + list);
                t = list;
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

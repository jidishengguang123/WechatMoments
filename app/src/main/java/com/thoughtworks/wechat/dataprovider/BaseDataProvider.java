package com.thoughtworks.wechat.dataprovider;

import com.android.volley.VolleyError;
import com.thoughtworks.wechat.WeChatApp;
import com.thoughtworks.wechat.constant.LoadState;
import com.thoughtworks.wechat.listener.OnRequestListener;

import java.util.Vector;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 12:23
 * Description:
 */
public abstract class BaseDataProvider<T> {
    protected final String TAG = BaseDataProvider.class.getSimpleName();

    protected T t;

    protected VolleyError mVolleyError;

    protected Vector<OnRequestListener<T>> mListeners = new Vector<OnRequestListener<T>>();

    protected LoadState mLoadState = LoadState.LOADSTATE_IDLE;

    protected int getStatusCode(){
        if (mVolleyError != null && mVolleyError.networkResponse != null){
            return mVolleyError.networkResponse.statusCode;
        }
        return -1;
    }

    protected abstract void onRequested(OnRequestListener<T> listener,T t);

    public void addOnRequestedListener(OnRequestListener<T> listener){
        if (listener == null){
            return;
        }
        if (!mListeners.contains(listener)){
            mListeners.add(listener);
        }
        if (mLoadState != LoadState.LOADSTATE_LOADING && t != null){
            if (getStatusCode() == 200 && t != null){
                onRequested(listener,t);
            }else {
                listener.onRequested(null,mVolleyError);
            }
        }
    }

    public void removeOnRequestedListener(OnRequestListener<T> listener){
        if (listener == null){
            return;
        }
        if (mListeners.contains(listener)){
            mListeners.remove(listener);
        }
    }

    protected void notifyAllOnRequestedListener(){
        WeChatApp.sUiHandler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mListeners.size(); i++) {
                    onRequested(mListeners.get(i),t);
                }
            }
        });
    }

    protected abstract void initData();
}

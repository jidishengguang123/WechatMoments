package com.thoughtworks.wechat.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.WeChatApp;
import com.thoughtworks.wechat.adapter.MomentsAdapter;
import com.thoughtworks.wechat.dataprovider.TweetsDataprovider;
import com.thoughtworks.wechat.dataprovider.UserInfoDataprovider;
import com.thoughtworks.wechat.http.entity.Tweet;
import com.thoughtworks.wechat.http.entity.UserInfo;
import com.thoughtworks.wechat.listener.OnLoadListener;
import com.thoughtworks.wechat.listener.OnRefreshListener;
import com.thoughtworks.wechat.listener.OnTweetsRequestedListener;
import com.thoughtworks.wechat.listener.OnUserInfoRequestedListener;
import com.thoughtworks.wechat.view.CustomProgressDialog;
import com.thoughtworks.wechat.view.MomentsListView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements OnRefreshListener,OnLoadListener{
    private static final String TAG = "HomeActivity";
    /**
     * 自定义进度加载框
     */
    private CustomProgressDialog progressDialog = null;
    /**
     * Moments展示列表
     */
    private MomentsListView mMomentsListView;
    /**
     *全部Tweet数据
     */
    private List<Tweet> mAllTweets;
    /**
     * 正在显示的Tweet数据
     */
    private List<Tweet> mCurrentShowTweets = new ArrayList<Tweet>();
    /**
     * Moments数据适配器
     */
    private MomentsAdapter mCommentsAdapter;
    /**
     * 正在加载的网络任务数量，用于控制Loading框的显示
     */
    private int mTaskCount;
    /**
     * mAllTweets中上次获取item的位置
     */
    private int mLastPos;

    private OnUserInfoRequestedListener mOnUserInfoRequestedListener = new OnUserInfoRequestedListener() {
        @Override
        public void onRequested(UserInfo info, VolleyError error) {
            //网络任务结束，计数器-1
            mTaskCount--;
            //计数器<=0时，说明网络任务结束，此时需要隐藏进度框
            if (mTaskCount <= 0){
                stopProgressDialog();
            }
            Log.d(TAG, "info = " + info + ",error = " + error);
            mMomentsListView.setUserInfo(info);
        }
    };

    private OnTweetsRequestedListener mOnTweetsRequestedListener = new OnTweetsRequestedListener() {
        @Override
        public void onRequested(List<Tweet> list, VolleyError error) {
            mTaskCount--;
            if (mTaskCount <= 0){
                stopProgressDialog();
            }
            Log.d(TAG, "list = " + list + ",error = " + error);
            mAllTweets = list;
            initMomentsListData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initView(){
        mMomentsListView = (MomentsListView) findViewById(R.id.moments_list);
    }

    private void initData(){
        startProgressDialog();
        UserInfoDataprovider.getInstance().addOnRequestedListener(mOnUserInfoRequestedListener);
        mTaskCount++;
        TweetsDataprovider.getInstance().addOnRequestedListener(mOnTweetsRequestedListener);
        mTaskCount++;
        mMomentsListView.setOnLoadListener(this);
        mMomentsListView.setOnRefreshListener(this);
    }


    /**
     * 初始化Moments列表数据
     */
    private void initMomentsListData(){
        //第一次最多加载5条数据
        if (mAllTweets.size() >= mLastPos + MomentsListView.DEFAULT_LOAD_COUNT){
            mCurrentShowTweets.addAll(mAllTweets.subList(mLastPos, MomentsListView.DEFAULT_LOAD_COUNT));
        }else {
            mCurrentShowTweets.addAll(mAllTweets.subList(mLastPos, mAllTweets.size()));
        }
        mLastPos = mCurrentShowTweets.size() - 1;
        mCommentsAdapter = new MomentsAdapter(this, mCurrentShowTweets);
        mMomentsListView.setAdapter(mCommentsAdapter);
        mMomentsListView.setResultSize(mLastPos + 1);
    }

    /**
     *  显示Loading加载框
     */
    private void startProgressDialog(){
        if (progressDialog == null){
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage(getString(R.string.loading_msg));
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Log.d(TAG,"onCancel....");
                    finish();
                }
            });
        }
        progressDialog.show();
    }

    /**
     * 隐藏Loading加载框
     */
    private void stopProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProgressDialog();
        UserInfoDataprovider.getInstance().removeOnRequestedListener(mOnUserInfoRequestedListener);
        TweetsDataprovider.getInstance().removeOnRequestedListener(mOnTweetsRequestedListener);
        ((WeChatApp)getApplication()).finishApp();
    }

    @Override
    public void onLoad() {
        //此处模拟分页加载过程
        WeChatApp.sUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAllTweets.size() > mLastPos + MomentsListView.DEFAULT_LOAD_COUNT){
                    mCurrentShowTweets.addAll(mAllTweets.subList(mLastPos, MomentsListView.DEFAULT_LOAD_COUNT));
                }else {
                    mCurrentShowTweets.addAll(mAllTweets.subList(mLastPos, mAllTweets.size()));
                }
                mCommentsAdapter.notifyDataSetChanged();
                mMomentsListView.onLoadComplete(mCurrentShowTweets.size() - mLastPos - 1);
                mLastPos = mCurrentShowTweets.size() - 1;
            }
        },1000);
    }

    @Override
    public void onRefresh() {
        //模拟上拉刷新
        WeChatApp.sUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMomentsListView.onRefreshComplete();
            }
        },2000);
    }
}

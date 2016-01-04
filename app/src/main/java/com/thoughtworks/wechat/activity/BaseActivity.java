package com.thoughtworks.wechat.activity;

import android.app.Activity;
import android.os.Bundle;

import com.thoughtworks.wechat.utils.ActivityManager;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-21
 * Time: 21:35
 * Description:
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().onCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().onDestroy(this);
    }
}

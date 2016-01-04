package com.thoughtworks.wechat;

import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<WeChatApp> {
    private WeChatApp mWeChatApp;

    public ApplicationTest() {
        super(WeChatApp.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //获取application之前必须调用的方法
        createApplication();
        //获取待测试的mWeChatApp
        mWeChatApp = getApplication();
    }

    //测试结束应用的方法
    public void testCheckNetWork(){
        mWeChatApp.checkNetWork();
    }
}
package com.thoughtworks.wechat.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.adapter.SamplePagerAdapter;
import com.thoughtworks.wechat.view.HackyViewPager;

import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-21
 * Time: 15:55
 * Description:
 */
public class ViewPagerActivity extends BaseActivity{
    private static final String ISLOCKED_ARG = "isLocked";

    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        List<String> urls = getIntent().getStringArrayListExtra("urls");
        int selected = getIntent().getIntExtra("selected",0);
        mViewPager.setAdapter(new SamplePagerAdapter(urls));
        mViewPager.setCurrentItem(selected);
        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
            ((HackyViewPager) mViewPager).setLocked(isLocked);
        }
    }

    private boolean isViewPagerActive() {
        return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }
}

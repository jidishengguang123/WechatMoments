package com.thoughtworks.wechat.scaleview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by liyang on 2014/5/30.
 */
public class ScaleListView extends ListView {
    private boolean isOnMeasure;
    // listview是否在滚动
    private boolean isScrolling;

    public ScaleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScaleListView(Context context) {
        super(context);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction,
            Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.isOnMeasure = true;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.isOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }

    public boolean isOnMeasure() {
        return this.isOnMeasure;
    }

    public boolean isScrolling() {
        return this.isScrolling;
    }

    public void setScrolling(boolean isScrolling) {
        this.isScrolling = isScrolling;
    }

}

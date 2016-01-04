package com.thoughtworks.wechat.scaleview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by liyang on 2014/5/30.
 */
public class ScaleGridView extends GridView {
    private boolean isOnMeasure;

    public ScaleGridView(Context context) {
        super(context);
    }

    public ScaleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            ScaleCalculator.getInstance().scaleViewGroup(this);
        } catch (Exception e) {

        }
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

    boolean shouldShowSelector() {
        return (this.hasFocus() && !this.isInTouchMode());
    }
}

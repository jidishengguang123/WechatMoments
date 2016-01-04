package com.thoughtworks.wechat.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.constant.LoadState;
import com.thoughtworks.wechat.http.entity.UserInfo;
import com.thoughtworks.wechat.listener.OnLoadListener;
import com.thoughtworks.wechat.listener.OnRefreshListener;
import com.thoughtworks.wechat.scaleview.ScaleListView;
import com.thoughtworks.wechat.utils.ProgressAnimationUtil;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-18
 * Time: 13:20
 * Description:
 */
public class MomentsListView extends ScaleListView implements AbsListView.OnScrollListener,View.OnTouchListener{
    private static final String TAG = MomentsListView.class.getSimpleName();
    /**
     * 每次加载数据条数
     */
    public static final int DEFAULT_LOAD_COUNT = 5;
    /*-------------handler message what---------------------*/
    private static final int DECREASE_HEADVIEW_PADDING = 1;
    private static final int LOAD_DATA = 2;
    private static final int DISMISS_CIRCLE = 3;
    /**
     * ListView的HeaderView
     */
    private View mHeaderView;
    /**
     * 数据加载时的LoadingView
     */
    private View mProgressView;

    private ImageView mThemeImage;

    private TextView mUserName;

    private ImageView mUserIcon;

    private View mFooterView;

    private LinearLayout mLoadingMoreLayout;

    private TextView mFullLoad;

    private TextView mNodata;

    /**
     * 手指按下时Y坐标值
     */
    private float lastDownY;
    /**
     * 当前HeaderView离顶端的距离
     */
    private int deltaCount;
    /**
     * 当前数据加载状态
     */
    private LoadState mCurrentLoadingState;
    /**
     * ListView当前滚动状态
     */
    private int mCurrentScrollState;
    /**
     * ListView第一个可见的Item
     */
    private int mFirstVisibleItem;

    private int mProgressViewMarginTop;

    private int mHeaderViewHeight;

    private UserInfo mUserInfo;

    private boolean isLoadFull;

    private int pageSize = DEFAULT_LOAD_COUNT;

    private OnLoadListener mOnLoadListener;

    private OnRefreshListener mOnRefreshListener;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DECREASE_HEADVIEW_PADDING:
                    setHeaderViewPaddingTop(deltaCount);
                    setProgressViewMargin();
                    break;
                case LOAD_DATA:
                    Thread thread = new Thread(new DismissCircleThread());
                    thread.start();
                    mCurrentLoadingState = LoadState.LOADSTATE_IDLE;
                    if (getAdapter() != null && getAdapter() instanceof BaseAdapter) {
                        ((BaseAdapter)getAdapter()).notifyDataSetChanged();
                    }
                    break;
                case DISMISS_CIRCLE:
                    int margin = msg.arg1;
                    setProgressViewMargin(margin);
                    if (margin == 0) {
                        ProgressAnimationUtil.stopRotateAnmiation(mProgressView);
                    }
                    break;
            }
        }
    };

    public MomentsListView(Context context) {
        super(context);
        init(context);
    }

    public MomentsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MomentsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        initHeadView(context);
        initFooterView(context);
        setListener();
    }

    private void initHeadView(Context context) {
        mHeaderView = LayoutInflater.from(context).inflate(R.layout.layout_header, null);
        addHeaderView(mHeaderView);
        mProgressView = mHeaderView.findViewById(R.id.progress_image);
        mThemeImage = (ImageView) mHeaderView.findViewById(R.id.theme_image);
        mUserName = (TextView) mHeaderView.findViewById(R.id.user_name);
        mUserIcon = (ImageView) mHeaderView.findViewById(R.id.user_icon);
        mHeaderView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        if (mHeaderView.getMeasuredHeight() > 0) {
                            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
                            mHeaderView.getViewTreeObserver()
                                    .removeOnPreDrawListener(this);
                        }
                        return true;
                    }
                });
        setOnScrollListener(this);
        mCurrentScrollState = OnScrollListener.SCROLL_STATE_IDLE;
        mCurrentLoadingState = LoadState.LOADSTATE_IDLE;
        mFirstVisibleItem = 0;
        mProgressViewMarginTop = (int) getContext().getResources().getDimension(R.dimen.header_progressView_margin_top);
        setSelector(new ColorDrawable(Color.TRANSPARENT));
        setItemsCanFocus(true);
    }

    private void initFooterView(Context context){
        mFooterView = LayoutInflater.from(context).inflate(R.layout.layout_footer,null);
        mLoadingMoreLayout = (LinearLayout) mFooterView.findViewById(R.id.loading_more_layout);
        mFullLoad = (TextView) mFooterView.findViewById(R.id.full_load_hint);
        mNodata = (TextView) mFooterView.findViewById(R.id.no_data_hint);
        addFooterView(mFooterView);
    }

    /**
     * 设置用户信息
     * @param info
     */
    public void setUserInfo(UserInfo info){
        if (info == null){
            return;
        }
        mUserInfo = info;
        mUserName.setText(mUserInfo.getUsername());
        ImageLoader.getInstance().displayImage(mUserInfo.getProfileImage(),mThemeImage);
        ImageLoader.getInstance().displayImage(mUserInfo.getAvatar(),mUserIcon);
    }

    private void setListener(){
        setOnTouchListener(this);
        setOnScrollListener(this);
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float downY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录手指按下的位置
                lastDownY = downY;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float downY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                /**
                 * 1.deltaCount>0：也就是它是要往下拉的，而如果当Down和Move之间的距离小于0，那么说明它在往上滑，那么就不用设置HeaderView的PaddingTop了
                 * 2.currentState!=LoadState.LOADSTATE_LOADING：当现在的状态没有加载数据的时候，才允许下拉刷新。
                 * 3.firstVisibleItem==0：只有当用户看到了HeaderView的时候才可以请求数据。
                 * 4.mHeaderView.getBottom() >= mHeadViewHeight：只有HeaderView bottom大于HeaderView高度时，说明ListView下拉了
                 */
                if (deltaCount > 0 && mCurrentLoadingState != LoadState.LOADSTATE_LOADING
                        && mFirstVisibleItem == 0 && mHeaderView.getBottom() >= mHeaderViewHeight) {
                    decreaseHeaderViewPadding(deltaCount);
                    loadData();
                    startProgressAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int nowDeltaCount = (int) ((downY - lastDownY) / 3.0);
                int grepDegree = nowDeltaCount - deltaCount;
                deltaCount = nowDeltaCount;
                if (deltaCount > 0 && mCurrentLoadingState != LoadState.LOADSTATE_LOADING
                        && mFirstVisibleItem == 0 && mHeaderView.getBottom() >= mHeaderViewHeight) {
                    setHeaderViewPaddingTop(deltaCount);
                    setProgressViewStay();
                    ProgressAnimationUtil.startCWAnimation(mProgressView, 5 * (deltaCount - grepDegree), 5 * deltaCount);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mCurrentScrollState = scrollState;
        ifNeedLoad(view,scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.mFirstVisibleItem = firstVisibleItem;
    }

    /**
     * 根据listview滑动的状态判断是否需要加载更多
     */
    private void ifNeedLoad(AbsListView view, int scrollState) {
        Log.d(TAG,"view.getLastVisiblePosition()="+view.getLastVisiblePosition()+",view.getChildCount()="+view.getChildCount());
        if(mCurrentLoadingState != LoadState.LOADSTATE_LOADING
                && view.getLastVisiblePosition() >= view.getChildCount()
                && !isLoadFull){
            onLoad();
        }
    }

    /**
     * 这个方法是根据结果的大小来决定footer显示的。
     * <p>
     * 这里假定每次请求的条数为10。如果请求到了10条。则认为还有数据。如过结果不足10条，则认为数据已经全部加载，这时footer显示已经全部加载
     * </p>
     *
     * @param resultSize
     */
    public void setResultSize(int resultSize) {
        Log.d(TAG,"resultSize = "+resultSize);
        if (resultSize == 0) {
            isLoadFull = true;
            mFullLoad.setVisibility(View.GONE);
            mLoadingMoreLayout.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
        } else if (resultSize > 0 && resultSize < pageSize) {
            isLoadFull = true;
            mFullLoad.setVisibility(View.VISIBLE);
            mLoadingMoreLayout.setVisibility(View.GONE);
            mNodata.setVisibility(View.GONE);
        } else if (resultSize == pageSize) {
            isLoadFull = false;
            mFullLoad.setVisibility(View.GONE);
            mLoadingMoreLayout.setVisibility(View.VISIBLE);
            mNodata.setVisibility(View.GONE);
        }

    }

    private void onLoad() {
        Log.d(TAG,"onLoad...............");
        mCurrentLoadingState = LoadState.LOADSTATE_LOADING;
        if (mOnLoadListener != null) {
            mOnLoadListener.onLoad();
        }else {
            mHandler.sendEmptyMessageDelayed(LOAD_DATA,3000);
        }
    }

    private void onRefresh(){
        if (mOnRefreshListener != null){
            mOnRefreshListener.onRefresh();
        }
    }

    // 用于下拉刷新结束后的回调
    public void onRefreshComplete() {
        mCurrentLoadingState = LoadState.LOADSTATE_IDLE;
        mHandler.sendEmptyMessage(LOAD_DATA);
    }


    // 用于加载更多结束后的回调
    public void onLoadComplete(int resultSize) {
        mCurrentLoadingState = LoadState.LOADSTATE_IDLE;
        setResultSize(resultSize);
    }

    private void setProgressViewMargin() {
        MarginLayoutParams lp = (MarginLayoutParams) mProgressView.getLayoutParams();
        lp.topMargin = mProgressViewMarginTop - mHeaderView.getPaddingTop();
        mProgressView.setLayoutParams(lp);
    }

    private void setProgressViewMargin(int margin){
        MarginLayoutParams lp = (MarginLayoutParams) mProgressView.getLayoutParams();
        lp.topMargin = margin;
        mProgressView.setLayoutParams(lp);
    }

    /**
     * 通过设置HeaderView的PaddingTop值，实现下拉的效果
     * @param deltaY
     */
    private void setHeaderViewPaddingTop(int deltaY) {
        mHeaderView.setPadding(0, deltaY, 0, 0);
    }

    private void setProgressViewStay() {
        if (mHeaderView.getPaddingTop() > mProgressViewMarginTop) {
            MarginLayoutParams lp = (MarginLayoutParams) mProgressView.getLayoutParams();
            lp.topMargin = mProgressViewMarginTop - mHeaderView.getPaddingTop();
            mProgressView.setLayoutParams(lp);
        }
    }

    private void decreaseHeaderViewPadding(int count) {
        Thread thread = new Thread(new DecreaseHeaderViewPaddingThread(count));
        thread.start();
    }

    private void loadData() {
        mCurrentLoadingState = LoadState.LOADSTATE_LOADING;
        onRefresh();
    }

    private void startProgressAnimation(){
        ProgressAnimationUtil.startRotateAnimation(mProgressView);
    }

    /**
     * 这个线程，会分25次来减少当前的deltaCount，并且使用Handler来发送消息，告诉HeaderView该刷新PaddingTop了
     */
    private class DecreaseHeaderViewPaddingThread implements Runnable {
        private final static int TIME = 25;
        private int decrease_length;

        public DecreaseHeaderViewPaddingThread(int count) {
            decrease_length = count / TIME;
        }

        @Override
        public void run() {
            for (int i = 0; i < TIME; i++) {
                if (i == TIME - 1) {
                    deltaCount = 0;
                } else {
                    deltaCount = deltaCount - decrease_length;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                }
                Message msg = mHandler.obtainMessage(DECREASE_HEADVIEW_PADDING);
                msg.what = DECREASE_HEADVIEW_PADDING;
                mHandler.sendMessage(msg);
            }
        }
    }

    private class DismissCircleThread implements Runnable {
        private final int COUNT = 10;
        private final int deltaMargin;

        public DismissCircleThread() {
            deltaMargin = mProgressViewMarginTop / COUNT;
        }

        @Override
        public void run() {
            int temp = 0;
            for (int i = 0; i <= COUNT; i++) {
                if (i == COUNT) {
                    temp = 0;
                } else {
                    temp = mProgressViewMarginTop - deltaMargin * i;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                }
                Message msg = mHandler.obtainMessage(DISMISS_CIRCLE);
                msg.arg1 = temp;
                mHandler.sendMessage(msg);
            }
        }
    }
}
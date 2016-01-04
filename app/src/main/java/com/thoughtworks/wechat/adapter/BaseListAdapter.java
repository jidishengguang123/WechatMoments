package com.thoughtworks.wechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.utils.ImageLoadUtils;

import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 17:50
 * Description:
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected final String TAG = BaseListAdapter.class.getSimpleName();
    protected final DisplayImageOptions options;
    protected Context mContext;
    protected List<T> mList;

    protected LayoutInflater mLayoutInflater;

    public BaseListAdapter(Context context, List<T> list){
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.options = ImageLoadUtils
                .getDisplayImageOptions(R.drawable.default_share_image);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

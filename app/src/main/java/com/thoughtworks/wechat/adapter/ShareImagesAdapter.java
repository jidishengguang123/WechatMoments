package com.thoughtworks.wechat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.http.entity.UrlEntity;
import com.thoughtworks.wechat.scaleview.ScaleImageView;

import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 17:48
 * Description:
 */
public class ShareImagesAdapter extends BaseListAdapter<UrlEntity> {
    private static final int COLUMN = 3;//GridView列数
    private final int spacing;//GridView列间距

    public ShareImagesAdapter(Context context, List<UrlEntity> list) {
        super(context, list);
        spacing = (int) mContext.getResources().getDimension(R.dimen.images_gridView_spacing);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = new ScaleImageView(mContext);
            ((ImageView)convertView).setScaleType(ImageView.ScaleType.FIT_XY);
            addOnPreDrawListener(convertView,parent);
            holder = new ViewHolder();
            holder.image = (ImageView)convertView;
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        ImageLoader.getInstance().displayImage(mList.get(position).getUrl(), holder.image, options);
        return convertView;
    }

    private void addOnPreDrawListener(final View convertView,final ViewGroup parent){
        //由于GridView未绘制时，width=0，所以需要监听GridView绘制。
        parent.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(parent.getWidth() / COLUMN - spacing, parent.getWidth() / COLUMN - spacing);
                convertView.setLayoutParams(params);
                return true;
            }
        });
    }

    static class ViewHolder{
        ImageView image;
    }
}

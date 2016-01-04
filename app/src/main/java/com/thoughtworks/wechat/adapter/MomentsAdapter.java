package com.thoughtworks.wechat.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.activity.ViewPagerActivity;
import com.thoughtworks.wechat.http.entity.Comment;
import com.thoughtworks.wechat.http.entity.Tweet;
import com.thoughtworks.wechat.http.entity.UrlEntity;
import com.thoughtworks.wechat.http.entity.UserInfo;
import com.thoughtworks.wechat.utils.DataTranslateUtil;

import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 15:56
 * Description:
 */
public class MomentsAdapter extends BaseListAdapter<Tweet> {
    private static final int LIMITE_IMAGE_COUNT = 9;

    public MomentsAdapter(Context context, List<Tweet> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.layout_moments_list_item,null);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        bindViewHolder(holder, position);
        return convertView;
    }

    private ViewHolder getViewHolder(View convertView){
        ViewHolder holder = new ViewHolder();
        holder.senderdIcon = (ImageView) convertView.findViewById(R.id.sender_icon);
        holder.senderName = (TextView) convertView.findViewById(R.id.sender_name);
        holder.content = (TextView) convertView.findViewById(R.id.content);
        holder.more = (TextView) convertView.findViewById(R.id.more);
        holder.images = (GridView) convertView.findViewById(R.id.images_gridView);
        holder.publishTime = (TextView) convertView.findViewById(R.id.publish_time);
        holder.fromDevice = (TextView) convertView.findViewById(R.id.from_device);
        holder.commentBtn = (ImageView) convertView.findViewById(R.id.comments_button);
        holder.commentLayout = (LinearLayout) convertView.findViewById(R.id.comments_layout);
        holder.commentList = (ListView) convertView.findViewById(R.id.comments_list);
        return holder;
    }

    private void bindViewHolder(ViewHolder holder, int position){
        Tweet tweet = mList.get(position);
        UserInfo userInfo = tweet.getSender();
        List<Comment> comments = tweet.getComments();
        final List<UrlEntity> images = tweet.getImages();
        //设置说说发送人名，图像
        if (userInfo != null){
            ImageLoader.getInstance().displayImage(userInfo.getAvatar(), holder.senderdIcon,options);
            holder.senderName.setText(userInfo.getUsername());
        }
        //设置说说内容
        if (tweet != null && !TextUtils.isEmpty(tweet.getContent())){
            holder.content.setText(tweet.getContent());
            holder.content.setVisibility(View.VISIBLE);
        }else {
            holder.content.setVisibility(View.GONE);
        }
        //test
        holder.publishTime.setText("2天前");
        holder.fromDevice.setText("LeX600");

        //显示分享图片
        if (images != null && images.size() > 0){
            //最多允许显示9张图片
            ShareImagesAdapter imagesAdapter = null;
            if (images.size() > LIMITE_IMAGE_COUNT){
                imagesAdapter = new ShareImagesAdapter(mContext,images.subList(0,LIMITE_IMAGE_COUNT));
            }else {
                imagesAdapter = new ShareImagesAdapter(mContext,images);
            }
            holder.images.setAdapter(imagesAdapter);
            holder.images.setVisibility(View.VISIBLE);
        }else {
            holder.images.setVisibility(View.GONE);
        }

        holder.images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, ViewPagerActivity.class);
                intent.putStringArrayListExtra("urls", DataTranslateUtil.translateImageUrls(images));
                intent.putExtra("selected", position);
                mContext.startActivity(intent);
            }
        });

        //显示评论
        if (comments != null && comments.size() > 0){
            CommentsAdapter commentsAdapter = new CommentsAdapter(mContext,comments);
            holder.commentList.setAdapter(commentsAdapter);
            holder.commentLayout.setVisibility(View.VISIBLE);
        }else {
            holder.commentLayout.setVisibility(View.GONE);
        }
    }

    static class ViewHolder{
        /**
         * 分享者图像
         */
        private ImageView senderdIcon;
        /**
         * 分享者名字
         */
        private TextView senderName;
        /**
         * 分享内容
         */
        private TextView content;
        /**
         * 显示全部分享内容
         */
        private TextView more;
        /**
         * 分享图片
         */
        private GridView images;
        /**
         * 分享时间
         */
        private TextView publishTime;
        /**
         *来自设备名称
         */
        private TextView fromDevice;
        /**
         *提交评论按钮
         */
        private ImageView commentBtn;
        /**
         * 评论容器布局
         */
        private LinearLayout commentLayout;
        /**
         *评论列表
         */
        private ListView commentList;
    }
}

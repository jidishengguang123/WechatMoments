package com.thoughtworks.wechat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.http.entity.Comment;
import com.thoughtworks.wechat.http.entity.UserInfo;

import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-19
 * Time: 18:24
 * Description:
 */
public class CommentsAdapter extends BaseListAdapter<Comment>{

    private String mCommentContent;

    public CommentsAdapter(Context context, List<Comment> list) {
        super(context, list);
        mCommentContent = mContext.getString(R.string.coment_content);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.layout_comments_item,null);
            holder = new ViewHolder();
            holder.commentSender = (TextView) convertView.findViewById(R.id.comment_sender);
            holder.commentContent = (TextView) convertView.findViewById(R.id.comment_content);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        bindViewHolder(holder,position);
        return convertView;
    }

    private void bindViewHolder(ViewHolder holder,int position){
        Comment comment = mList.get(position);
        if (comment == null){
            return;
        }
        UserInfo info = comment.getSender();
        if (info != null){
            holder.commentSender.setText(info.getUsername());
        }
        holder.commentContent.setText(String.format(mCommentContent, comment.getContent()));
    }

    class ViewHolder{
        TextView commentSender;
        TextView commentContent;
    }
}

package com.thoughtworks.wechat.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.utils.ImageLoadUtils;

import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-21
 * Time: 16:12
 * Description:
 */
public class SamplePagerAdapter extends PagerAdapter{

    private List<String> mUrls;

    private DisplayImageOptions mImageOptions;

    public SamplePagerAdapter(List<String> urls){
        mUrls = urls;
        mImageOptions = ImageLoadUtils
                .getDisplayImageOptions(R.drawable.default_big_image);
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        ImageView photoView = new ImageView(container.getContext());
        ImageLoader.getInstance().displayImage(mUrls.get(position),photoView,mImageOptions);
        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return photoView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}

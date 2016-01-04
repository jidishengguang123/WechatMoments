package com.thoughtworks.wechat.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ImageLoadUtils {
    /**
     * 获取图片显示配置参数
     */
    public static DisplayImageOptions getDisplayImageOptions(int defaultImageRes) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImageRes) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(defaultImageRes)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(defaultImageRes) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型//
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .build();// 构建完成
        return options;
    }

    /**
     * 获取图片显示配置参数
     */
    public static DisplayImageOptions getDisplayImageOptions(
            Drawable defaultImageDrawable) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImageDrawable) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(defaultImageDrawable)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(defaultImageDrawable) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型//
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .build();// 构建完成
        return options;
    }
}

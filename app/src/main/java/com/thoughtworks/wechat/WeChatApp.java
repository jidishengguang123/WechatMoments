package com.thoughtworks.wechat;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.thoughtworks.wechat.dataprovider.TweetsDataprovider;
import com.thoughtworks.wechat.dataprovider.UserInfoDataprovider;
import com.thoughtworks.wechat.utils.ActivityManager;
import com.thoughtworks.wechat.utils.DataController;
import com.thoughtworks.wechat.utils.NetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:liyang
 * Email:jidishengguang123@163.com
 * Date: 2015-12-18
 * Time: 16:23
 * Description:
 */
public class WeChatApp extends Application{
    /**
     * 硬盘图片缓存100M
     */
    private static final int MAX_DISK_CACHE_SIZE = 100 * 1024 * 1024;
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime()
            .availableProcessors() + 1;

    public static Context sContext;
    public static Handler sUiHandler = new Handler(Looper.getMainLooper());

    public List<Activity> mActivityList = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        configImageLoader();
        DataController.getsInstance().initData(this);
        UserInfoDataprovider.getInstance().initData();
        TweetsDataprovider.getInstance().initData();
        checkNetWork();
    }

    /**
     * 配置ImageLoader
     */
    private void configImageLoader() {
        // 缓存到应用目录的缓存目录，没有缓存文件数量的限制，使用URL的MD5值作为文件名
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(MAX_DISK_CACHE_SIZE)
                .threadPoolSize(THREAD_POOL_SIZE);
        if (AppConfig.DEBUG) {
            builder.writeDebugLogs();
        }
        ImageLoaderConfiguration configuration = builder.build();
        ImageLoader.getInstance().init(configuration);
    }

    public void checkNetWork(){
        if (!NetUtil.isNetworkAvailable()){
            Toast.makeText(this,getString(R.string.no_available_net),Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 结束应用
     */
    public void finishApp(){
        ImageLoader.getInstance().destroy();
        ActivityManager.getInstance().finishAllActivities();
        android.os.Process.killProcess(Process.myPid());
    }

}

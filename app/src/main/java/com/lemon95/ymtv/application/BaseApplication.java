package com.lemon95.ymtv.application;

/**
 * Created by WXT on 2016/3/2.
 */

import android.app.Activity;
import android.app.Application;

import com.lemon95.ymtv.view.activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


/**
 * 功能描述：用于存放全局变量和公用的资源等
 *
 * @author wxt
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;
    /**
     * Activity集合
     */
    private static ArrayList<BaseActivity> activitys = new ArrayList<BaseActivity>();

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }

    /**
     * 添加Activity到ArrayList<Activity>管理集合
     *
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        String className = activity.getClass().getName();
        for (Activity at : activitys) {
            if (className.equals(at.getClass().getName())) {
                activitys.remove(at);
                break;
            }
        }
        activitys.add(activity);
    }

    /**
     * 退出应用程序的时候，手动调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        for (BaseActivity activity : activitys) {
            activity.defaultFinish();
        }
    }

    public static BaseApplication getInstance() {
        if (null == instance) {
            instance = new BaseApplication();
        }
        return instance;
    }
}

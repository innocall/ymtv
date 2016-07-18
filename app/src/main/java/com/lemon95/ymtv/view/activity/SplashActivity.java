package com.lemon95.ymtv.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.presenter.SplashPresenter;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.view.impl.ISplashActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 启动页,
 * 1、检测版本更新
 * 2、初始化数据库数据
 * 3、生成关联二维码
 */
public class SplashActivity extends BaseActivity implements ISplashActivity{

    private SplashPresenter splashPresenter = new SplashPresenter(this);
    private ImageView lemon_splash_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }


    @Override
    protected void setupViews() {
        // 方法1 Android获得屏幕的宽和高
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        LogUtils.e(TAG,"分辨率：" + width + "*" + height + ";屏幕密度:" + densityDpi);
        showToastLong("分辨率：" + width + "*" + height + ";屏幕密度:" + densityDpi);
        lemon_splash_id = (ImageView)findViewById(R.id.lemon_splash_id);
    }

    @Override
    protected void initialized() {
        ImageLoader.getInstance().displayImage("assets://lemon_splash.jpg",lemon_splash_id);
        splashPresenter.start();
    }


    @Override
    public void toMainActivity() {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public String getVersion() {
        return AppSystemUtils.getVersionName(context);
    }
}

package com.lemon95.ymtv.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.utils.LogUtils;

/**
 * 启动页,
 * 1、检测版本更新
 * 2、初始化数据库数据
 * 3、生成关联二维码
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        LogUtils.i(TAG, "开始加载");
        return R.layout.activity_splash;
    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void initialized() {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

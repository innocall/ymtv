package com.lemon95.ymtv.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.presenter.SplashPresenter;
import com.lemon95.ymtv.service.MyPushIntentService;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.view.impl.ISplashActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.common.message.UmengMessageDeviceConfig;
import com.umeng.message.ALIAS_TYPE;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

/**
 * 启动页,
 * 1、检测版本更新
 * 2、初始化数据库数据
 * 3、生成关联二维码
 */
public class SplashActivity extends BaseActivity implements ISplashActivity{

    private SplashPresenter splashPresenter = new SplashPresenter(this);
    private ImageView lemon_splash_id;
    private PushAgent mPushAgent;
    public Handler handler = new Handler();
    public LinearLayout lemon_updata_pro;
    public ProgressBar mProgress; //下载进度条控件

    @Override
    protected int getLayoutId() {
        //开始推送服务
        mPushAgent = PushAgent.getInstance(getApplicationContext());
        mPushAgent.setPushCheck(true);    //默认不检查集成配置文件
        LogUtils.i(TAG, "别名：" + AppSystemUtils.getDeviceId());
        mPushAgent.setExclusiveAlias(AppSystemUtils.getDeviceId(), ALIAS_TYPE.SINA_WEIBO);
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        mPushAgent.enable(mRegisterCallback);
        return R.layout.activity_splash;
    }

    public IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {

        @Override
        public void onRegistered(String registrationId) {
            // TODO Auto-generated method stub
            handler.post(new Runnable() {

                @Override
                public void run() {
                    updateStatus(0);
                }
            });
        }

    };

    /**
     * 查看启动日志
     */
    private void updateStatus(int i) {
        String pkgName = getApplicationContext().getPackageName();
        String info = String.format("enabled:%s\nisRegistered:%s\nDeviceToken:%s\n" +
                        "SdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
                mPushAgent.isEnabled(), mPushAgent.isRegistered(),
                mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
                UmengMessageDeviceConfig.getAppVersionCode(this), UmengMessageDeviceConfig.getAppVersionName(this));
        LogUtils.i(TAG, "==============推送启动情况===============");
        LogUtils.i(TAG,pkgName);
        LogUtils.i(TAG,info);
        LogUtils.i(TAG, "updateStatus:" + String.format("enabled:%s  isRegistered:%s",
                mPushAgent.isEnabled(), mPushAgent.isRegistered()));
        LogUtils.i(TAG, "=============================");
        if(i == 0 && !mPushAgent.isEnabled()) {
            LogUtils.i(TAG,"再次开启推送服务");
            mPushAgent.enable(mRegisterCallback);
        }
    }

    @Override
    protected void setupViews() {
        // 方法1 Android获得屏幕的宽和高
        lemon_updata_pro = (LinearLayout) findViewById(R.id.lemon_updata_pro);
        mProgress = (ProgressBar) findViewById(R.id.update_progress);
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

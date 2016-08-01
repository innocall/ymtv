package com.lemon95.ymtv.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.service.MyPushIntentService;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.QRUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.common.message.UmengMessageDeviceConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

import java.io.File;

public class LoginActivity extends BaseActivity {

    private ImageView lemon_qr;
    private PushAgent mPushAgent;
    public Handler handler = new Handler();
    public static LoginActivity loginActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupViews() {
        loginActivity = this;
        lemon_qr = (ImageView) findViewById(R.id.lemon_qr);
        //初始化推送服务
        mPushAgent = PushAgent.getInstance(getApplicationContext());
        mPushAgent.enable(mRegisterCallback);
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
    protected void initialized() {
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(sdPath + AppConstant.DIRS + AppConstant.QRNAME);
        if (!file.isFile()) {
            File file2 = new File(sdPath + AppConstant.DIRS);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            LogUtils.e(TAG, "生成二维码");
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            boolean is = QRUtils.createQRImage(AppSystemUtils.getDeviceId(), 220, 220, bitmap, sdPath + AppConstant.DIRS + AppConstant.QRNAME);
            if (is) {
                ImageLoader.getInstance().displayImage("file:///" + sdPath + AppConstant.DIRS + AppConstant.QRNAME,lemon_qr);
            }
        } else {
            ImageLoader.getInstance().displayImage("file:///" + sdPath + AppConstant.DIRS + AppConstant.QRNAME,lemon_qr);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //离开后关闭推送服务
        mPushAgent.disable(iUmengUnregisterCallback);
    }

    public IUmengUnregisterCallback iUmengUnregisterCallback = new IUmengUnregisterCallback() {

        @Override
        public void onUnregistered(String s) {
            updateStatus(1);
        }
    };

}

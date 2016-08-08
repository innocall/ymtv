package com.lemon95.ymtv.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.bean.DeviceLogin;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.utils.QRUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

public class LoginActivity extends BaseActivity {

    private ImageView lemon_qr;
    public Handler handler = new Handler();
    private MsgReceiver msgReceiver;

    @Override
    protected int getLayoutId() {
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.lemon.login.RECEIVER");
        registerReceiver(msgReceiver, intentFilter);
        return R.layout.activity_login;
    }

    @Override
    protected void setupViews() {
        lemon_qr = (ImageView) findViewById(R.id.lemon_qr);
        //初始化推送服务
//        mPushAgent = PushAgent.getInstance(getApplicationContext());
//        mPushAgent.enable(mRegisterCallback);
//        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
    }

    /*public IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {

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

    };*/

    /**
     * 查看启动日志
     */
   /* private void updateStatus(int i) {
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
    }*/



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
        unregisterReceiver(msgReceiver);
        //离开后关闭推送服务
       // mPushAgent.disable(iUmengUnregisterCallback);
    }

   /* public IUmengUnregisterCallback iUmengUnregisterCallback = new IUmengUnregisterCallback() {

        @Override
        public void onUnregistered(String s) {
            updateStatus(1);
        }
    };*/

    /**
     * 自定义广播接收器，用于接收服务发出的信息
     */
    class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra("state");
            Gson gson = new Gson();
            DeviceLogin.Data user = gson.fromJson(state, DeviceLogin.Data.class);
            if (user != null) {
                PreferenceUtils.putString(context, AppConstant.USERID, user.getId());
                PreferenceUtils.putString(context, AppConstant.USERNAME,user.getNickName());
                PreferenceUtils.putString(context, AppConstant.USERIMG,user.getHeadImgUrl());
                PreferenceUtils.putString(context, AppConstant.USERMOBILE,user.getMobile());
                String pageType = PreferenceUtils.getString(context,AppConstant.PAGETYPE);
                if ("1".equals(pageType)) {
                    //去私人定制页面
                    Intent intent1 = new Intent();
                    intent1.setClass(context, NeedMovieActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                } else if("2".equals(pageType)) {
                    //去用户信息页面
                    Intent intent1 = new Intent();
                    intent1.setClass(context, UserActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
            }
        }
    }

}

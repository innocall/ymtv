package com.lemon95.ymtv.view.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lemon95.ymtv.R;
import com.lemon95.ymtv.bean.DeviceLogin;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.presenter.LoginPresenter;
import com.lemon95.ymtv.service.LoginService;
import com.lemon95.ymtv.service.PayService;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.utils.QRUtils;
import com.lemon95.ymtv.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

public class LoginActivity extends BaseActivity {

    private ImageView lemon_qr;
    public Handler handler = new Handler();
    //private MsgReceiver msgReceiver;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected int getLayoutId() {
       /* msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.lemon.login.RECEIVER");
        registerReceiver(msgReceiver, intentFilter);*/
        return R.layout.activity_login;
    }

    @Override
    protected void setupViews() {
        lemon_qr = (ImageView) findViewById(R.id.lemon_qr);
    }

    @Override
    protected void initialized() {
        String token = PreferenceUtils.getString(this,AppConstant.MACTOKEN,"");
        if (StringUtils.isBlank(token)) {
            loginPresenter.createToken(lemon_qr);
        } else {
            loginPresenter.createOr(token, lemon_qr);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }


    private boolean binded;

    // 创建一个 ServiceConnection 对象
    final ServiceConnection loginService = new ServiceConnection () {

        //服务关闭和杀死调用
        public void onServiceDisconnected(ComponentName name) {

        }
        //服务成功时被调用
        public void onServiceConnected(ComponentName name, IBinder service) {
            binded = true;
            String token = PreferenceUtils.getString(LoginActivity.this,AppConstant.MACTOKEN,"");
            loginPresenter.isOrder = false;
            loginPresenter.loginUser(token);
        }
    };

    /**
     * 解除服务绑定
     */
    private void unbindService() {
        if (binded) {
            unbindService(loginService);
            binded = false;
            loginPresenter.isOrder = true;
        }
    }

    /**
     * 绑定服务
     */
    public void bind() {
        Intent intent = new Intent(this, LoginService.class);
        bindService(intent, loginService, Context.BIND_AUTO_CREATE);
    }

    private Intent intent3 = new Intent("com.lemon.Main.RECEIVER");

    public void toPage() {
        unbindService();
        String pageType = PreferenceUtils.getString(context,AppConstant.PAGETYPE);
        Intent intent1 = new Intent();
        if ("1".equals(pageType)) {
            //去私人定制页面
            intent1.setClass(LoginActivity.this, NeedMovieActivity.class);
        } else if("2".equals(pageType)) {
            //去用户信息页面
            intent1.setClass(LoginActivity.this, UserActivity.class);
        }
        sendBroadcast(intent3);
        startActivity(intent1);
        finish();
    }
}

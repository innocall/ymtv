package com.lemon95.ymtv.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.bean.DeviceLogin;
import com.lemon95.ymtv.bean.Result;
import com.lemon95.ymtv.bean.impl.ISplashBean;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.dao.SplashDao;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.utils.QRUtils;
import com.lemon95.ymtv.utils.StringUtils;
import com.lemon95.ymtv.view.activity.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

/**
 * Created by WXT on 2016/8/9.
 */
public class LoginPresenter {

    private LoginActivity loginActivity;
    private ISplashBean iSplashBean;
    public boolean isOrder = true;

    public LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        iSplashBean = new SplashDao();
    }


    public void createToken(final ImageView lemon_qr) {
        iSplashBean.GenerateToken(AppSystemUtils.getDeviceId(), new SplashDao.OnResultListener() {
            @Override
            public void onSuccess(Result deviceLogin) {
                String token = deviceLogin.getData();
                if (deviceLogin != null && StringUtils.isNotBlank(token)) {
                    PreferenceUtils.putString(loginActivity, AppConstant.MACTOKEN, token);
                    createOr(token, lemon_qr);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public void createOr(String token,ImageView lemon_qr) {
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(sdPath + AppConstant.DIRS + AppConstant.QRNAME);
       // if (!file.isFile()) {
            File file2 = new File(sdPath + AppConstant.DIRS);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            Bitmap bitmap = BitmapFactory.decodeResource(loginActivity.getResources(), R.drawable.ic_launcher);
            boolean is = QRUtils.createQRImage(token, 220, 220, bitmap, sdPath + AppConstant.DIRS + AppConstant.QRNAME);
            if (is) {
                ImageLoader.getInstance().displayImage("file:///" + sdPath + AppConstant.DIRS + AppConstant.QRNAME,lemon_qr);
            }
//        } else {
//            ImageLoader.getInstance().displayImage("file:///" + sdPath + AppConstant.DIRS + AppConstant.QRNAME,lemon_qr);
//        }
        //开始服务
        loginActivity.bind();
    }

    public void loginUser(final String token) {
        if (StringUtils.isBlank(token) || isOrder) {
            return;
        }
        iSplashBean.deviceLogin(token, new SplashDao.OnDeviceLoginListener() {
            @Override
            public void onSuccess(DeviceLogin deviceLogin) {
                if (deviceLogin != null && deviceLogin.getData() != null) {
                    com.lemon95.ymtv.bean.DeviceLogin.Data user = deviceLogin.getData();
                    PreferenceUtils.putString(loginActivity, AppConstant.USERID,user.getId());
                    PreferenceUtils.putString(loginActivity, AppConstant.USERNAME,user.getNickName());
                    PreferenceUtils.putString(loginActivity, AppConstant.USERIMG,user.getHeadImgUrl());
                    PreferenceUtils.putString(loginActivity, AppConstant.USERMOBILE,user.getMobile());
                    loginActivity.toPage();
                } else {
                    loginUser(token);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                loginUser(token);
            }
        });
    }
}

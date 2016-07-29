package com.lemon95.ymtv.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.QRUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

public class LoginActivity extends BaseActivity {

    private ImageView lemon_qr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupViews() {
        lemon_qr = (ImageView) findViewById(R.id.lemon_qr);
    }

    @Override
    protected void initialized() {
        File file = new File("/sdcard/myImage/ymtv/qr/qr.png");
        if (!file.isFile()) {
            File file2 = new File("/sdcard/myImage/ymtv/qr");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            LogUtils.e(TAG, "生成二维码");
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            boolean is = QRUtils.createQRImage(AppSystemUtils.getDeviceId(), 220, 220, bitmap, "/sdcard/myImage/ymtv/qr/qr.png");
            if (is) {
                ImageLoader.getInstance().displayImage("file:///sdcard/myImage/ymtv/qr/qr.png",lemon_qr);
            }
        } else {
            ImageLoader.getInstance().displayImage("file:///sdcard/myImage/ymtv/qr/qr.png",lemon_qr);
        }
    }
}

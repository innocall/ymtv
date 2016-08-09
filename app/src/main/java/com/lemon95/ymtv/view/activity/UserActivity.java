package com.lemon95.ymtv.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.myview.CircleBitmapDisplayer;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.utils.StringUtils;
import com.lemon95.ymtv.utils.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by WXT on 2016/8/1.
 */
public class UserActivity extends BaseActivity {

    private Intent intent3 = new Intent("com.lemon.Main.RECEIVER");
    DisplayImageOptions options;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void setupViews() {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .build();
        ImageView lemon_user_img_id = (ImageView)findViewById(R.id.lemon_user_img_id);
        String img = PreferenceUtils.getString(context, AppConstant.USERIMG);
        if (StringUtils.isNotBlank(img)) {
            if (img.startsWith("http:")) {
                ImageLoader.getInstance().displayImage(img,lemon_user_img_id,options);
            } else {
                ImageLoader.getInstance().displayImage(AppConstant.RESOURCE + img,lemon_user_img_id,options);
            }
        }
        ((TextView)findViewById(R.id.lemon_user_nick)).setText("昵称：" + PreferenceUtils.getString(context,AppConstant.USERNAME));
        ((TextView)findViewById(R.id.lemon_user_mobile)).setText("手机号：" + PreferenceUtils.getString(context,AppConstant.USERMOBILE));
        findViewById(R.id.lemon_but_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.clearAll(context);
                sendBroadcast(intent3);
                PreferenceUtils.putString(context, AppConstant.PAGETYPE, "2");
                startActivity(LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void initialized() {

    }
}

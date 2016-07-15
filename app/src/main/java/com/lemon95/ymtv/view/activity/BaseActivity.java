package com.lemon95.ymtv.view.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.application.BaseApplication;
import com.lemon95.ymtv.utils.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by WXT on 2016/7/8.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * LOG打印标签
     */
    public static final String TAG = BaseActivity.class.getSimpleName();
    private MyConnectionChanngeReceiver myReceiver;
    private boolean isNetWork = true;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        context = this.getApplicationContext();
        LogUtils.i(TAG, "开始加载");
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
            // 删除窗口背景
            getWindow().setBackgroundDrawable(null);
        }

        BaseApplication.getInstance().addActivity(this);

        //向用户展示信息前的准备工作在这个方法里处理
        preliminary();
        registerReceiver();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    /**
     * 向用户展示信息前的准备工作在这个方法里处理
     */
    protected void preliminary() {
        // 初始化组件
        setupViews();

        // 初始化数据
        initialized();
    }

    /**
     * 获取全局的Context
     *
     * @return {@link #context = this.getApplicationContext();}
     */
    public Context getContext() {
        return context;
    }

    /**
     * 布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化组件
     */
    protected abstract void setupViews();

    /**
     * 初始化数据
     */
    protected abstract void initialized();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * 通过Action跳转界面
     **/
    public void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    public void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void showToastLong(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void showToastShort(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 监听广播
     */
    class MyConnectionChanngeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                LogUtils.i(TAG, "网络不可以用");
                showToastShort("网络断开连接");
                isNetWork = false;
                netWorkNO();
            } else {
                LogUtils.i(TAG, "网络连接成功");
                netWorkYew();
            }
        }
    }

    /**
     * 带有右进右出动画的退出
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 默认退出
     */
    public void defaultFinish() {
        super.finish();
    }

    public void netWorkNO() {
        /*final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.lemon95_dialog_title);
        builder.setMessage(R.string.lemon95_dialog_net_msg);
        builder.setNegativeButton(R.string.lemon95_dialog_cancal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(R.string.lemon95_dialog_net_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent("android.settings.WIFI_SETTINGS");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                BaseActivity.this.startActivity(intent);
            }
        });
        builder.show();*/
    }

    public void netWorkYew() {
        if (!isNetWork) {
            showToastShort("网络连接成功");
            isNetWork = true;
        }
    }

    //注册广播
    private void registerReceiver() {
        if (myReceiver == null) {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            myReceiver = new MyConnectionChanngeReceiver();
            this.registerReceiver(myReceiver, filter);
        }
    }

    private void unregisterReceiver() {
        if (myReceiver != null) {
            this.unregisterReceiver(myReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //低内存运行
        LogUtils.e(TAG, "clear cache");
        ImageLoader.getInstance().clearMemoryCache();  // 清除内存缓存
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtils.e(TAG, "内存级别：" + level);
        switch(level){
            //UI组件不可见时
            case TRIM_MEMORY_UI_HIDDEN:
                LogUtils.e(TAG,"clear cache");
                //   x.image().clearCacheFiles();    //清空缓存文件
                ImageLoader.getInstance().clearMemoryCache();  // 清除内存缓存
                break;
        }

    }


}

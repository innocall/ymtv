package com.lemon95.ymtv.view.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.utils.LogUtils;

import cn.com.video.venvy.param.JjVideoView;
import cn.com.video.venvy.param.OnJjBufferCompleteListener;
import cn.com.video.venvy.param.OnJjBufferStartListener;
import cn.com.video.venvy.param.OnJjBufferingUpdateListener;
import cn.com.video.venvy.param.OnJjOpenStartListener;
import cn.com.video.venvy.param.OnJjOpenSuccessListener;
import cn.com.video.venvy.param.OnJjOutsideLinkClickListener;
import cn.com.video.venvy.param.VideoJjMediaContoller;

public class PlayActivity extends BaseActivity {

    private String url;
    private JjVideoView mVideoView;//
    private View mLoadBufferView;// //
    private TextView mLoadBufferTextView;// //
    private View mLoadView;// /
    private TextView mLoadText;//

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play;
    }

    @Override
    protected void setupViews() {
        url = getIntent().getStringExtra("playUrl");
        mVideoView = (JjVideoView) findViewById(R.id.video);
        mLoadView = findViewById(R.id.sdk_ijk_progress_bar_layout);
        mLoadText = (TextView) findViewById(R.id.sdk_ijk_progress_bar_text);
        mLoadBufferView = findViewById(R.id.sdk_load_layout);
        mLoadBufferTextView = (TextView) findViewById(R.id.sdk_sdk_ijk_load_buffer_text);
        mVideoView.setMediaController(new VideoJjMediaContoller(this, true));
        mLoadBufferTextView.setTextColor(Color.RED);
        /***
         * 用户自定义的外链 可 获取外链点击时间
         */
        mVideoView.setOnJjOutsideLinkClickListener(new OnJjOutsideLinkClickListener() {

            @Override
            public void onJjOutsideLinkClick(String arg0) {
            }

            @Override
            public void onJjOutsideLinkClose() {
            }
        });
        /***
         * 设置缓冲
         */
        mVideoView.setMediaBufferingView(mLoadBufferView);
        /***
         * 视频开始加载数据
         */
        mVideoView.setOnJjOpenStart(new OnJjOpenStartListener() {

            @Override
            public void onJjOpenStart(String arg0) {
                mLoadText.setText(arg0);
            }
        });
        /***
         * 视频开始播放
         */
        mVideoView.setOnJjOpenSuccess(new OnJjOpenSuccessListener() {

            @Override
            public void onJjOpenSuccess() {
                mLoadView.setVisibility(View.GONE);
            }
        });
        // 缓冲开始
        mVideoView.setOnJjBufferStart(new OnJjBufferStartListener() {

            @Override
            public void onJjBufferStartListener(int arg0) {
                LogUtils.e("Video++", "====================缓冲值=====" + arg0);
            }
        });
        mVideoView.setOnJjBufferingUpdateListener(new OnJjBufferingUpdateListener() {

            @Override
            public void onJjBufferingUpdate(int arg1) {
                // TODO Auto-generated method stub
                if (mLoadBufferView.getVisibility() == View.VISIBLE) {
                    mLoadBufferTextView.setText(String
                            .valueOf(mVideoView.getBufferPercentage())
                            + "%");
                    LogUtils.e("Video++", "====================缓冲值2====="
                            + arg1);
                }
            }
        });
        // 缓冲完成
        mVideoView.setOnJjBufferComplete(new OnJjBufferCompleteListener() {

            @Override
            public void onJjBufferCompleteListener(int arg0) {
            }
        });
        /***
         * 注意VideoView 要调用下面方法 配置你用户信息
         */
        mVideoView.setVideoJjAppKey("N1fd2rFDZ");
        mVideoView.setVideoJjPageName("com.lemon95.ymtv");
        // mVideoView.setMediaCodecEnabled(true);// 是否开启 硬解 硬解对一些手机有限制
        // 判断是否源 0 代表 8大视频网站url 3代表自己服务器的视频源 2代表直播地址 1代表本地视频(手机上的视频源),4特殊需求
        mVideoView.setVideoJjType(3);
        /***
         * 视频标签显示的时间 默认显示5000毫秒 可设置 传入值 long类型 毫秒
         */
        // 参数代表是否记录视频播放位置 默认false不记录 true代表第二次或多次进入，直接跳转到上次退出的时间点开始播放
        // mVideoView.setVideoJjSaveExitTime(false);
        /***
         * 指定时间开始播放 毫秒
         */
        mVideoView.setResourceVideo(url);
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // 必须调用 要不直播有问题
        if (mVideoView != null)
            mVideoView.onDestroy();
    }
}

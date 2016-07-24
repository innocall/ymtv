package com.lemon95.ymtv.view.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.presenter.PlayMoviePresenter;
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

    private String videoId;
    private String videoType;
    private JjVideoView mVideoView;//
    private View mLoadBufferView;// //
    private TextView mLoadBufferTextView;// //
    private View mLoadView;// /
    private TextView mLoadText;//
    private PlayMoviePresenter playMoviePresenter = new PlayMoviePresenter(this);
    private ProgressBar sdk_ijk_progress_bar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play;
    }

    @Override
    protected void setupViews() {
        videoId = getIntent().getStringExtra("videoId");
        videoType = getIntent().getStringExtra("videoType");
        mVideoView = (JjVideoView) findViewById(R.id.video);
        mLoadView = findViewById(R.id.sdk_ijk_progress_bar_layout);
        mLoadText = (TextView) findViewById(R.id.sdk_ijk_progress_bar_text);
        sdk_ijk_progress_bar = (ProgressBar)findViewById(R.id.sdk_ijk_progress_bar);
        mLoadBufferView = findViewById(R.id.sdk_load_layout);
        mLoadBufferTextView = (TextView) findViewById(R.id.sdk_sdk_ijk_load_buffer_text);
        mVideoView.setMediaController(new VideoJjMediaContoller(this, true));
        mLoadBufferTextView.setTextColor(Color.RED);
        mVideoView.setMediaBufferingView(mLoadBufferView);//设置缓冲
        //视频开始加载数据
        mVideoView.setOnJjOpenStart(new OnJjOpenStartListener() {

            @Override
            public void onJjOpenStart(String arg0) {
                mLoadText.setText(arg0);
            }
        });
        //视频开始播放
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
                    mLoadBufferTextView.setText(String.valueOf(mVideoView.getBufferPercentage()) + "%");
                    LogUtils.e("Video++", "====================缓冲值2=====" + arg1);
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
       // mVideoView.setResourceVideo("http://movie.lemon95.com/201607231259/0d38483df67d4cf9a79a6d19294a580e/ty.rmvb");
    }

    @Override
    protected void initialized() {
        //获取播放地址
        if (AppConstant.MOVICE.equals(videoType)) {
           playMoviePresenter.getPlayUrl(videoId);
        } else if(AppConstant.SERIALS.equals(videoType)) {

        }
    }

    /**
     * 开始播放
     * @param url
     */
    public void startPlay(String url) {
        mVideoView.setVideoJjResetState();
        mVideoView.setVideoJjType(3);
        mVideoView.setResourceVideo(url);
    }

    /**
     * 播放出错
     */
    public void showError(String msg) {
        sdk_ijk_progress_bar.setVisibility(View.GONE);
        mLoadText.setText(msg);
        mLoadText.setVisibility(View.VISIBLE);
        mLoadView.setVisibility(View.VISIBLE);
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

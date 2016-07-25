package com.lemon95.ymtv.view.activity;

import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.presenter.PlayMoviePresenter;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.ToastUtils;

import com.lemon95.ymtv.myview.VideoMediaContoller;
import cn.com.video.venvy.param.JjVideoView;
import cn.com.video.venvy.param.OnJjBufferCompleteListener;
import cn.com.video.venvy.param.OnJjBufferStartListener;
import cn.com.video.venvy.param.OnJjBufferingUpdateListener;
import cn.com.video.venvy.param.OnJjCompletionListener;
import cn.com.video.venvy.param.OnJjOnOpenFailedListener;
import cn.com.video.venvy.param.OnJjOpenStartListener;
import cn.com.video.venvy.param.OnJjOpenSuccessListener;
import cn.com.video.venvy.widget.UsetMediaContoller;

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
    private VideoMediaContoller videoJjMediaContoller;
    private long waitTime = 2000;
    private long touchTime = 0;
    private ImageView lemon_play_img;

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
        lemon_play_img = (ImageView)findViewById(R.id.lemon_play_img);
        mLoadBufferTextView = (TextView) findViewById(R.id.sdk_sdk_ijk_load_buffer_text);

        videoJjMediaContoller = new VideoMediaContoller(this,true); //初始化媒体控制器
        mVideoView.setMediaController(videoJjMediaContoller);

        //videoJjMediaContoller = new VideoMediaContoller(this,this, true);
      //  mVideoView.setMediaController(videoJjMediaContoller);

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
            }
        });
        mVideoView.setOnJjBufferingUpdateListener(new OnJjBufferingUpdateListener() {

            @Override
            public void onJjBufferingUpdate(int arg1) {
                if (mLoadBufferView.getVisibility() == View.VISIBLE) {
                    mLoadBufferTextView.setText("加载中：" + String.valueOf(mVideoView.getBufferPercentage()) + "%");
                }
            }
        });
        // 缓冲完成
        mVideoView.setOnJjBufferComplete(new OnJjBufferCompleteListener() {

            @Override
            public void onJjBufferCompleteListener(int arg0) {
            }
        });
        //视频播放完毕
        mVideoView.setOnJjCompletionListener(new OnJjCompletionListener() {
            @Override
            public void onJjCompletion() {
                showMsg("播放完毕");
                finish();
            }
        });
        mVideoView.setOnJjOpenFailedListener(new OnJjOnOpenFailedListener() {
            @Override
            public boolean onJjOpenFailed(int i, int i1) {
               // showError("播放出错");
                if (AppConstant.MOVICE.equals(videoType)) {
                    playMoviePresenter.getPlayUrl(videoId);
                } else if(AppConstant.SERIALS.equals(videoType)) {
                    playMoviePresenter.getPlaySerialUrl(videoId);
                }
                return false;
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
    }

    @Override
    protected void initialized() {
        //获取播放地址
        if (AppConstant.MOVICE.equals(videoType)) {
            playMoviePresenter.getPlayUrl(videoId);
        } else if(AppConstant.SERIALS.equals(videoType)) {
            playMoviePresenter.getPlaySerialUrl(videoId);
        }
    }

    /**
     * 开始播放
     * @param url
     */
    public void startPlay(String url) {
        String name = getIntent().getStringExtra("videoName");
        mVideoView.setVideoJjResetState();
        mVideoView.setVideoJjType(3);
        mVideoView.setVideoJjTitle(name);
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        try{
            if(keyCode==KeyEvent.KEYCODE_DPAD_UP){
                lemon_play_img.setVisibility(View.GONE);
            } else if (keyCode ==KeyEvent.KEYCODE_DPAD_LEFT) {
                mLoadBufferView.setVisibility(View.VISIBLE);
                lemon_play_img.setVisibility(View.GONE);
            } else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT) {
                mLoadBufferView.setVisibility(View.VISIBLE);
                lemon_play_img.setVisibility(View.GONE);
            } else if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN) {
                lemon_play_img.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try{
            if(keyCode==KeyEvent.KEYCODE_DPAD_UP){
                videoJjMediaContoller.show();

            } else if (keyCode ==KeyEvent.KEYCODE_DPAD_LEFT) {
                videoJjMediaContoller.show();
                mLoadBufferView.setVisibility(View.GONE);
                videoJjMediaContoller.toLeft(mVideoView,lemon_play_img);
            } else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT) {
                mLoadBufferView.setVisibility(View.GONE);
                videoJjMediaContoller.show();
                videoJjMediaContoller.toRight(mVideoView,lemon_play_img);
            } else if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN) {
                videoJjMediaContoller.show();
            } else if (keyCode==KeyEvent.KEYCODE_DPAD_CENTER||keyCode==KeyEvent.KEYCODE_ENTER) {
                videoJjMediaContoller.enter(mVideoView,lemon_play_img);
            } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                long currentTime = System.currentTimeMillis();
                if ((currentTime - touchTime) >= waitTime) {
                    showMsg("再按一次退出播放");
                    touchTime = currentTime;
                } else {
                    this.finish();
                }
                return false;
            }
        } catch (Exception e) {
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showMsg(String msg) {
        ToastUtils.showBgToast(msg,context);
    }
}

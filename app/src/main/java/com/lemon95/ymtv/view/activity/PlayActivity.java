package com.lemon95.ymtv.view.activity;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lemon95.ymtv.R;
import com.lemon95.ymtv.bean.VideoWatchHistory;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.myview.VerticalProgressBar;
import com.lemon95.ymtv.presenter.PlayMoviePresenter;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.utils.ToastUtils;

import com.lemon95.ymtv.myview.VideoMediaContoller;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    private static final long DEFAULT_TIME_OUT = 5000; //显示时间
    private static final long show_time = 5 * 60 * 1000;
    private String videoId;
    private String videoType;
    private String SerialEpisodeId;
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
    private ImageView lemon_play_img,lemon_pay_img;
    private VerticalProgressBar lemon_volume_seek;
    private AudioManager mAM;
    private int mMaxVolume;
    private int mVolume = 0;
    private ImageView lemon_volume_img;
    private LinearLayout lemon_volume,lemon_ll_pro;
    private Boolean isPersonal;
    private TextView lemon_pay_title;
    private final static int VOLUME_HIDE = 1;
    private long playTime = 0;
    private LinearLayout lemon_pay;
    private boolean isTop = true; //是否能够点击
    public boolean isPro = false;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case VOLUME_HIDE:
                    lemon_volume.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play;
    }

    @Override
    protected void setupViews() {
        isPersonal = getIntent().getBooleanExtra("isPersonal", false);
        videoId = getIntent().getStringExtra("videoId");
        videoType = getIntent().getStringExtra("videoType");
        SerialEpisodeId = getIntent().getStringExtra("SerialEpisodeId");
        mVideoView = (JjVideoView) findViewById(R.id.video);
        mLoadView = findViewById(R.id.sdk_ijk_progress_bar_layout);
        mLoadText = (TextView) findViewById(R.id.sdk_ijk_progress_bar_text);
        lemon_pay_title = (TextView) findViewById(R.id.lemon_pay_title);
        lemon_pay_title.setText(Html.fromHtml("试看结束，请打赏<font color=\"#ff4040\">2元</font>看全片"));
        sdk_ijk_progress_bar = (ProgressBar)findViewById(R.id.sdk_ijk_progress_bar);
        mLoadBufferView = findViewById(R.id.sdk_load_layout);
        lemon_play_img = (ImageView)findViewById(R.id.lemon_play_img);
        lemon_volume_img = (ImageView)findViewById(R.id.lemon_volume_img);
        lemon_pay_img = (ImageView)findViewById(R.id.lemon_pay_img);
        lemon_volume = (LinearLayout)findViewById(R.id.lemon_volume);
        lemon_ll_pro = (LinearLayout)findViewById(R.id.lemon_ll_pro);
        lemon_pay = (LinearLayout)findViewById(R.id.lemon_pay);
        mLoadBufferTextView = (TextView) findViewById(R.id.sdk_sdk_ijk_load_buffer_text);
        lemon_volume_seek = (VerticalProgressBar) findViewById(R.id.lemon_volume_seek);
        videoJjMediaContoller = new VideoMediaContoller(this,true); //初始化媒体控制器
        mVideoView.setMediaController(videoJjMediaContoller);
        mAM = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        mMaxVolume = mAM.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        lemon_volume_seek.setMax(mMaxVolume);
        mVolume = mAM.getStreamVolume(AudioManager.STREAM_MUSIC);
        lemon_volume_seek.setProgress(mVolume);
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
                if (isPro) {
                    showLL();
                    handler.sendEmptyMessageDelayed(PAY_LIS,1000);
                }
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
                    //playMoviePresenter.getPlayUrl(videoId);
                    String userId = PreferenceUtils.getString(context,AppConstant.USERID,"");
                    playMoviePresenter.initPageData(videoId, userId, isPersonal);
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
            //playMoviePresenter.getPlayUrl(videoId);
            String userId = PreferenceUtils.getString(context,AppConstant.USERID,"");
            playMoviePresenter.initPageData(videoId, userId, isPersonal);
        } else if(AppConstant.SERIALS.equals(videoType)) {
            playMoviePresenter.getPlaySerialUrl(SerialEpisodeId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mVideoView != null && playTime > 0) {
            mVideoView.seekTo(playTime);
            lemon_play_img.setVisibility(View.GONE);
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

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            playTime = mVideoView.getCurrentPosition();
            videoJjMediaContoller.pausePlay(mVideoView,lemon_play_img);
        }
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
        playMoviePresenter.setIsParam(false);
        //上传播放记录
        VideoWatchHistory videoWatchHistory = new VideoWatchHistory();
        String userId = PreferenceUtils.getString(context, AppConstant.USERID, ""); //获取用户ID;
        String mac = AppSystemUtils.getDeviceId();
        videoWatchHistory.setUserId(userId);
        videoWatchHistory.setMAC(mac);
        videoWatchHistory.setVideoId(videoId);
        videoWatchHistory.setVideoTypeId(videoType);
        videoWatchHistory.setIsPersonal(isPersonal);
        videoWatchHistory.setSerialEpisodeId(SerialEpisodeId);
        videoWatchHistory.setUserIP("");
        // 必须调用 要不直播有问题
        if (mVideoView != null)
            //上传播放记录
            videoWatchHistory.setWatchTime(mVideoView.getCurrentPosition() + "");
            playMoviePresenter.addVideoHistory(videoWatchHistory);
            mVideoView.onDestroy();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isTop) {
            try{
                if (mLoadView.getVisibility() == View.GONE) {
                    if(keyCode==KeyEvent.KEYCODE_DPAD_UP){
                        mHandler.removeMessages(VOLUME_HIDE);
                        mHandler.sendEmptyMessageDelayed(VOLUME_HIDE, DEFAULT_TIME_OUT);
                        // lemon_play_img.setVisibility(View.GONE);
                    } else if (keyCode ==KeyEvent.KEYCODE_DPAD_LEFT) {
                        mLoadBufferView.setVisibility(View.VISIBLE);
                        lemon_play_img.setVisibility(View.GONE);
                    } else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT) {
                        mLoadBufferView.setVisibility(View.VISIBLE);
                        lemon_play_img.setVisibility(View.GONE);
                    } else if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN) {
                        mHandler.removeMessages(VOLUME_HIDE);
                        mHandler.sendEmptyMessageDelayed(VOLUME_HIDE, DEFAULT_TIME_OUT);
                        //lemon_play_img.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isTop) {
            try{
                if (mLoadView.getVisibility() == View.GONE) {
                    if(keyCode==KeyEvent.KEYCODE_DPAD_UP){
                        //加声音
                        lemon_volume.setVisibility(View.VISIBLE);
                        mVolume = mVolume + 1;
                        if (mVolume >= mMaxVolume) {
                            mVolume = mMaxVolume;
                        }
                        lemon_volume_seek.setProgress(mVolume);
                        lemon_volume_img.setImageResource(R.drawable.icon_volume);
                        mAM.setStreamVolume(AudioManager.STREAM_MUSIC, mVolume, 0);
                    } else if (keyCode ==KeyEvent.KEYCODE_DPAD_LEFT) {
                        videoJjMediaContoller.show();
                        mLoadBufferView.setVisibility(View.GONE);
                        videoJjMediaContoller.toLeft(mVideoView, lemon_play_img);
                    } else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT) {
                        mLoadBufferView.setVisibility(View.GONE);
                        videoJjMediaContoller.show();
                        videoJjMediaContoller.toRight(mVideoView, lemon_play_img);
                    } else if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN) {
                        lemon_volume.setVisibility(View.VISIBLE);
                        mVolume = mVolume - 1;
                        if (mVolume <= 0) {
                            mVolume = 0;
                            lemon_volume_img.setImageResource(R.drawable.icon_novolume);
                        }
                        lemon_volume_seek.setProgress(mVolume);
                        mAM.setStreamVolume(AudioManager.STREAM_MUSIC, mVolume, 0);
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
                }
            } catch (Exception e) {
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showPay() {
        lemon_pay.setVisibility(View.VISIBLE);
        mVideoView.pause(); //暂停播放
        isTop = false;
        videoJjMediaContoller.hide();
    }

    public void initPay(String url) {
        ImageLoader.getInstance().displayImage(url,lemon_pay_img);
    }

    public void hidePay() {
        lemon_ll_pro.setVisibility(View.GONE);
        lemon_pay.setVisibility(View.GONE);
        mVideoView.start();
        isTop = true;
        isPro = false;
    }

    private final static int HIDE_LL = 0;
    private final static int PAY_LIS = 1;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HIDE_LL:
                    lemon_ll_pro.setVisibility(View.GONE);
                    isPro = false;
                    break;
                case PAY_LIS:
                    if (mVideoView != null) {
                        long pos = mVideoView.getCurrentPosition();
                        if (pos > show_time) {
                            showPay();
                        } else  {
                            handler.sendEmptyMessageDelayed(PAY_LIS,1000);
                        }
                    }
                    break;
            }
        }
    };

    public void showLL() {
        lemon_ll_pro.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(HIDE_LL,10000);
    }

    public void showMsg(String msg) {
        ToastUtils.showBgToast(msg,context);
    }
}

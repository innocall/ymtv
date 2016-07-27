package com.lemon95.ymtv.myview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lemon95.ymtv.view.activity.PlayActivity;

import cn.com.video.venvy.param.JjVideoView;
import cn.com.video.venvy.param.VideoJjMediaContoller;

/**
 * Created by WXT on 2016/7/25.
 */
public class VideoMediaContoller extends VideoJjMediaContoller {

    private static final long K_TIME = 15000;  //快进时间
    private ImageView lemon_play_img;
    private PlayActivity playActivity;
    private boolean isView = false;
    private long time = 0,time2 = 0;

    public VideoMediaContoller(PlayActivity playActivity, boolean b) {
        super(playActivity, b);
        this.playActivity = playActivity;
    }

    /**
     * 按下OK键
     */
    public void enter(JjVideoView mVideoView,ImageView lemon_play_img) {
        if (mVideoView.isPlaying()) {
            isView = false;
            pausePlay(mVideoView,lemon_play_img);
        } else {
            isView = true;
            startPlay(mVideoView,lemon_play_img);
        }
    }

    /**
     * 暂停播放
     * @param mVideoView
     */
    public void pausePlay(JjVideoView mVideoView,ImageView lemon_play_img) {
        try {
            lemon_play_img.setImageResource(cn.com.video.venvy.R.drawable.icon_pause);
            lemon_play_img.setVisibility(View.VISIBLE);
            mVideoView.pause();
            show();
        } catch (Exception e){}
    }

    /**
     * 开始播放
     * @param mVideoView
     */
    public void startPlay(JjVideoView mVideoView,ImageView lemon_play_img) {
        mVideoView.start();
        lemon_play_img.setImageResource(cn.com.video.venvy.R.drawable.icon_play);
        hide();
    }

    /**
     * 遥控右键
     * @param mVideoView
     * @param lemon_play_img
     */
    public void toRight(JjVideoView mVideoView, ImageView lemon_play_img) {
        isView = true;
        this.lemon_play_img = lemon_play_img;
        lemon_play_img.setImageResource(cn.com.video.venvy.R.drawable.icon_fast_forward);
        lemon_play_img.setVisibility(View.VISIBLE);
        show();
        long totleTime = mVideoView.getDuration();
        if (time == 0) {
            time = mVideoView.getCurrentPosition();
        } else {
            time = time + K_TIME;
        }
        if (totleTime > time) {
            mVideoView.seekTo(time);
        } else {
            playActivity.showMsg("即将播放完毕");
        }
    }

    @Override
    public void hide() {
        super.hide();
        if (isView) {
            lemon_play_img.setVisibility(View.GONE);
        }
    }

    public void toLeft(JjVideoView mVideoView, ImageView lemon_play_img) {
        isView = true;
        this.lemon_play_img = lemon_play_img;
        lemon_play_img.setImageResource(cn.com.video.venvy.R.drawable.icon_fast_rewind);
        lemon_play_img.setVisibility(View.VISIBLE);
        show();
        if (time2 == 0) {
            time2 = mVideoView.getCurrentPosition();
        } else {
            if (time2 - K_TIME < 0) {
                time2 = 0;
            } else {
                time2 = time2 - K_TIME;
            }
        }
        mVideoView.seekTo(time2);
    }
}

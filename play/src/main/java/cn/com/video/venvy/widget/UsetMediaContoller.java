package cn.com.video.venvy.widget;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import cn.com.video.venvy.R;
import cn.com.video.venvy.param.Gestures;
import cn.com.video.venvy.param.JjMediaContoller;
import cn.com.video.venvy.param.JjVideoView;
import cn.com.video.venvy.param.MediaContollerTouchListener;
import cn.com.video.venvy.param.MediaPlayerControl;

/**
 * Created by super on 2015/7/25. Video++视频控制器 自定义
 * 修改venvy_video_user_media_controller_sdk.xml重写
 */
public class UsetMediaContoller extends JjMediaContoller {
    /**
     * 控制器接口
     */
    private MediaPlayerControl mPlayer;
    /***
     * 锁视频控件
     */
    private ImageButton mLock;
    /**
     * 拖动进度控件
     */
    private SeekBar mProgress;
    /**
     * 视频总时间 当前时间
     */
    private TextView mEndTime, mCurrentTime;
    /**
     * 视频总时间
     */
    private long mDuration;
    /**
     * 是否显示
     */
    private boolean mShowing;
    /***
     * 控制器是否是锁的状态
     */
    private boolean mScreenLocked = false;
    /**
     * 是否是拖动状态
     */
    private boolean mDragging;
    /***
     *
     */
    private boolean mInstantSeeking = true;
    /***
     * 默认时间
     */
    private static final int DEFAULT_TIME_OUT = 3000;
    /***
     * 设置 标题一直显示
     */
    private static final int SHOW_TIME_CONTINUE = 0;
    /**
     * 控制器默认的显示时间
     */
    private static final int DEFAULT_LONG_TIME_SHOW = 1200000;
    /**
     * SeekBar拖动条默认值
     */
    private static final int DEFAULT_SEEKBAR_VALUE = 1000;
    private static final int TIME_TICK_INTERVAL = 1000;
    /**
     * 设置横竖屏View
     */
    private ImageButton mDirectionView;
    /***
     * 暂停按钮
     */
    private ImageButton mPauseButton;
    private View mMediaController;
    private View mControlsLayout;
    private View mSystemInfoLayout;
    private TextView mFileName;
    /***
     * 弹出内容区
     */
    private TextView mPopupInfoView;
    /**
     * 设置清晰度View
     */
    private TextView mQualityView;
    /**
     * 关闭播放器
     */
    private ImageButton mBackButton;
    /***
     * 声音 亮度布局
     */
    private View mOperationVolLum;
    /***
     * 快进 快退 布局
     */
    private View mPlanVolLum;
    /**
     * 快进 快退 显示Logo
     */
    private ImageView mPlanImage;
    /***
     * 快进 快退 当前时间
     */
    private long mPlanPos;
    /**
     * 快进 快退 显示时间
     */
    private TextView mPlanTimeView;
    /***
     * 手势控制的图片
     */
    private ImageView mVolLumBg;
    /***
     * 手势控制的进度条
     */
    private TextView mVolLumTextView;
    private AudioManager mAM;
    private int mMaxVolume;
    private float mBrightness = 0.01f;
    private int mVolume = 0;
    private Handler mHandler;

    private Animation mAnimSlideInTop;
    private Animation mAnimSlideInBottom;
    private Animation mAnimSlideOutTop;
    private Animation mAnimSlideOutBottom;
    /**
     * 手势管理类
     */
    private Gestures mGestures;

    private ImageView lemon_play_img;

    /***
     * 构造函数
     *
     * @param context 上下文
     */
    public UsetMediaContoller(Context context) {
        super(context);
        initResources();
    }

    /***
     * 构造函数
     * <p/>
     * 上下文
     */
    // public UsetMediaContoller(Context context, AttributeSet attrs) {
    // super(context, attrs);
    // lock(mScreenLocked);
    // }
    private void initResources() {
        mHandler = new MHandler(this);
        mAM = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAM.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mGestures = new Gestures(mContext);
        mGestures.setTouchListener(mTouchListener, true);
        mAnimSlideOutBottom = AnimationUtils.loadAnimation(mContext,
                R.anim.venvy_slide_out_bottom);
        mAnimSlideOutTop = AnimationUtils.loadAnimation(mContext,
                R.anim.venvy_slide_out_top);
        mAnimSlideInBottom = AnimationUtils.loadAnimation(mContext,
                R.anim.venvy_slide_in_bottom);
        mAnimSlideInTop = AnimationUtils.loadAnimation(mContext,
                R.anim.venvy_slide_in_top);
        mAnimSlideOutBottom
                .setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mMediaController.setVisibility(View.GONE);
                        showButtons(false);
                        mHandler.removeMessages(MSG_HIDE_SYSTEM_UI);
                        mHandler.sendEmptyMessageDelayed(MSG_HIDE_SYSTEM_UI,
                                DEFAULT_TIME_OUT);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
        findViewItems(mRootView);
    }

    @Override
    public void setAnchorView(ViewGroup view) {
        super.setAnchorView(view);
    }

    /***
     * find加载布局的控件
     *
     * @param v RootView
     */
    private void findViewItems(View v) {
        /***
         * 中间弹出区域控件
         */
        mPopupInfoView = (TextView) v.findViewById(R.id.sdk_ijk_show_popup_view);
        /**
         * 上下控制器区域
         */
        mMediaController = mRootView;
        /**
         * 头部控制器区域
         */
        mSystemInfoLayout = v.findViewById(R.id.sdk_media_controller_panel);
        /**
         * 视频总时间
         */
        mEndTime = (TextView) v
                .findViewById(R.id.sdk_media_controller_time_total);
        /**
         * 视频播放过去时间
         */
        mCurrentTime = (TextView) v
                .findViewById(R.id.sdk_media_controller_time_current);
        /**
         * 视频标题
         */
        mFileName = (TextView) v
                .findViewById(R.id.sdk_media_controller_video_name);
        /***
         * 底部控制器区域
         */
        mControlsLayout = v.findViewById(R.id.sdk_media_controller_control);
        /**
         * 返回按钮
         */
        mBackButton = (ImageButton) v
                .findViewById(R.id.sdk_media_controller_back);
        mBackButton.setOnClickListener(mBackListener);
        /**
         * 声音 亮度布局
         */
        mOperationVolLum = v
                .findViewById(R.id.sdk_media_controller_operation_volume_brightness);
        /**
         * 快进 快退 布局
         */
        mPlanVolLum = v.findViewById(R.id.sdk_media_controller_plan_layout);
        /**
         * 快进 快退 Logo
         */
        mPlanImage = (ImageView) v
                .findViewById(R.id.sdk_sdk_media_controller_plan);
        lemon_play_img = (ImageView) v
                .findViewById(R.id.lemon_play_img);
        /**
         * 快进 快退 时间
         */
        mPlanTimeView = (TextView) v
                .findViewById(R.id.sdk_sdk_media_controller_plan_time);
        /**
         * 手势 左右显示的ImageView
         */
        mVolLumBg = (ImageView) v
                .findViewById(R.id.sdk_media_controller_operation_bg);
        /***
         * 手势 左右显示的TextView
         */
        mVolLumTextView = (TextView) v
                .findViewById(R.id.sdk_media_controller_tv_volume_percentage);
        /***
         * 锁控件
         */
        mLock = (ImageButton) v
                .findViewById(R.id.sdk_media_controller_video_lock);
        mLock.setOnClickListener(mLockClickListener);
        /***
         * 暂停 开始 按钮
         */
        mPauseButton = (ImageButton) v
                .findViewById(R.id.sdk_media_controller_play_pause);
        mPauseButton.setOnClickListener(mPauseListener);
        /**
         * 拖动控件
         */
        mProgress = (SeekBar) v.findViewById(R.id.sdk_media_controller_seek);
        mProgress.setOnSeekBarChangeListener(mSeekListener);
        mProgress.setMax(DEFAULT_SEEKBAR_VALUE);
        /***
         * 设置横竖屏
         */
        mDirectionView = (ImageButton) v
                .findViewById(R.id.sdk_media_controller_direction);
        mDirectionView.setOnClickListener(mDirectionListener);
        /**
         * 设置清晰度View
         */
        mQualityView = (TextView) v
                .findViewById(R.id.sdk_media_controller_video_mass);
    }

    /**
     * 设置控制器锁定状态方法
     *
     * @param toLock
     */
    private void lock(boolean toLock) {
        if (toLock) {
            mLock.setImageResource(R.drawable.venvy_sdk_media_controller_lock_bg);
            mProgress.setEnabled(false);
            if (mScreenLocked != toLock)
                setOperationInfo(
                        mContext.getString(R.string.sdk_video_screen_locked_string),
                        1000);
        } else {
            mLock.setImageResource(R.drawable.venvy_sdk_media_controller_unlock_bg);
            mProgress.setEnabled(true);
            if (mScreenLocked != toLock)
                setOperationInfo(
                        mContext.getString(R.string.sdk_video_screen_unlocked_string),
                        1000);
        }
        mScreenLocked = toLock;
        mGestures.setTouchListener(mTouchListener, !mScreenLocked);
    }

    /***
     * 判断屏幕是否是锁状态
     *
     * @return
     */
    public boolean isLocked() {
        return mScreenLocked;
    }

    /***
     * 设置弹出内容区方法
     *
     * @param info
     * @param time
     */
    private void setOperationInfo(String info, long time) {
        mPopupInfoView.setText(info);
        mPopupInfoView.setVisibility(View.VISIBLE);
        mHandler.removeMessages(MSG_HIDE_OPERATION_INFO);
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_OPERATION_INFO, time);
    }

    private static final int MSG_FADE_OUT = 1;
    private static final int MSG_SHOW_PROGRESS = 2;
    private static final int MSG_HIDE_SYSTEM_UI = 3;
    private static final int MSG_TIME_TICK = 4;
    private static final int MSG_HIDE_OPERATION_INFO = 5;
    private static final int MSG_HIDE_OPERATION_VOLLUM = 6;

    /**
     * 自定义 Handler 消息类
     */
    private static class MHandler extends Handler {
        private WeakReference<UsetMediaContoller> mc;

        public MHandler(UsetMediaContoller mc) {
            this.mc = new WeakReference<UsetMediaContoller>(mc);
        }

        @Override
        public void handleMessage(Message msg) {
            UsetMediaContoller c = mc.get();
            if (c == null)
                return;

            switch (msg.what) {
                case MSG_FADE_OUT:
                    c.hide();
                    break;
                case MSG_SHOW_PROGRESS:
                    long pos = c.setProgress();
                    if (!c.mDragging && c.mShowing) {
                        msg = obtainMessage(MSG_SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                        c.updatePausePlay();
                    }
                    break;
                case MSG_HIDE_SYSTEM_UI:
                    if (!c.mShowing)
                        c.showSystemUi(false);
                    break;
                case MSG_TIME_TICK:
                    sendEmptyMessageDelayed(MSG_TIME_TICK, TIME_TICK_INTERVAL);
                    break;
                case MSG_HIDE_OPERATION_INFO:
                    c.mPopupInfoView.setVisibility(View.GONE);
                    break;
                case MSG_HIDE_OPERATION_VOLLUM:
                    c.mOperationVolLum.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /**
     * 设置SeekBar进度
     *
     * @return
     */
    private long setProgress() {
        if (mPlayer == null || mDragging)
            return 0;

        long position = mPlayer.getCurrentPosition();
        long duration = mPlayer.getDuration();
        if (duration > 0) {
            long pos = 1000L * position / duration;
            mProgress.setProgress((int) pos);
        }
        int percent = mPlayer.getBufferPercentage();
        mProgress.setSecondaryProgress(percent * 10);

        mDuration = duration;

        mEndTime.setText(generateTime(mDuration));
        mCurrentTime.setText(generateTime(position));

        return position;
    }

    /***
     * @param showButtons
     */
    private void showButtons(boolean showButtons) {
        Window window = mContext.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        float val = showButtons ? -1 : 0;
        try {
            Field buttonBrightness = layoutParams.getClass().getField(
                    "buttonBrightness");
            buttonBrightness.set(layoutParams, val);
        } catch (Exception e) {
        }
        window.setAttributes(layoutParams);
    }

    /***
     * 更新暂停 开始播放按钮暂停
     */
    private void updatePausePlay() {
        if (mPlayer.isPlaying())
            mPauseButton
                    .setImageResource(R.drawable.venvy_sdk_media_controller_pause_bg);
        else
            mPauseButton
                    .setImageResource(R.drawable.venvy_sdk_media_controller_play_bg);
    }

    /***
     * 设置亮度 手势操作
     *
     * @param scale
     */
    private void setBrightnessScale(float scale) {
        mVolLumBg.setImageResource(R.drawable.venvy_sdk_media_controller_bright_big);
        int a = (int) (scale * 100);
        mVolLumTextView.setText(String.valueOf(a));
        mOperationVolLum.setVisibility(View.VISIBLE);
    }

    /***
     * 设置声音 手势操作
     *
     * @param scale 进度条
     */
    private void setVolumeScale(float scale) {
        if (scale != 0) {
            mVolLumBg
                    .setImageResource(R.drawable.venvy_sdk_media_controller_volume);
        } else {
            mVolLumBg
                    .setImageResource(R.drawable.venvy_sdk_media_controller_silence);
        }
        mVolLumTextView.setText(String.valueOf((int) scale));
        mOperationVolLum.setVisibility(View.VISIBLE);
    }

    /***
     * 设置亮度
     *
     * @param f 进度
     */
    private void setBrightness(float f) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.screenBrightness = f;
        if (lp.screenBrightness > 1.0f)
            lp.screenBrightness = 1.0f;
        else if (lp.screenBrightness < 0.01f)
            lp.screenBrightness = 0.01f;
        mContext.getWindow().setAttributes(lp);
    }

    /***
     * 设置声音
     *
     * @param v 进度
     */
    private void setVolume(int v) {
        if (v > mMaxVolume) {
            v = mMaxVolume;
        } else if (v < 0) {
            v = 0;
        }
        mAM.setStreamVolume(AudioManager.STREAM_MUSIC, v, 0);
        setVolumeScale((v * 100) / mMaxVolume);
    }

    /***
     * SeekBar监听器
     */
    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStartTrackingTouch(SeekBar bar) {
            mDragging = true;
            show(3600000);
            mHandler.removeMessages(MSG_SHOW_PROGRESS);
            if (mInstantSeeking) {
                mAM.setStreamMute(AudioManager.STREAM_MUSIC, true);
            }
        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress,
                                      boolean fromuser) {
            if (!fromuser)
                return;

            long newPosition = (mDuration * progress) / 1000;
            String time = generateTime(newPosition);
            setOperationInfo(time, 1500);
            mCurrentTime.setText(time);
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            if (!mInstantSeeking) {
                mPlayer.seekTo((mDuration * bar.getProgress()) / 1000);
            }
            show(DEFAULT_TIME_OUT);
            mAM.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mDragging = false;
            if (mDuration != 0) {
                long curPos = (mDuration * bar.getProgress()) / 1000;
                long proPos = 1000L * curPos / mDuration;
                mProgress.setProgress((int) proPos);
                mPlayer.seekTo(curPos);
            }
        }
    };

    /**
     * 设置 快进 快退进度条 手势结束设置
     *
     * @return
     */
    private long setPlanProgress(long position) {
        if (mPlayer == null || mDragging)
            return 0;
        long duration = mPlayer.getDuration();
        if (duration > 0) {
            long pos = 1000L * position / duration;
            mProgress.setProgress((int) pos);
        }
        int percent = mPlayer.getBufferPercentage();
        mProgress.setSecondaryProgress(percent * 10);

        mDuration = duration;

        mEndTime.setText(generateTime(mDuration));
        mCurrentTime.setText(generateTime(position));
        mPlayer.seekTo(mPlanPos);
        return position;
    }

    // TODO

    /***
     * 设置快进方法
     */
    public void setForwardPlan() {
        mPlanImage.setImageResource(R.drawable.venvy_sdk_media_controller_forward_bg);
        long mDuration = mPlayer.getDuration();
        if (mPlanPos < mDuration - 16 * 1000) {
            mPlanPos += 3 * 1000;
        } else {
            mPlanPos = mDuration - 10 * 1000;
        }
        mPlanTimeView.setText(generateTime(mPlanPos) + "/"
                + generateTime(mDuration));
        mPlanVolLum.setVisibility(VISIBLE);
    }

    /***
     * 设置快退方法
     */
    private void setBackwardPlan() {
        mPlanImage
                .setImageResource(R.drawable.venvy_sdk_media_controller_backward_bg);
        long mDuration = mPlayer.getDuration();
        if (mPlanPos > 3 * 1000) {
            mPlanPos -= 3 * 1000;
        } else {
            mPlanPos = 3 * 1000;
        }
        mPlanTimeView.setText(generateTime(mPlanPos) + "/"
                + generateTime(mDuration));
        mPlanVolLum.setVisibility(VISIBLE);
    }

    /**
     * 转换时间显示
     *
     * @param time 毫秒
     * @return
     */
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes,
                seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    /***
     * 设置横竖屏
     */
    private OnClickListener mDirectionListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!isLocked()) {
                setScreenlandscape(mContext);
            }
        }
    };

    /***
     * 设置设置为横屏 竖
     *
     * @param context
     */
    public static void setScreenlandscape(Activity context) {
        Log.e("Video++", "===================================自定义控制器");
        if (context.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 设置为横屏

        } else {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置为竖屏
        }
    }

    /***
     * 设置手势区域
     */
    private MediaContollerTouchListener mTouchListener = new MediaContollerTouchListener() {
        /***
         * 手势开始
         */
        @Override
        public void onGestureBegin() {
            mBrightness = mContext.getWindow().getAttributes().screenBrightness;
            mVolume = mAM.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;
            if (mVolume < 0)
                mVolume = 0;
            /**
             * 设置快进 快退当前时间
             */
            mPlanPos = mPlayer.getCurrentPosition();
        }

        /***
         * 手势结束
         */
        @Override
        public void onGestureEnd() {
            if (mPlanVolLum.getVisibility() == VISIBLE) {
                if (mPlanPos != 0) {
                    setPlanProgress(mPlanPos);
                }
            }
            mOperationVolLum.setVisibility(View.GONE);
            mPlanVolLum.setVisibility(GONE);
        }

        /***
         * 控制亮度
         *
         * @param percent
         */
        @Override
        public void onLeftSlide(float percent) {
            setBrightness(mBrightness + percent);
            setBrightnessScale(mContext.getWindow().getAttributes().screenBrightness);
        }

        /***
         * 控制声音
         *
         * @param percent
         */
        @Override
        public void onRightSlide(float percent) {
            int v = (int) (percent * mMaxVolume) + mVolume;
            setVolume(v);
        }

        /**
         * 快退
         *
         * @param percent
         */
        @Override
        public void onLeftSpeedSlide(float percent) {
            setBackwardPlan();
        }

        /**
         * 快进
         *
         * @param percent
         */
        @Override
        public void onRightSpeedSlide(float percent) {
            setForwardPlan();
        }

        /***
         * 单击
         */
        @Override
        public void onSingleTap() {
            if (mShowing)
                hide();
            else
                show();
        }

        /***
         * 双击
         */
        @Override
        public void onDoubleTap() {
            if (mPlayer.isPlaying()) {
                show(SHOW_TIME_CONTINUE);
                mPlayer.pause();
            } else {
                mPlayer.start();
            }
        }

        @Override
        public void onScale(float scaleFactor, int state) {
            switch (state) {
                case Gestures.SCALE_STATE_BEGIN:
                    break;
                case Gestures.SCALE_STATE_SCALEING:
                    break;
                case Gestures.SCALE_STATE_END:
                    break;
            }
        }

        @Override
        public void onLongPress() {
            doPauseResume();
        }
    };
    /***
     * 控制器锁监听时间
     */
    private OnClickListener mLockClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            hide();
            lock(!mScreenLocked);
            show();
        }
    };

    /***
     * 设置播放器开始 暂停
     */
    private void doPauseResume() {
        if (mPlayer.isPlaying())
            mPlayer.pause();
        else
            mPlayer.start();
        updatePausePlay();
    }

    /***
     * 视频播放按钮监听
     */
    private OnClickListener mPauseListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isLocked()) {
                if (!mCompletiond) {
                    if (mPlayer.isPlaying())
                        show(DEFAULT_LONG_TIME_SHOW);
                    else
                        show();
                } else {
                    mControlsLayout.startAnimation(mAnimSlideOutTop);
                    mSystemInfoLayout.startAnimation(mAnimSlideOutBottom);
                    mShowing = false;
                    mCompletiond = false;
                }
                doPauseResume();
            }
        }
    };
    private OnClickListener mBackListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isLocked()) {
                if (mPlayer.isPlaying())
                    mContext.finish();
            }
        }
    };

    /***
     * 复写Touch事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHandler.removeMessages(MSG_HIDE_SYSTEM_UI);
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_SYSTEM_UI, DEFAULT_TIME_OUT);
        return mGestures.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        show(DEFAULT_TIME_OUT);
        return false;
    }

    /***
     * 事件分发
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_MUTE:
                return super.dispatchKeyEvent(event);
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                mVolume = mAM.getStreamVolume(AudioManager.STREAM_MUSIC);
                int step = keyCode == KeyEvent.KEYCODE_VOLUME_UP ? 1 : -1;
                setVolume(mVolume + step);
                mHandler.removeMessages(MSG_HIDE_OPERATION_VOLLUM);
                mHandler.sendEmptyMessageDelayed(MSG_HIDE_OPERATION_VOLLUM, 500);
                return true;
        }

        if (isLocked()) {
            show();
            return true;
        }

        if (event.getRepeatCount() == 0
                && (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE || keyCode == KeyEvent.KEYCODE_SPACE)) {
            doPauseResume();
            show(DEFAULT_TIME_OUT);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                updatePausePlay();
            }
            return true;
        } else {
            show(DEFAULT_TIME_OUT);
        }
        return super.dispatchKeyEvent(event);
    }

    // ////////////////////要复写的方法

    /***
     * 设置视频标题
     */
    @Override
    public void setFileName(String name) {
        super.setFileName(name);
        mFileName.setText(name);
    }

    /***
     * 隐藏控制器
     */
    @Override
    public void hide() {
        super.hide();
        if (mShowing) {
            try {
                mHandler.removeMessages(MSG_TIME_TICK);
                mHandler.removeMessages(MSG_SHOW_PROGRESS);
                if (mPlayer.isPlaying()) {
                    mControlsLayout.startAnimation(mAnimSlideOutTop);
                    mSystemInfoLayout.startAnimation(mAnimSlideOutBottom);
                    mShowing = false;
                } else {
                    mShowing = true;
                }
            } catch (IllegalArgumentException ex) {
            }

        }
    }

    /***
     * 设置显示时间默认时间
     */
    @Override
    public void show() {
        super.show();
    }

    /***
     * 设置上下控制器显示时间
     *
     * @param timeout
     */
    @Override
    public void show(int timeout) {
        super.show(timeout);
        if (timeout != 0) {
            mHandler.removeMessages(MSG_FADE_OUT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_FADE_OUT),
                    timeout);
        }
        if (!mShowing) {
            showButtons(true);
            mHandler.removeMessages(MSG_HIDE_SYSTEM_UI);
            showSystemUi(true);
            mPauseButton.requestFocus();
            if (mPlayer.isPlaying()) {
                mControlsLayout.startAnimation(mAnimSlideInTop);
                mSystemInfoLayout.startAnimation(mAnimSlideInBottom);
                mShowing = true;
            }
            mMediaController.setVisibility(View.VISIBLE);
            updatePausePlay();
            mHandler.sendEmptyMessage(MSG_TIME_TICK);
            mHandler.sendEmptyMessage(MSG_SHOW_PROGRESS);

        }
    }

    /***
     * 获取视频源 清晰度 mode==0 1080P mode==1 超清 mode==2 高清 mode==3 标清 mode==4 极速
     * 清晰度名称可自定义
     */
    private TextView m1080;// 要显示的
    private TextView m720;
    private TextView m480P;
    private TextView m360P;
    // @Override
    // public void getVideoQuality(List<Integer> data) {
    // super.getVideoQuality(data);
    // // for (int x = 0; x < data.size(); x++) {
    // // int mode = data.get(x);
    // // switch (mode) {
    // // case 0:
    // // m1080.setText("1080p");
    // // break;
    // // case 1:
    // // m720.setText("720p");
    // // break;
    // // case 2:
    // // m480P.setText("480P");
    // // break;
    // // case 3:
    // // m360P.setText("360P");
    // // break;
    // // default:
    // // break;
    // // }
    // // }
    // }

    // 清晰度切换点击方法m1080
    private OnClickListener m1080pListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPlayer.setVideoQuality(0);
        }
    };

    /***
     * 设置控制器
     */
    @Override
    public void setMediaPlayer(MediaPlayerControl player) {
        super.setMediaPlayer(player);
        mPlayer = player;
        updatePausePlay();
    }

    /***
     * 横屏特殊操作
     */
    @SuppressLint("NewApi")
    @Override
    public void setVideoCofLand() {
        // TODO Auto-generated method stub
        super.setVideoCofLand();
        mContext.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /***
     * 竖屏特殊操作
     */
    @SuppressLint("NewApi")
    @Override
    public void setVideoCofPort() {
        // TODO Auto-generated method stub
        super.setVideoCofPort();
        mContext.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private boolean mCompletiond;// 判断是否视频播放结束

    /***
     * 视频播放完毕自动回调
     */
    @Override
    public void onVideoCompletion() {
        super.onVideoCompletion();
        mCompletiond = true;
        show();
    }
}

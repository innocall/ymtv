package com.lemon95.ymtv.presenter;

import android.os.Handler;
import android.os.Message;

import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.Video;
import com.lemon95.ymtv.bean.VideoType;
import com.lemon95.ymtv.bean.impl.ISplashBean;
import com.lemon95.ymtv.dao.SplashDao;
import com.lemon95.ymtv.db.DataBaseDao;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.view.activity.SplashActivity;

import java.util.List;

/**
 * Created by WXT on 2016/7/14.
 * 接口顺序
 * 1、检测app版本
 * 2、获取每日推荐数据
 *
 */
public class SplashPresenter {

    private static final String TAG = "SplashPresenter";
    private static final int TOMAIN = 1;
    private SplashActivity splashActivity;
    private ISplashBean iSplashBean;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TOMAIN:
                    splashActivity.toMainActivity();
                    break;
            }
        }
    };

    public SplashPresenter(SplashActivity splashActivity) {
        this.splashActivity = splashActivity;
        iSplashBean = new SplashDao();
    }

    /**
     * 检测版本
     * @param newVersion
     */
    public void checkVersion(String newVersion) {
        iSplashBean.checkVersion(newVersion, new SplashDao.OnVersionLisener() {
            @Override
            public void updateVersion(Version version) {
                if (version.isUpdate()) {
                    splashActivity.showToastShort("强制更新");
                } else {
                    splashActivity.showToastShort("非强制更新");
                }
            }

            @Override
            public void noUpdateVersion() {
                splashActivity.showToastShort("最新版本");
                getRecommends();
            }
        });
    }

    /**
     * 获取每日推荐数据
     */
    public void getRecommends() {
        iSplashBean.getRecommends(new SplashDao.OnVideoListener() {
            @Override
            public void onSuccess(Recommend recommend) {
                //数据获取成功
                final List<Recommend.Data> data = recommend.getData();
                if (data == null) {
                    LogUtils.e(TAG, "每日推荐获取为空");
                } else {
                    initVideoData(data);  //保存数据到数据库
                }
                initVideoType();
            }

            @Override
            public void onFailure(Throwable e) {
                initVideoType();
            }
        });
    }

    /**
     * 获取影视分类
     */
    public void initVideoType() {
        iSplashBean.getVideoType(new SplashDao.OnVideoTypeListener() {
            @Override
            public void onSuccess(VideoType videoType) {
                //数据获取成功
                final List<VideoType.Data> data = videoType.getData();
                if (data == null) {
                    LogUtils.e(TAG, "每日推荐获取为空");
                } else {
                    saveDateToVideoType(data);
                }
                splashActivity.toMainActivity();
            }

            @Override
            public void onFailure(Throwable e) {
                splashActivity.toMainActivity();
            }
        });
    }

    /**
     * 保存影视分类数据到数据库
     * @param data
     */
    private void saveDateToVideoType(List<VideoType.Data> data) {
        for (int i=0;i<data.size();i++) {
            com.lemon95.ymtv.bean.VideoType.Data d = data.get(i);
            //保存数据到数据库
            DataBaseDao baseDao = new DataBaseDao(splashActivity);
            baseDao.addOrUpdateVideoType(d);
        }
    }

    /**
     * 保存每日推荐数据到数据库
     * @param data
     */
    private void initVideoData(List<Recommend.Data> data) {
        for (int i=0;i<data.size();i++) {
            com.lemon95.ymtv.bean.Recommend.Data d = data.get(i);
            Video video = new Video();
            video.setVideoTypeId(d.getVideoTypeId());
            video.setVideoId(d.getVideoId());
            video.setTitle(d.getTitle());
            video.setOrderNum(d.getOrderNum());
            video.setPicturePath(d.getPicturePath());
            //保存数据到数据库
            DataBaseDao baseDao = new DataBaseDao(splashActivity);
            baseDao.addOrUpdateVideo(video);
        }
    }



    public void start() {
        //检测版本更新
        checkVersion(splashActivity.getVersion());
    }
}

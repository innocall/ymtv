package com.lemon95.ymtv.bean.impl;

import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.Video;
import com.lemon95.ymtv.dao.SplashDao;

import java.util.List;

/**
 * Created by WXT on 2016/7/14.
 * 启动页 暴露的方法
 */
public interface ISplashBean {

    /**
     * 检测版本
     * @param url
     * @param onVersionLisener
     */
    public void checkVersion(String url,SplashDao.OnVersionLisener onVersionLisener);

    /**
     * 获取每日推荐影片
     */
    public void getRecommends(SplashDao.OnVideoListener onVideoListener);

    public void getVideoType(SplashDao.OnVideoTypeListener onVideoListener);

    public void downImg(String imgUrl,String oldPath,SplashDao.OnImageDownListener onImageDownListener);

    public void deviceLogin(String token,SplashDao.OnDeviceLoginListener onDeviceLoginListener);

    public void GenerateToken(String mac,SplashDao.OnResultListener onResultListener);

}

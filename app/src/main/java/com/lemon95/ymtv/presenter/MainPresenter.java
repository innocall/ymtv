package com.lemon95.ymtv.presenter;

import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Video;
import com.lemon95.ymtv.bean.VideoType;
import com.lemon95.ymtv.bean.impl.ISplashBean;
import com.lemon95.ymtv.dao.SplashDao;
import com.lemon95.ymtv.db.DataBaseDao;
import com.lemon95.ymtv.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXT on 2016/7/15.
 */
public class MainPresenter {

    private MainActivity mainActivity;
    private ISplashBean iSplashBean;

    public MainPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        iSplashBean = new SplashDao();
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
                    mainActivity.showToastShort("服务器数据异常");
                } else {
                    //显示界面图片
                    List<Video> list = new ArrayList<Video>();
                    for (int i = 0; i < data.size(); i++) {
                        com.lemon95.ymtv.bean.Recommend.Data d = data.get(i);
                        Video video = new Video();
                        video.setVideoTypeId(d.getVideoTypeId());
                        video.setVideoId(d.getVideoId());
                        video.setTitle(d.getTitle());
                        video.setOrderNum(d.getOrderNum());
                        video.setPicturePath(d.getPicturePath());
                        list.add(video);
                    }
                    mainActivity.showRecommends(list);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                mainActivity.showToastShort("数据获取失败,请检查网络连接");
            }
        });
    }

    private void initVideoData(List<Recommend.Data> data) {
        for (int i = 0; i < data.size(); i++) {
            com.lemon95.ymtv.bean.Recommend.Data d = data.get(i);
            Video video = new Video();
            video.setVideoTypeId(d.getVideoTypeId());
            video.setVideoId(d.getVideoId());
            video.setTitle(d.getTitle());
            video.setOrderNum(d.getOrderNum());
            video.setPicturePath(d.getPicturePath());
            //保存数据到数据库
            DataBaseDao baseDao = new DataBaseDao(mainActivity);
            baseDao.addOrUpdateVideo(video);
        }
    }

    public void getVideoType() {
        iSplashBean.getVideoType(new SplashDao.OnVideoTypeListener() {
            @Override
            public void onSuccess(VideoType videoType) {
                //数据获取成功
                final List<VideoType.Data> data = videoType.getData();
                if (data == null) {
                    mainActivity.showToastShort("服务器数据异常");
                } else {
                    mainActivity.showVideoType(data);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                mainActivity.showToastShort("数据获取失败,请检查网络连接");
            }
        });
    }
}

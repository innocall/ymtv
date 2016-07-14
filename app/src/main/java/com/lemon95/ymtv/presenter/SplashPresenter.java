package com.lemon95.ymtv.presenter;

import android.nfc.Tag;

import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.Video;
import com.lemon95.ymtv.bean.impl.ISplashBean;
import com.lemon95.ymtv.dao.SplashDao;
import com.lemon95.ymtv.db.DataBaseDao;
import com.lemon95.ymtv.view.activity.SplashActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXT on 2016/7/14.
 * 接口顺序
 * 1、检测app版本
 * 2、获取每日推荐数据
 *
 */
public class SplashPresenter {

    private SplashActivity splashActivity;
    private ISplashBean iSplashBean;

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
                List<Recommend.Data> data = recommend.getData();
                if (data == null) {

                } else {
                    DataBaseDao baseDao = new DataBaseDao(splashActivity);
                    List<Video> videoList = baseDao.getAllVideoList();
                    initVideoData(data, videoList, baseDao);
                }
            }

            @Override
            public void onFailure(Throwable e) {
            }
        });
    }

    /**
     * 处理每日推荐数据
     * @param data
     * @param videoList
     * @param baseDao
     */
    private void initVideoData(List<Recommend.Data> data, List<Video> videoList, DataBaseDao baseDao) {
        if (videoList == null) {
            //数据库没有数据，一般为第一次进入,保存数据到数据库
            videoList = new ArrayList<>();
            for (Recommend.Data d:data ) {

            }
        } else {

        }
    }


    public void start() {
        //检测版本更新
        checkVersion(splashActivity.getVersion());
    }
}

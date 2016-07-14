package com.lemon95.ymtv.dao;

import android.graphics.Bitmap;

import com.lemon95.ymtv.api.ApiManager;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.impl.ISplashBean;
import com.lemon95.ymtv.utils.ImageUtils;
import com.lemon95.ymtv.utils.LogUtils;

import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by WXT on 2016/7/14.
 */
public class SplashDao implements ISplashBean {

    private final String TAG = "SplashDao";

    @Override
    public void checkVersion(final String version,final OnVersionLisener onVersionLisener) {
        ApiManager.getVersion().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Version>() {
                    @Override
                    public void call(Version v) {
                        if (version.equals(v.getVersionId())) {
                            LogUtils.i(TAG, "版本号相同");
                            onVersionLisener.noUpdateVersion();
                        } else {
                            LogUtils.i(TAG, "版本号不同");
                            onVersionLisener.updateVersion(v);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //获取版本失败
                        LogUtils.i(TAG, "获取版本失败");
                        onVersionLisener.noUpdateVersion();
                    }
                });
    }

    @Override
    public void getRecommends(final OnVideoListener onVideoListener) {
        ApiManager.getRecommends().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Recommend>() {

                    @Override
                    public void call(Recommend recommend) {
                        LogUtils.i(TAG, recommend.getReturnMsg());
                        onVideoListener.onSuccess(recommend);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //获取版本失败
                        LogUtils.i(TAG, "获取版本失败");
                        onVideoListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void downImg(final String imgUrl,final OnImageDownListener onImageDownListener) {
        ApiManager.downloadPicFromNet(imgUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {

                    @Override
                    public void call(ResponseBody responseBody) {
                        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1,imgUrl.lastIndexOf("."));
                        ImageUtils.saveImage(responseBody,fileName);
                        onImageDownListener.onSuccess("/myImage/ymtv/" + fileName + ".png");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       onImageDownListener.onFailure(throwable);
                    }
                });
    }


    /**
     * Created by WXT on 2016/7/14.
     * 监听版本变化
     * 2中状态 升级  不升级
     */
    public interface OnVersionLisener {
        //升级版本
        void updateVersion(Version version);
        //版本相同不升级
        void noUpdateVersion();
    }

    public interface OnVideoListener {
        void onSuccess(Recommend recommend);  //获取成功
        void onFailure(Throwable e);  //获取失败
    }

    public interface OnImageDownListener {
        void onSuccess(String fileUrl);
        void onFailure(Throwable e);
    }
}


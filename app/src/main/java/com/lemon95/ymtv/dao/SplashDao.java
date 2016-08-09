package com.lemon95.ymtv.dao;

import android.graphics.Bitmap;

import com.lemon95.ymtv.api.ApiManager;
import com.lemon95.ymtv.bean.DeviceLogin;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Result;
import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.VideoType;
import com.lemon95.ymtv.bean.impl.ISplashBean;
import com.lemon95.ymtv.utils.ImageUtils;
import com.lemon95.ymtv.utils.LogUtils;

import java.io.File;
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
    public void checkVersion(String url,final OnVersionLisener onVersionLisener) {
        ApiManager.getVersion(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody v) {
                        onVersionLisener.updateVersion(v);
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

    /**
     * 获取影视分类
     * @param onVideoListener
     */
    @Override
    public void getVideoType(final OnVideoTypeListener onVideoListener) {
        ApiManager.getVideoType().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoType>() {

                    @Override
                    public void call(VideoType videoType) {
                        LogUtils.i(TAG, videoType.getReturnMsg());
                        onVideoListener.onSuccess(videoType);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //获取版本失败
                        LogUtils.i(TAG, "获取影视类型失败");
                        onVideoListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void downImg(final String imgUrl,final String oldPath,final OnImageDownListener onImageDownListener) {
        ApiManager.downloadPicFromNet(imgUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {

                    @Override
                    public void call(ResponseBody responseBody) {
                        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1,imgUrl.lastIndexOf("."));
                        File file = new File(oldPath);
                        if (file.exists()) {
                            file.delete();
                        }
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
     * 设备登录
     * @param token
     * @param onDeviceLoginListener
     */
    @Override
    public void deviceLogin(String token, final OnDeviceLoginListener onDeviceLoginListener) {
        ApiManager.deviceLogin(token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DeviceLogin>() {

                    @Override
                    public void call(DeviceLogin deviceLogin) {
                        onDeviceLoginListener.onSuccess(deviceLogin);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        onDeviceLoginListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void GenerateToken(String mac, final OnResultListener onResultListener) {
        ApiManager.GenerateToken(mac).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result>() {

                    @Override
                    public void call(Result result) {
                        onResultListener.onSuccess(result);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        onResultListener.onFailure(throwable);
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
        void updateVersion(ResponseBody version);
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

    public interface OnVideoTypeListener {
        void onSuccess(VideoType videoType);
        void onFailure(Throwable e);
    }

    public interface OnDeviceLoginListener {
        void onSuccess(DeviceLogin deviceLogin);
        void onFailure(Throwable e);
    }

    public interface OnResultListener {
        void onSuccess(Result deviceLogin);
        void onFailure(Throwable e);
    }
}


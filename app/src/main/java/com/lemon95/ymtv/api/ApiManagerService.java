package com.lemon95.ymtv.api;

import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.VideoType;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by WXT on 2016/7/14.
 */
public interface ApiManagerService {

    /**
     * 获取每日推荐影视
     * @return
     */
    @GET("/Media/TVs/Recommends")
    Observable<Recommend> getRecommends();

    /**
     * 获取影视分类
     * @return
     */
    @GET("/Media/TVs/Programs")
    Observable<VideoType> getVideoType();

    /**
     * 检测app版本
     * @return
     */
    @GET("/Media/TVs/Version")
    Observable<Version> getVersion();

    /**
     * 下载图片
     * @param fileUrl
     * @return
     */
    @GET
    Observable<ResponseBody> downloadPicFromNet(@Url String fileUrl);
}

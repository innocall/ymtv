package com.lemon95.ymtv.api;

import com.lemon95.ymtv.bean.Conditions;
import com.lemon95.ymtv.bean.GenresMovie;
import com.lemon95.ymtv.bean.Movie;
import com.lemon95.ymtv.bean.MovieSources;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.SerialDitions;
import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.VideoSearchList;
import com.lemon95.ymtv.bean.VideoType;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
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

    @GET("/Media/Movies/Detail")
    Observable<Movie> getDetail(@Query("id") String id,@Query("userId") String userId,@Query("isPersonal") boolean isPersonal);

    /**
     * 获取相应体裁影视
     * @param genreIds
     * @param vipLevel
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GET("/Media/Movies/GetMoviesByGenres")
    Observable<GenresMovie> getMoviesByGenres(@Query("genreIds") String genreIds,@Query("vipLevel") String vipLevel,@Query("currentPage") String currentPage,@Query("pageSize") String pageSize);

    /**
     * 下载图片
     * @param fileUrl
     * @return
     */
    @GET
    Observable<ResponseBody> downloadPicFromNet(@Url String fileUrl);

    /**
     * 查询影视类型
     * @param type
     * @return
     */
    @GET("/Media/Videos/CombQueryConditions")
    Observable<Conditions> getCombQueryConditions(@Query("type") String type);


    /**
     * 查询影视列表
     * @param areaId
     * @param genreId
     * @param groupId
     * @param chargeMethod
     * @param vipLevel
     * @param year
     * @param type
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GET("/Media/Videos/CombSearch")
    Observable<VideoSearchList> getCombSearch(@Query("areaId") String areaId,@Query("genreId") String genreId,@Query("groupId") String groupId,@Query("chargeMethod") String chargeMethod,@Query("vipLevel") String vipLevel,@Query("year") String year,@Query("type") String type,@Query("currentPage") String currentPage,@Query("pageSize") String pageSize);

    /**
     * 获取电视剧详情
     * @param id
     * @return
     */
    @GET("/Media/Serials/Detail")
    Observable<SerialDitions> getSerialDetail(@Query("id") String id);

    /**
     * 获取相应体裁电视剧
     * @param genreIds
     * @param vipLevel
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GET("/Media/Serials/GetSerialsByGenres")
    Observable<GenresMovie> getSerialsByGenres(@Query("genreIds") String genreIds,@Query("vipLevel") String vipLevel,@Query("currentPage") String currentPage,@Query("pageSize") String pageSize);

    /**
     * 通过电影ID获取电影播放地址
     * @param movieId
     * @return
     */
    @GET("/Media/Movies/MovieAnalysis")
    Observable<List<MovieSources>> getMovieAnalysis(@Query("movieId") String movieId);

    /**
     * 解析电视剧播放地址
     * @param episodeId
     * @return
     */
    @GET("/Media/Serials/SerialAnalysis")
    Observable<String> getSerialAnalysis(@Query("episodeId") String episodeId);
}

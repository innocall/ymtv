package com.lemon95.ymtv.api;

import com.lemon95.ymtv.bean.Conditions;
import com.lemon95.ymtv.bean.DeviceLogin;
import com.lemon95.ymtv.bean.Favorite;
import com.lemon95.ymtv.bean.FavoritesBean;
import com.lemon95.ymtv.bean.FirstLettersSearch;
import com.lemon95.ymtv.bean.ForWechat;
import com.lemon95.ymtv.bean.GenresMovie;
import com.lemon95.ymtv.bean.Movie;
import com.lemon95.ymtv.bean.MovieSources;
import com.lemon95.ymtv.bean.PersonalMovies;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.SerialDitions;
import com.lemon95.ymtv.bean.UploadResult;
import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.VideoSearchList;
import com.lemon95.ymtv.bean.VideoType;
import com.lemon95.ymtv.bean.VideoWatchHistory;
import com.lemon95.ymtv.bean.WatchHistories;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
    @GET
    Observable<ResponseBody> getVersion(@Url String url);

    /**
     * 下载更新包
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> downLoadFile(@Url String url);

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

    /**
     * 上传播放记录
     * @param videoWatchHistory
     * @return
     */
    @POST("/Media/Videos/AddVideoWatchHistory")
    Observable<UploadResult> addVideoWatchHistory(@Body VideoWatchHistory videoWatchHistory);

    /**
     * 添加收藏
     * @param favorite
     * @return
     */
//    @Headers({
//        "Accept:*/*",
//        "Accept-Encoding:gzip, deflate",
//        "Accept-Language:zh-CN,zh;q=0.8",
//        "Connection:keep-alive",
//        "Content-Length:70",
//        "Content-Type:application/json; charset=UTF-8"
//    })
    @POST("/Media/Videos/AddFavorite")
    Observable<UploadResult> addFavorite(@Body Favorite favorite);

    /**
     * 获取收藏记录
     * @param mac
     * @param userId
     * @return
     */
    @GET("/Media/Videos/Favorites")
    Observable<FavoritesBean> getFavorites(@Query("mac")String mac,@Query("userId")String userId,@Query("currentPage")String currentPage,@Query("pageSize")String pageSize);

    @POST("/Media/Videos/DeleteFavorites")
    Observable<UploadResult> deleteFavorite(@Body String favoriteId[]);

    @GET("/Media/Videos/PersonalWatchHistories")
    Observable<WatchHistories> getWatchHistories(@Query("currentPage")String currentPage,@Query("pageSize")String pageSize,@Query("mac")String mac,@Query("userId")String userId);

    /**
     * 删除播放记录
     * @param historyIds
     * @return
     */
    @POST("/Media/Videos/DeletePersonalHistories")
    Observable<UploadResult> deletePersonalHistories(@Body String historyIds[]);

    /**
     * 设备登录
     * @param userId
     * @param mac
     * @return
     */
    @GET("/Media/TVs/DeviceLogin")
    Observable<DeviceLogin> deviceLogin(@Query("userId") String userId,@Query("mac")String mac);

    /**
     * 获取求片影视
     * @param userId
     * @param vipLevel
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GET("/Media/Movies/PersonalMovies")
    Observable<PersonalMovies> getPersonalMovies(@Query("userId")String userId,@Query("vipLevel")String vipLevel,@Query("currentPage")String currentPage,@Query("pageSize")String pageSize);

    /**
     * 生成微信扫码订单
     * @param userId
     * @param chargemethod
     * @param videoId
     * @return
     */
    @GET("/Media/Pay/ForWechat")
    Observable<ForWechat> getForWechat(@Query("userId")String userId,@Query("chargemethod")String chargemethod,@Query("videoId")String videoId);

    /**
     * 首字母搜索
     * @param firstLetters
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GET("/Media/TVs/FirstLettersSearch")
    Observable<FirstLettersSearch> getFirstLettersSearch(@Query("firstLetters") String firstLetters,@Query("currentPage")String currentPage,@Query("pageSize")String pageSize);

}

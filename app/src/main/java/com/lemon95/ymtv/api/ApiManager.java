package com.lemon95.ymtv.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lemon95.ymtv.bean.Conditions;
import com.lemon95.ymtv.bean.DeviceLogin;
import com.lemon95.ymtv.bean.Favorite;
import com.lemon95.ymtv.bean.FavoritesBean;
import com.lemon95.ymtv.bean.FirstLettersSearch;
import com.lemon95.ymtv.bean.ForWechat;
import com.lemon95.ymtv.bean.GenresMovie;
import com.lemon95.ymtv.bean.GetOrder;
import com.lemon95.ymtv.bean.Movie;
import com.lemon95.ymtv.bean.MovieSources;
import com.lemon95.ymtv.bean.PersonalMovies;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Result;
import com.lemon95.ymtv.bean.SerialDitions;
import com.lemon95.ymtv.bean.UploadResult;
import com.lemon95.ymtv.bean.VideoSearchList;
import com.lemon95.ymtv.bean.VideoType;
import com.lemon95.ymtv.bean.VideoWatchHistory;
import com.lemon95.ymtv.bean.WatchHistories;
import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by WXT on 2016/7/14.
 */
public class ApiManager {

    private static final String ENDPOINT = "http://media.lemon95.com";
    private static String SecretKey = "123456789";
    private static String AppKey = "com.lemon95.lemonvideo";

    public static Gson getGson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        return gson;
    }

    public static OkHttpClient genericClient() {
        // Log信息
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("SecretKey", SecretKey)
                                .addHeader("AppKey", AppKey)
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }

                }).addInterceptor(loggingInterceptor)
                .build();

        return httpClient;
    }

    private static final Retrofit sRetrofit = new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
            .client(genericClient())
            .build();

    private static final ApiManagerService apiManager = sRetrofit.create(ApiManagerService.class);

    /**
     * 获取每日推荐影视
     * @return
     */
    public static Observable<Recommend> getRecommends() {
        return apiManager.getRecommends();
    }

    /**
     * 获取服务器版本
     * @return
     */
    public static Observable<ResponseBody> getVersion(String url) {
        return apiManager.getVersion(url);
    }

    public static Observable<VideoType> getVideoType() {
        return apiManager.getVideoType();
    }

    /**
     * 获取影视详情
     * @param id
     * @param userId
     * @param isPersonal
     * @return
     */
    public static Observable<Movie> getDetails(String id,String userId,boolean isPersonal) {
        return apiManager.getDetail(id, userId, isPersonal);
    }

    /**
     * 下载图片
     * @param fileUrl
     * @return
     */
    public static Observable<ResponseBody> downloadPicFromNet(String fileUrl){
        return apiManager.downloadPicFromNet(fileUrl);
    }

    /**
     * 获取相应体裁影视
     * @param genreIds
     * @param vipLevel
     * @param currenPage
     * @param pageSize
     * @return
     */
    public static Observable<GenresMovie> getMoviesByGenres(String genreIds,String vipLevel,String currenPage,String pageSize) {
        return apiManager.getMoviesByGenres(genreIds, vipLevel, currenPage, pageSize);
    }

    /**
     * 获取相应题材电视剧
     * @param genreIds
     * @param vipLevel
     * @param currenPage
     * @param pageSize
     * @return
     */
    public static Observable<GenresMovie> getSerialsByGenres(String genreIds,String vipLevel,String currenPage,String pageSize) {
        return apiManager.getSerialsByGenres(genreIds, vipLevel, currenPage, pageSize);
    }

    /**
     * 查询影视类型
     * @param type
     * @return
     */
    public static Observable<Conditions> getCombQueryConditions(String type) {
        return apiManager.getCombQueryConditions(type);
    }

    /**
     * 获取影视列表
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
    public static Observable<VideoSearchList> getCombSearch(String areaId,String genreId,String groupId,String chargeMethod,String vipLevel,String year,String type,String currentPage,String pageSize) {
        return apiManager.getCombSearch(areaId, genreId, groupId, chargeMethod, vipLevel, year, type, currentPage, pageSize);
    }

    /**
     * 获取电视剧详情
     * @param id
     * @return
     */
    public static Observable<SerialDitions> getSerialDetail(String id) {
        return apiManager.getSerialDetail(id);
    }

    /**
     * 通过电影ID获取电影播放地址
     * @param movieId
     * @return
     */
    public static Observable<List<MovieSources>> getMovieAnalysis(String movieId) {
        return apiManager.getMovieAnalysis(movieId);
    }

    /**
     * 解析电视剧
     * @param episodeId
     * @return
     */
    public static Observable<String> getSerialAnalysis(String episodeId) {
        return apiManager.getSerialAnalysis(episodeId);
    }

    /**
     * 上传播放记录
     * @param videoWatchHistory
     * @return
     */
    public static  Observable<UploadResult> addVideoWatchHistory(VideoWatchHistory videoWatchHistory) {
        return apiManager.addVideoWatchHistory(videoWatchHistory);
    }

    /**
     * 上传收藏记录
     * @param favorite
     * @return
     */
    public static  Observable<UploadResult> addFavorite(Favorite favorite) {
        return apiManager.addFavorite(favorite);
    }

    /**
     * 获取收藏记录
     * @param mac
     * @param userId
     * @return
     */
    public static Observable<FavoritesBean> getFavorites(String mac,String userId,String currentPage,String pageSize){
        return apiManager.getFavorites(mac, userId,currentPage,pageSize);
    }

    /**
     * 根据ID删除收藏影片
     * @param favoriteId
     * @return
     */
    public static  Observable<UploadResult> deleteFavorite(String favoriteId[]){
        return apiManager.deleteFavorite(favoriteId);
    }

    /**
     * 获取观看记录
     * @param currentPage
     * @param pageSize
     * @param mac
     * @param userId
     * @return
     */
    public static Observable<WatchHistories> getWatchHistories(String currentPage,String pageSize,String mac,String userId) {
        return apiManager.getWatchHistories(currentPage,pageSize,mac,userId);
    }

    /**
     * 更加ID删除播放记录
     * @param historyIds
     * @return
     */
    public static Observable<UploadResult> deletePersonalHistories(String historyIds[]) {
        return apiManager.deletePersonalHistories(historyIds);
    }

    /**
     * 设备登录
     * @param token
     * @return
     */
    public static Observable<DeviceLogin> deviceLogin(String token) {
        return apiManager.deviceLogin(token);
    }

    /**
     * 获取求片影视
     * @param userId
     * @param vipLevel
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static Observable<PersonalMovies> getPersonalMovies(String userId,String vipLevel,String currentPage,String pageSize) {
        return apiManager.getPersonalMovies(userId, vipLevel, currentPage, pageSize);
    }

    /**
     * 生成微信扫描支付订单
     * @param userId
     * @param chargemethod
     * @param videoId
     * @return
     */
    public static Observable<ForWechat> getForWechat(String userId,String chargemethod,String videoId) {
        return apiManager.getForWechat(userId, chargemethod, videoId);
    }

    /**
     * 首字母搜索
     * @param firstLetters
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static Observable<FirstLettersSearch> getFirstLettersSearch(String firstLetters,String currentPage,String pageSize){
        return apiManager.getFirstLettersSearch(firstLetters, currentPage, pageSize);
    }

    /**
     * 下载更新包
     * @param url
     * @return
     */
    public static Observable<ResponseBody> downLoadFile(String url) {
        return apiManager.downLoadFile(url);
    }

    /**
     * 查询订单信息
     * @param orderNo
     * @return
     */
    public static Observable<GetOrder> getOrder(String orderNo) {
        return apiManager.getOrder(orderNo);
    }

    public static Observable<Result> GenerateToken(String mac) {
        return apiManager.GenerateToken(mac);
    }
}

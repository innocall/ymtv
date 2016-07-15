package com.lemon95.ymtv.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.Version;
import com.lemon95.ymtv.bean.Video;
import com.lemon95.ymtv.bean.VideoType;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("SecretKey", SecretKey)
                                .addHeader("AppKey", AppKey)
                                .build();
                        return chain.proceed(request);
                    }

                })
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
    public static Observable<Version> getVersion() {
        return apiManager.getVersion();
    }

    public static Observable<VideoType> getVideoType() {
        return apiManager.getVideoType();
    }

    /**
     * 下载图片
     * @param fileUrl
     * @return
     */
    public static Observable<ResponseBody> downloadPicFromNet(String fileUrl){
        return apiManager.downloadPicFromNet(fileUrl);
    }
}

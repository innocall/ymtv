package com.lemon95.ymtv.api;

import com.lemon95.ymtv.bean.Unifiedorder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by WXT on 2016/8/3.
 */
public class WXApiManager {
    private static final String WP_QR = "https://api.mch.weixin.qq.com";

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
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }

                }).addInterceptor(loggingInterceptor)
                .build();

        return httpClient;
    }

    private static final Retrofit sRetrofit = new Retrofit.Builder()
            .baseUrl(WP_QR)
            .client(genericClient())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
            .build();

    private static final WXApiManagerService apiManager = sRetrofit.create(WXApiManagerService.class);

    public static Observable<ResponseBody> unifiedorder(String xml) {
        RequestBody requestBody1 =  RequestBody.create(MediaType.parse("text/plain"), xml);
        return apiManager.unifiedorder(requestBody1);
    }


}

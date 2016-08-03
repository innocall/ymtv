package com.lemon95.ymtv.api;


import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by WXT on 2016/8/3.
 */
public interface WXApiManagerService {

    @POST("/pay/unifiedorder")
    Observable<ResponseBody> unifiedorder(@Body RequestBody xml);

}

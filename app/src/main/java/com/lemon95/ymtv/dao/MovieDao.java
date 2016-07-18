package com.lemon95.ymtv.dao;

import com.lemon95.ymtv.api.ApiManager;
import com.lemon95.ymtv.bean.GenresMovie;
import com.lemon95.ymtv.bean.Movie;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.utils.LogUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by WXT on 2016/7/18.
 */
public class MovieDao implements IMovieBean{


    private static final String TAG = "MovieDao";

    @Override
    public void getMovieDetails(String id, String userId, boolean isPersonal,final OnMovieDetailsListener movieDetailsListener) {
        ApiManager.getDetails(id,userId,isPersonal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Movie>() {

                    @Override
                    public void call(Movie movie) {
                        LogUtils.i(TAG, movie.getReturnMsg());
                        movieDetailsListener.onSuccess(movie);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //获取版本失败
                        LogUtils.i(TAG, "获取影视类型失败");
                        movieDetailsListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void getMoviesByGenres(String genreIds, String vipLevel, String currenPage, String pageSize,final OnGenresMovieDetailsListener onVideoListener) {
        ApiManager.getMoviesByGenres(genreIds, vipLevel, currenPage, pageSize).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GenresMovie>() {

                    @Override
                    public void call(GenresMovie movie) {
                        LogUtils.i(TAG, movie.getReturnMsg());
                        onVideoListener.onSuccess(movie);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //获取版本失败
                        LogUtils.i(TAG, "相应题材影视获取失败");
                        onVideoListener.onFailure(throwable);
                    }
                });
    }

    public interface OnMovieDetailsListener{
        void onSuccess(Movie movie);  //获取成功
        void onFailure(Throwable e);  //获取失败
    }

    public interface OnGenresMovieDetailsListener{
        void onSuccess(GenresMovie movie);  //获取成功
        void onFailure(Throwable e);  //获取失败
    }
}

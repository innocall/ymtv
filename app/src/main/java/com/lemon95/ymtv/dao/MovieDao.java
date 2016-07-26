package com.lemon95.ymtv.dao;

import com.lemon95.ymtv.api.ApiManager;
import com.lemon95.ymtv.bean.Conditions;
import com.lemon95.ymtv.bean.GenresMovie;
import com.lemon95.ymtv.bean.Movie;
import com.lemon95.ymtv.bean.MovieSources;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.SerialDitions;
import com.lemon95.ymtv.bean.UploadResult;
import com.lemon95.ymtv.bean.VideoSearchList;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.utils.LogUtils;

import java.util.List;

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

    @Override
    public void getSerialsByGenres(String genreIds, String vipLevel, String currenPage, String pageSize, final OnGenresMovieDetailsListener onVideoListener) {
        ApiManager.getSerialsByGenres(genreIds, vipLevel, currenPage, pageSize).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    @Override
    public void getCombQueryConditions(String type, final OnConditionsListener onVideoListener) {
        ApiManager.getCombQueryConditions(type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Conditions>() {

                    @Override
                    public void call(Conditions conditions) {
                        LogUtils.i(TAG, conditions.getReturnMsg());
                        onVideoListener.onSuccess(conditions);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //获取版本失败
                        LogUtils.i(TAG, "影视查询类型获取失败");
                        onVideoListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void getCombSearch(String areaId, String genreId, String groupId, String chargeMethod, String vipLevel, String year, String type, String currentPage, String pageSize, final OnVideoSearchListListener onVideoSearchListListener) {
        ApiManager.getCombSearch(areaId, genreId, groupId, chargeMethod, vipLevel, year, type, currentPage, pageSize).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoSearchList>() {

                    @Override
                    public void call(VideoSearchList videoSearchList) {
                        LogUtils.i(TAG, videoSearchList.getReturnMsg());
                        onVideoSearchListListener.onSuccess(videoSearchList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //获取版本失败
                        LogUtils.i(TAG, "影视列表获取失败");
                        onVideoSearchListListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void getSerialDetail(String id, final OnSerialDitionListener onSerialDitionListener) {
        ApiManager.getSerialDetail(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SerialDitions>() {

                    @Override
                    public void call(SerialDitions serialDitions) {
                        LogUtils.i(TAG, serialDitions.getReturnMsg());
                        onSerialDitionListener.onSuccess(serialDitions);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.i(TAG, "电视剧详情获取失败");
                        onSerialDitionListener.onFailure(throwable);
                    }
                });
    }

    /**
     * 解析电影
     * @param id
     * @param onMovieAnalysisListener
     */
    @Override
    public void getMovieAnalysis(String id, final OnMovieAnalysisListener onMovieAnalysisListener) {
        ApiManager.getMovieAnalysis(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MovieSources>>() {

                    @Override
                    public void call(List<MovieSources> movieSources) {
                        onMovieAnalysisListener.onSuccess(movieSources);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        onMovieAnalysisListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void getSerialAnalysis(String id, final OnSerialAnalysisListener onMovieAnalysisListener) {
        ApiManager.getSerialAnalysis(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {

                    @Override
                    public void call(String movieSources) {
                        onMovieAnalysisListener.onSuccess(movieSources);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        onMovieAnalysisListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void addVideoWatchHistory(String mobile, final OnUpdateListener onUpdateListener) {
        ApiManager.addVideoWatchHistory(mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UploadResult>() {

                    @Override
                    public void call(UploadResult uploadResult) {
                        onUpdateListener.onSuccess(uploadResult);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        onUpdateListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void addFavorite(String mobile, final OnUpdateListener onUpdateListener) {
        ApiManager.addFavorite(mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UploadResult>() {

                    @Override
                    public void call(UploadResult uploadResult) {
                        onUpdateListener.onSuccess(uploadResult);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        onUpdateListener.onFailure(throwable);
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

    public interface OnConditionsListener{
        void onSuccess(Conditions movie);  //获取成功
        void onFailure(Throwable e);  //获取失败
    }

    public interface OnVideoSearchListListener{
        void onSuccess(VideoSearchList videoSearchList);  //获取成功
        void onFailure(Throwable e);  //获取失败
    }

    public interface OnSerialDitionListener{
        void onSuccess(SerialDitions serialDitions);  //获取成功
        void onFailure(Throwable e);  //获取失败
    }

    public interface OnMovieAnalysisListener{
        void onSuccess(List<MovieSources> movieSources);
        void onFailure(Throwable e);
    }

    public interface OnSerialAnalysisListener{
        void onSuccess(String movieSources);
        void onFailure(Throwable e);
    }

    public interface OnUpdateListener{
        void onSuccess(UploadResult uploadResult);
        void onFailure(Throwable e);
    }
}

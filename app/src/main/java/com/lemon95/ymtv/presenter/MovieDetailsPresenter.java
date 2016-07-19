package com.lemon95.ymtv.presenter;

import android.widget.TextView;

import com.lemon95.ymtv.bean.GenresMovie;
import com.lemon95.ymtv.bean.Movie;
import com.lemon95.ymtv.bean.Recommend;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.dao.SplashDao;
import com.lemon95.ymtv.view.activity.MovieDetailsActivity;

import java.util.List;

/**
 * Created by WXT on 2016/7/18.
 * 影视详情页面
 */
public class MovieDetailsPresenter {

    private MovieDetailsActivity movieDetailsActivity;
    private IMovieBean iMovieBean;

    public MovieDetailsPresenter(MovieDetailsActivity movieDetailsActivity) {
        this.movieDetailsActivity = movieDetailsActivity;
        iMovieBean = new MovieDao();
    }

    public void initPageData(String id,String userId,boolean isPre) {
        iMovieBean.getMovieDetails(id,userId,isPre, new MovieDao.OnMovieDetailsListener(){

            @Override
            public void onSuccess(Movie movie) {
                com.lemon95.ymtv.bean.Movie.Data data = movie.getData();
                if (data != null) {
                    movieDetailsActivity.initViewMoveDate(data);
                   // movieDetailsActivity.hidePro();
                    //获取相应题材影视
                    getMoviesByGenres(data.getVideoGenreIds(),"1","1","7");
                } else {
                    movieDetailsActivity.showToastLong("数据获取为空");
                }
            }

            @Override
            public void onFailure(Throwable e) {
                movieDetailsActivity.showToastLong("数据获取失败");
                movieDetailsActivity.finish();
            }
        });
    }

    private void getMoviesByGenres(String videoGenreIds, String s, String s1, String s2) {
        iMovieBean.getMoviesByGenres(videoGenreIds, s, s1, s2, new MovieDao.OnGenresMovieDetailsListener() {
            @Override
            public void onSuccess(GenresMovie movie) {
                List<GenresMovie.Data> dataList = movie.getData();
                if (dataList != null && dataList.size() > 0) {
                    movieDetailsActivity.initViewByGenresMoveDate(dataList);
                    movieDetailsActivity.hidePro();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                movieDetailsActivity.showToastLong("相关影视获取失败");
                movieDetailsActivity.finish();
            }
        });
    }


}

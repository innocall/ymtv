package com.lemon95.ymtv.bean.impl;

import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.dao.SplashDao;

/**
 * Created by WXT on 2016/7/18.
 *
 * 影视模块暴露的方法
 */
public interface IMovieBean {

    /**
     * 获取影视详情
     * @param id
     * @param userId
     * @param isPersonal
     */
    public void getMovieDetails(String id,String userId,boolean isPersonal,MovieDao.OnMovieDetailsListener movieDetailsListener);

    /**
     * 获取相应题材影视
     * @param onVideoListener
     */
    public void getMoviesByGenres(String genreIds,String vipLevel,String currenPage,String pageSize,MovieDao.OnGenresMovieDetailsListener onVideoListener);

}

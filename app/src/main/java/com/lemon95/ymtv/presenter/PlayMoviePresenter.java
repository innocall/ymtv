package com.lemon95.ymtv.presenter;

import com.google.gson.Gson;
import com.lemon95.ymtv.bean.Movie;
import com.lemon95.ymtv.bean.MovieSources;
import com.lemon95.ymtv.bean.RealSource;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.StringUtils;
import com.lemon95.ymtv.view.activity.PlayActivity;

import java.util.List;

/**
 * Created by wuxiaotie on 16/7/23.
 * 电影播放页
 */
public class PlayMoviePresenter {

    private PlayActivity playActivity;
    private IMovieBean iMovieBean;

    public PlayMoviePresenter(PlayActivity playActivity) {
        this.playActivity = playActivity;
        iMovieBean = new MovieDao();
    }


    public void getPlayUrl(String videoId) {
        iMovieBean.getMovieAnalysis(videoId, new MovieDao.OnMovieAnalysisListener() {

            @Override
            public void onSuccess(List<MovieSources> movieSources) {
                if (movieSources != null && movieSources.size() > 0) {
                    String sources = movieSources.get(0).getRealSource();
                    if (StringUtils.isBlank(sources)) {
                        playActivity.showError("播放失败，视频地址不存在");
                    } else {
                        sources = sources.replace("\\", "");
                        Gson gson = new Gson();
                        RealSource realSource = gson.fromJson(sources,RealSource.class);
                        List<RealSource.seg> seg = realSource.getSeg();
                        if (seg != null && seg.size() > 0) {
                            LogUtils.i("播放地址：",seg.get(0).getFurl());
                            playActivity.startPlay(seg.get(0).getFurl());
                        }
                    }
                } else {
                    playActivity.showError("播放失败，视频地址不存在");
                }
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
                playActivity.showError("播放失败，视频地址不存在");
            }
        });
    }
}

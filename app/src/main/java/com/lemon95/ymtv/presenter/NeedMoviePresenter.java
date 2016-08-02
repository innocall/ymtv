package com.lemon95.ymtv.presenter;

import com.lemon95.ymtv.bean.FavoritesBean;
import com.lemon95.ymtv.bean.PersonalMovies;
import com.lemon95.ymtv.bean.UploadResult;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.view.activity.FavoritesActivity;
import com.lemon95.ymtv.view.activity.NeedMovieActivity;

import java.util.List;

/**
 * Created by WXT on 2016/8/2.
 */
public class NeedMoviePresenter {

    private NeedMovieActivity needMovieActivity;
    private IMovieBean iMovieBean;

    public NeedMoviePresenter(NeedMovieActivity needMovieActivity) {
        this.needMovieActivity = needMovieActivity;
        this.iMovieBean = new MovieDao();
    }

    /**
     * 获取收藏数据
     */
    public void getFavorites(String currentPage) {
        String userId = PreferenceUtils.getString(needMovieActivity, AppConstant.USERID, "");
        iMovieBean.getPersonalMovies(userId,"1", currentPage, AppConstant.PAGESIZE, new MovieDao.OnPersonalMoviesListener() {
            @Override
            public void onSuccess(PersonalMovies personalMovies) {
                List<PersonalMovies.Data> listData = personalMovies.getData();
                if (listData == null || listData.size() == 0) {
                    needMovieActivity.showError("您当前没有求片影视");
                } else {
                    needMovieActivity.showFavoriteData(listData);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                needMovieActivity.showError("求片记录获取失败，请稍后再试");
            }
        });
    }

}

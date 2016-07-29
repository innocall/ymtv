package com.lemon95.ymtv.presenter;

import com.lemon95.ymtv.bean.FavoritesBean;
import com.lemon95.ymtv.bean.UploadResult;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.view.activity.FavoritesActivity;

import java.util.List;

/**
 * Created by WXT on 2016/7/27.
 */
public class FavoritesPresenter {

    private FavoritesActivity favoritesActivity;
    private IMovieBean iMovieBean;

    public FavoritesPresenter(FavoritesActivity favoritesActivity) {
        this.favoritesActivity = favoritesActivity;
        this.iMovieBean = new MovieDao();
    }

    /**
     * 获取收藏数据
     */
    public void getFavorites(String currentPage) {
        String mac = AppSystemUtils.getDeviceId();
        String userId = PreferenceUtils.getString(favoritesActivity, AppConstant.USERID,"");
        iMovieBean.getFavorites(mac, userId,currentPage,AppConstant.PAGESIZE, new MovieDao.OnFavoritesBeanListener() {
            @Override
            public void onSuccess(FavoritesBean favoritesBean) {
                List<FavoritesBean.Data> listData = favoritesBean.getData();
                if (listData == null || listData.size() == 0) {
                    favoritesActivity.showError("您当前没有收藏影视");
                } else {
                    favoritesActivity.showFavoriteData(listData);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                favoritesActivity.showError("收藏记录获取失败，请稍后再试");
            }
        });
    }

    public void deleteVideo(final List<FavoritesBean.Data> data , final FavoritesBean.Data video) {
        String arr[] = new String[]{video.getId()};
        iMovieBean.deleteFavorite(arr, new MovieDao.OnUpdateListener(){

            @Override
            public void onSuccess(UploadResult uploadResult) {
                String d = uploadResult.getData();
                if ("true".equals(d)) {
                    getFavorites("1");
                    favoritesActivity.page = 1;
                    favoritesActivity.videoList.clear();
                    favoritesActivity.showToastLong("删除成功");
                    favoritesActivity.setIsDelete(true);
                } else {
                    favoritesActivity.showToastLong("删除失败");
                }
            }

            @Override
            public void onFailure(Throwable e) {
                favoritesActivity.showToastLong("删除失败，请稍后再试");
            }
        });
    }
}

package com.lemon95.ymtv.presenter;

import android.widget.ProgressBar;

import com.lemon95.ymtv.bean.FavoritesBean;
import com.lemon95.ymtv.bean.UploadResult;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.view.activity.HistoryActivity;

import java.util.List;

/**
 * Created by WXT on 2016/7/27.
 */
public class HistoryPresenter {

    private HistoryActivity historyActivity;
    private IMovieBean iMovieBean;

    public HistoryPresenter(HistoryActivity historyActivity) {
        this.historyActivity = historyActivity;
        this.iMovieBean = new MovieDao();
    }

    /**
     * 获取收藏数据
     */
    public void getFavorites() {
        String mac = AppSystemUtils.getDeviceId();
        String userId = PreferenceUtils.getString(historyActivity, AppConstant.USERID,"");
        iMovieBean.getFavorites(mac, userId, new MovieDao.OnFavoritesBeanListener() {
            @Override
            public void onSuccess(FavoritesBean favoritesBean) {
                List<FavoritesBean.Data> listData = favoritesBean.getData();
                if (listData == null || listData.size() == 0) {
                    historyActivity.showError("您当前没有收藏影视");
                } else {
                    historyActivity.showFavoriteData(listData);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                historyActivity.showError("收藏记录获取失败，请稍后再试");
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
                    getFavorites();
//                    data.remove(video);
//                    historyActivity.showFavoriteData(data);
                    historyActivity.showToastLong("删除成功");
                    historyActivity.setIsDelete(true);
                } else {
                    historyActivity.showToastLong("删除失败");
                }
            }

            @Override
            public void onFailure(Throwable e) {
                historyActivity.showToastLong("删除失败，请稍后再试");
            }
        });
    }
}

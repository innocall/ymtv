package com.lemon95.ymtv.presenter;

import com.lemon95.ymtv.bean.FavoritesBean;
import com.lemon95.ymtv.bean.UploadResult;
import com.lemon95.ymtv.bean.WatchHistories;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.utils.AppSystemUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.view.activity.FavoritesActivity;
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
     * 获取播放记录
     */
    public void getFavorites(String mac,String userId,int page) {
        iMovieBean.getWatchHistories(page + "", AppConstant.PAGESIZE, mac, userId, new MovieDao.OnWatchHistoriesListener() {
            @Override
            public void onSuccess(WatchHistories watchHistories) {
                List<WatchHistories.Data> dataList = watchHistories.getData();
                if (dataList == null || dataList.size() == 0) {
                    historyActivity.showError("您当前没有播放记录");
                } else {
                    historyActivity.showFavoriteData(dataList);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                historyActivity.showError("播放记录获取失败，请稍后再试");
            }
        });
    }

    public void deleteVideo(String id) {
        String arr[] = new String[]{id};
        iMovieBean.deletePersonalHistories(arr, new MovieDao.OnUpdateListener() {

            @Override
            public void onSuccess(UploadResult uploadResult) {
                String d = uploadResult.getData();
                if ("1".equals(d)) {
                    getFavorites(historyActivity.mac,historyActivity.userId,1);
                    historyActivity.page = 1;
                    historyActivity.videoList.clear();
//                    data.remove(video);
//                    favoritesActivity.showFavoriteData(data);
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

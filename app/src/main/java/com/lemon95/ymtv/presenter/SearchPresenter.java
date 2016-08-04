package com.lemon95.ymtv.presenter;

import com.lemon95.ymtv.bean.FavoritesBean;
import com.lemon95.ymtv.bean.FirstLettersSearch;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.view.activity.SearchActivity;

import java.util.List;

/**
 * Created by WXT on 2016/8/4.
 */
public class SearchPresenter {

    private SearchActivity searchActivity;
    private IMovieBean iMovieBean;

    public SearchPresenter(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
        iMovieBean = new MovieDao();
    }


    public void searchData(String ss,int page) {
        iMovieBean.getFirstLettersSearch(ss,page + "", AppConstant.PAGESIZE,new MovieDao.OnFirstLettersSearchListener(){

            @Override
            public void onSuccess(FirstLettersSearch firstLettersSearch) {
                List<FirstLettersSearch.Data> listData = firstLettersSearch.getData();
                if (listData == null || listData.size() == 0) {
                    searchActivity.showError("没有找到您要搜索的影片");
                } else {
                    searchActivity.showFavoriteData(listData);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                searchActivity.showError("影片搜索失败，请稍后再试");
            }
        });
    }
}

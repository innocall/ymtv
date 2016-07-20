package com.lemon95.ymtv.presenter;

import com.lemon95.ymtv.bean.Conditions;
import com.lemon95.ymtv.bean.QueryConditions;
import com.lemon95.ymtv.bean.VideoSearchList;
import com.lemon95.ymtv.bean.impl.IMovieBean;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.dao.MovieDao;
import com.lemon95.ymtv.view.activity.VideoListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXT on 2016/7/19.
 */
public class VideoListPresenter {

    private VideoListActivity videoListActivity;
    private IMovieBean iMovieBean;

    public VideoListPresenter(VideoListActivity videoListActivity) {
        this.videoListActivity = videoListActivity;
        iMovieBean = new MovieDao();
    }

    public void getCombQueryConditions(final String type) {
        iMovieBean.getCombQueryConditions(type, new MovieDao.OnConditionsListener() {
            @Override
            public void onSuccess(Conditions conditions) {
                Conditions.Data data = conditions.getData();
                ArrayList<QueryConditions> conditionsArrayList = new ArrayList<QueryConditions>();
                if (data != null) {
                    List<Conditions.Data.Groups> groupsList = data.getGroups();
                    for (int i=0; i<groupsList.size(); i++) {
                        QueryConditions queryConditions = new QueryConditions();
                        queryConditions.setGroupId(groupsList.get(i).getGroupId());
                        queryConditions.setName(groupsList.get(i).getGroupName());
                        queryConditions.setType(type);
                        conditionsArrayList.add(queryConditions);
                    }
                    List<Conditions.Data.Genres> genresList =  data.getGenres();
                    for (int i=0; i<genresList.size(); i++) {
                        QueryConditions queryConditions = new QueryConditions();
                        queryConditions.setGenreId(genresList.get(i).getId());
                        queryConditions.setName(genresList.get(i).getTypeName());
                        queryConditions.setType(type);
                        conditionsArrayList.add(queryConditions);
                    }
                    List<Conditions.Data.Areas> areasList = data.getAreas();
                    for (int i=0; i<areasList.size(); i++) {
                        QueryConditions queryConditions = new QueryConditions();
                        queryConditions.setAreaId(areasList.get(i).getId());
                        queryConditions.setName(areasList.get(i).getAreaName());
                        queryConditions.setType(type);
                        conditionsArrayList.add(queryConditions);
                    }
                    videoListActivity.showListView(conditionsArrayList);
                    getCombSearch(conditionsArrayList.get(0).getAreaId(),conditionsArrayList.get(0).getGenreId(),conditionsArrayList.get(0).getGroupId(),conditionsArrayList.get(0).getChargeMethod(),conditionsArrayList.get(0).getVipLevel(),conditionsArrayList.get(0).getYear(),conditionsArrayList.get(0).getType(),"1", AppConstant.PAGESIZE);
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    public void getCombSearch(String areaId, String genreId, String groupId, String chargeMethod, String vipLevel, String year, String type, String currentPage, String pageSize) {
        iMovieBean.getCombSearch(areaId,genreId,groupId,chargeMethod,vipLevel,year,type,currentPage,pageSize,new MovieDao.OnVideoSearchListListener(){

            @Override
            public void onSuccess(VideoSearchList videoSearchList) {
                VideoSearchList.Data data = videoSearchList.getData();
                List<VideoSearchList.Data.VideoBriefs> videoBriefsList =  data.getVideoBriefs();
                videoListActivity.showGridView(data);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

}

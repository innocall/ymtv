package com.lemon95.ymtv.db;

import android.content.Context;

import com.lemon95.ymtv.bean.Video;
import com.lemon95.ymtv.bean.VideoType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXT on 2016/7/14.
 */
public class DataBaseDao {

    //在构造函数里初始化helper
    public DataBaseDao(Context context){
        SqliteDBHandler.helper =new SqliteHelper(context);
    }

    /**
     * 获取所有的每日推荐
     * @return
     */
    public List<Video> getAllVideoList() {
        return SqliteDBHandler.getAllVideo();
    }

    public void addOrUpdateVideo(Video video) {
        SqliteDBHandler.addOrUpdateVideo(video);
    }

    public Video findVideoByOrderId(String orderNum) {
        return SqliteDBHandler.findVideoByOrderId(orderNum);
    }

    public void updateVideoByOrderId(Video v) {
        SqliteDBHandler.updateVideoByOrderId(v);
    }

    public void updateVideoImageByOrderId(String downPath,String orderNum) {
        SqliteDBHandler.updateVideoImageByOrderId(downPath, orderNum);
    }

    public void deleteVideo() {
        SqliteDBHandler.deleteVideo();
    }

    /**
     * 保存影视分类数据
     * @param d
     */
    public void addOrUpdateVideoType(VideoType.Data d) {
        SqliteDBHandler.addOrUpdateVideoType(d);
    }

    public List<VideoType.Data> getAllVideoTypeList() {
        return SqliteDBHandler.getAllVideoTypeList();
    }

    public void deleteVideoType() {
        SqliteDBHandler.deleteVideoType();
    }
}

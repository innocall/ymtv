package com.lemon95.ymtv.db;

import android.content.Context;

import com.lemon95.ymtv.bean.Video;

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
}

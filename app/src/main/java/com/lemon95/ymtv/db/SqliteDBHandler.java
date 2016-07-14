package com.lemon95.ymtv.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lemon95.ymtv.bean.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXT on 2016/7/14.
 */
public class SqliteDBHandler {

    public static SqliteHelper helper;

    /**
     * 保存影视数据
     * @param video
     */
    public static void addVideo(Video video) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()) {
            String videoTypeId = video.getVideoTypeId();
            String videoId = video.getVideoId();
            String createTime = video.getCreateTime();
            String downImg = video.getDownImg();
            String orderNum = video.getOrderNum();
            String picturePath = video.getPicturePath();
            String tag = video.getTag();
            String title = video.getTitle();
            ContentValues values = new ContentValues();
            values.put("videoTypeId", videoTypeId);
            values.put("createTime", createTime);
            values.put("videoId", videoId);
            values.put("downImg", downImg);
            values.put("orderNum", orderNum);
            values.put("picturePath", picturePath);
            values.put("tag", tag);
            values.put("title", title);
            db.insert("tb_video", null, values);
            db.close();
        }
    }

    /**
     * 根据影视类型删除影视
     *
     * @param videoId
     */
    public static void deleteVideo(String videoId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("tb_video", "videoId = ?", new String[]{videoId});
        db.close();
    }

    /**
     * 获取每日推荐
     * @return
     */
    public static List<Video> getAllVideo() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Video> instances = new ArrayList<Video>();
        Video instance = null;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("Select * from tb_video", null);
            instances = new ArrayList<Video>();
            while (cursor.moveToNext()) {
                instance = new Video();
                instance.setVideoTypeId(cursor.getString(cursor.getColumnIndex("videoTypeId")));
                instance.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
                instance.setVideoId(cursor.getString(cursor.getColumnIndex("videoId")));
                instance.setDownImg(cursor.getString(cursor.getColumnIndex("downImg")));
                instance.setOrderNum(cursor.getString(cursor.getColumnIndex("orderNum")));
                instance.setPicturePath(cursor.getString(cursor.getColumnIndex("picturePath")));
                instance.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                instance.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                instances.add(instance);
            }
            cursor.close();
            db.close();
        }
        return instances;
    }
}

package com.lemon95.ymtv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lemon95.ymtv.utils.LogUtils;

/**
 * Created by WXT on 2016/3/16.
 */
public class SqliteHelper extends SQLiteOpenHelper {

    private static final String TAG = "SqliteHelper";
    public static final String TB_NAME= "db_ymtv";
    public static final int TB_VERSION= 1;


    /**
     * 数据库的构造方法 用来定义数据库的名称 数据库查询的结果集 数据库的版本
     *
     * @param context
     */
    public SqliteHelper(Context context) {
        super(context, TB_NAME, null, TB_VERSION);
    }


    /**
     * 数据库第一次被创建的时候 调用的方法.
     *
     * @param db 被创建的数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //保存影片数据
        db.execSQL("CREATE TABLE IF NOT EXISTS tb_video "
                + "(id integer primary key autoincrement,"
                + "videoTypeId varchar(64),"+ "videoId varchar(64),"+ "title varchar(225)," + "picturePath varchar(225),"+ "orderNum varchar(10),"
                + "tag varchar(10)," + "createTime varchar(20)," + "downImg varchar(225),"+ "b1 varchar(225),"+ "b2 varchar(225),"+ "b3 varchar(225),"
                + "b4 varchar(225))");
        db.execSQL("CREATE TABLE IF NOT EXISTS tb_videoVideo "
                + "(id integer primary key autoincrement,"
                + "videoTypeId varchar(64),"+ "title varchar(225)," + "picturePath varchar(225),"
                + "tag varchar(10)," + "downImg varchar(225),"+ "b1 varchar(225),"+ "b2 varchar(225),"+ "b3 varchar(225),"
                + "b4 varchar(225))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "数据库的版本变化了...oldVersion=" + oldVersion);
    }
}

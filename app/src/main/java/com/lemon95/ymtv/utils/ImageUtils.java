package com.lemon95.ymtv.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by WXT on 2016/7/14.
 */
public class ImageUtils {

    /**
     * 保存图片到本地
     * @param bitmap
     * @return
     */
    public static void saveImage(Bitmap bitmap,String picName) throws Exception{
        String SDCarePath = Environment.getExternalStorageDirectory()
                .toString();
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            LogUtils.v("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }
        FileOutputStream b = null;
        File file = new File(SDCarePath + "/myImage/ymtv/");
        if (!file.exists()) {
            boolean isP = file.mkdirs();
        }
        String fileName = SDCarePath + "/myImage/ymtv/" + picName + ".png";
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * 获取大图地址
     * @param path
     * @return
     */
    public static String getBigImg(String path) {
        /*if (StringUtils.isNotBlank(path)) {
            String l = path.substring(0,path.lastIndexOf("/") + 1);
            String name = path.substring(path.lastIndexOf("/") + 2,path.length());
            return l + name;
        } else {
            return path;
        }*/
        return path;
    }

}

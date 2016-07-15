package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by WXT on 2016/7/15.
 * 影视类型
 */
public class VideoType {
    private String ReturnCode;
    private String ReturnMsg;
    private List<Data> Data;

    public String getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(String returnCode) {
        ReturnCode = returnCode;
    }

    public String getReturnMsg() {
        return ReturnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        ReturnMsg = returnMsg;
    }

    public List<VideoType.Data> getData() {
        return Data;
    }

    public void setData(List<VideoType.Data> data) {
        Data = data;
    }

    public static class Data {
        private String VideoTypeId;
        private String Title;
        private String PicturePath;
        private String downImg;

        public String getVideoTypeId() {
            return VideoTypeId;
        }

        public void setVideoTypeId(String videoTypeId) {
            VideoTypeId = videoTypeId;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(String picturePath) {
            PicturePath = picturePath;
        }

        public String getDownImg() {
            return downImg;
        }

        public void setDownImg(String downImg) {
            this.downImg = downImg;
        }
    }
}

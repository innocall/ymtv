package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by wuxiaotie on 16/7/26.
 * 获取收藏夹
 */
public class FavoritesBean {
    private String ReturnCode;
    private String ReturnMsg;
    private List<Data> Data;

    public static class Data {
        private String Id;
        private String VideoId;
        private String VideoName;
        private String Title;
        private String VideoTypeId;
        private String PicturePath;
        private String AddTime;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getVideoId() {
            return VideoId;
        }

        public void setVideoId(String videoId) {
            VideoId = videoId;
        }

        public String getVideoName() {
            return VideoName;
        }

        public void setVideoName(String videoName) {
            VideoName = videoName;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getVideoTypeId() {
            return VideoTypeId;
        }

        public void setVideoTypeId(String videoTypeId) {
            VideoTypeId = videoTypeId;
        }

        public String getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(String picturePath) {
            PicturePath = picturePath;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String addTime) {
            AddTime = addTime;
        }
    }

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

    public List<FavoritesBean.Data> getData() {
        return Data;
    }

    public void setData(List<FavoritesBean.Data> data) {
        Data = data;
    }
}

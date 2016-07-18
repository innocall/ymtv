package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by WXT on 2016/7/18.
 * 相关影视
 */
public class GenresMovie {
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

    public List<GenresMovie.Data> getData() {
        return Data;
    }

    public void setData(List<GenresMovie.Data> data) {
        Data = data;
    }

    public static class Data{
        private String VideoId;
        private String VideoName;
        private String Director;
        private String Starring;
        private String VideoTypeId;
        private String PicturePath;
        private String Title;
        private String VIPLevel;
        private String IsNew;
        private String AddTime;
        private String Hot;

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

        public String getDirector() {
            return Director;
        }

        public void setDirector(String director) {
            Director = director;
        }

        public String getStarring() {
            return Starring;
        }

        public void setStarring(String starring) {
            Starring = starring;
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

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getVIPLevel() {
            return VIPLevel;
        }

        public void setVIPLevel(String VIPLevel) {
            this.VIPLevel = VIPLevel;
        }

        public String getIsNew() {
            return IsNew;
        }

        public void setIsNew(String isNew) {
            IsNew = isNew;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String addTime) {
            AddTime = addTime;
        }

        public String getHot() {
            return Hot;
        }

        public void setHot(String hot) {
            Hot = hot;
        }
    }
}

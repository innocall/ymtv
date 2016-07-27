package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by WXT on 2016/7/27.
 * 观看记录
 */
public class WatchHistories {
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

    public List<WatchHistories.Data> getData() {
        return Data;
    }

    public void setData(List<WatchHistories.Data> data) {
        Data = data;
    }

    public static class Data {
        private String Id;
        private String VideoId;
        private String VideoTypeId;
        private String PicturePath;
        private String Title;
        private String SerialEpisodeId;
        private String WatchTime;
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

        public String getSerialEpisodeId() {
            return SerialEpisodeId;
        }

        public void setSerialEpisodeId(String serialEpisodeId) {
            SerialEpisodeId = serialEpisodeId;
        }

        public String getWatchTime() {
            return WatchTime;
        }

        public void setWatchTime(String watchTime) {
            WatchTime = watchTime;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String addTime) {
            AddTime = addTime;
        }
    }
}

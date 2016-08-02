package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by WXT on 2016/8/2.
 */
public class PersonalMovies {
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

    public List<PersonalMovies.Data> getData() {
        return Data;
    }

    public void setData(List<PersonalMovies.Data> data) {
        Data = data;
    }

    public static class Data {
        private String Id;
        private String MovieName;
        private String Title;
        private String VideoTypeId;
        private String PicturePath;
        private String StartTime;
        private String EndTime;
        private String OrderTime;
        private String ReturnCode;
        private String Hot;
        private String Description;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getMovieName() {
            return MovieName;
        }

        public void setMovieName(String movieName) {
            MovieName = movieName;
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

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        public String getOrderTime() {
            return OrderTime;
        }

        public void setOrderTime(String orderTime) {
            OrderTime = orderTime;
        }

        public String getReturnCode() {
            return ReturnCode;
        }

        public void setReturnCode(String returnCode) {
            ReturnCode = returnCode;
        }

        public String getHot() {
            return Hot;
        }

        public void setHot(String hot) {
            Hot = hot;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }
    }
}

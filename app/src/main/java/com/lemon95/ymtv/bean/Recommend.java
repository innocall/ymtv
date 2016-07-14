package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by WXT on 2016/7/14.
 * 每日推荐接口获取数据,安装gson语法代码
 */
public class Recommend {
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

    public List<Recommend.Data> getData() {
        return Data;
    }

    public void setData(List<Recommend.Data> data) {
        Data = data;
    }

    public static class Data{
        private String VideoTypeId;
        private String VideoId;
        private String Title;
        private String PicturePath;
        private String OrderNum;

        public String getVideoTypeId() {
            return VideoTypeId;
        }

        public void setVideoTypeId(String videoTypeId) {
            VideoTypeId = videoTypeId;
        }

        public String getVideoId() {
            return VideoId;
        }

        public void setVideoId(String videoId) {
            VideoId = videoId;
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

        public String getOrderNum() {
            return OrderNum;
        }

        public void setOrderNum(String orderNum) {
            OrderNum = orderNum;
        }
    }


}

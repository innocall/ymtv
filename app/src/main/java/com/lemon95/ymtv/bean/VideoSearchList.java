package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by WXT on 2016/7/19.
 * 影视列表
 */
public class VideoSearchList {
    private String ReturnCode;
    private String ReturnMsg;
    private Data Data;

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

    public VideoSearchList.Data getData() {
        return Data;
    }

    public void setData(VideoSearchList.Data data) {
        Data = data;
    }

    public static class Data {
        private String TotalCount;
        private String TotalPageCount;
        private List<VideoBriefs> VideoBriefs;

        public String getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(String totalCount) {
            TotalCount = totalCount;
        }

        public String getTotalPageCount() {
            return TotalPageCount;
        }

        public void setTotalPageCount(String totalPageCount) {
            TotalPageCount = totalPageCount;
        }

        public List<VideoSearchList.Data.VideoBriefs> getVideoBriefs() {
            return VideoBriefs;
        }

        public void setVideoBriefs(List<VideoSearchList.Data.VideoBriefs> videoBriefs) {
            VideoBriefs = videoBriefs;
        }

        public static class VideoBriefs {
            private String VideoId;
            private String VideoName;
            private String Director;
            private String Starring;
            private String VideoTypeId;
            private String PicturePath;
            private String Title;
            private String VIPLevel;
            private String IsNew;
            private String StrVideoSources;
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

            public String getStrVideoSources() {
                return StrVideoSources;
            }

            public void setStrVideoSources(String strVideoSources) {
                StrVideoSources = strVideoSources;
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
}

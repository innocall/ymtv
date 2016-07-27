package com.lemon95.ymtv.bean;

/**
 * Created by WXT on 2016/7/27.
 * 添加观看记录
 */
public class VideoWatchHistory {

    private String UserId;
    private String MAC;
    private String VideoTypeId;
    private String VideoId;
    private String SerialEpisodeId;
    private String WatchTime;
    private boolean isPersonal;
    private String UserIP;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

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

    public String getUserIP() {
        return UserIP;
    }

    public void setUserIP(String userIP) {
        UserIP = userIP;
    }

    public boolean isPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }
}

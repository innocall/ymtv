package com.lemon95.ymtv.bean;

/**
 * Created by wuxiaotie on 16/7/26.
 */
public class Favorite {
    private String MAC;
    private String UserId;
    private String VideoTypeId;
    private String VideoId;

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
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
}

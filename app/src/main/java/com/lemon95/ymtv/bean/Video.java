package com.lemon95.ymtv.bean;

/**
 * Created by WXT on 2016/7/14.
 */
public class Video {

    private String videoTypeId;
    private String videoId;
    private String title;
    private String picturePath;
    private String orderNum;  //位置
    private String tag;  //标签
    private String createTime; //创建时间
    private String downImg;  //下载后地址

    public String getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(String videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDownImg() {
        return downImg;
    }

    public void setDownImg(String downImg) {
        this.downImg = downImg;
    }

}

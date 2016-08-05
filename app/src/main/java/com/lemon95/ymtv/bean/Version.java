package com.lemon95.ymtv.bean;

/**
 * Created by WXT on 2016/7/11.
 */
public class Version {

    private String versionId;  //版本号
    private String isUpdate; //是否强制更新
    private String msg;  //更新提示
    private String url;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.lemon95.ymtv.bean;

/**
 * Created by WXT on 2016/7/11.
 */
public class Version {

    private String versionId;  //版本号
    private boolean isUpdate; //是否强制更新
    private String msg;  //更新提示

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

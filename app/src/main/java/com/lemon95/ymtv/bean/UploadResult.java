package com.lemon95.ymtv.bean;

/**
 * Created by WXT on 2016/7/26.
 */
public class UploadResult {
    private String ReturnCode;
    private String ReturnMsg;
    private boolean Data;

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

    public boolean isData() {
        return Data;
    }

    public void setData(boolean data) {
        Data = data;
    }
}

package com.lemon95.ymtv.bean;

/**
 * Created by WXT on 2016/8/2.
 */
public class DeviceLogin {
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

    public DeviceLogin.Data getData() {
        return Data;
    }

    public void setData(DeviceLogin.Data data) {
        Data = data;
    }

    public static class Data {
        private String Id;
        private String NickName;
        private String Mobile;
        private String HeadImgUrl;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        public String getHeadImgUrl() {
            return HeadImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            HeadImgUrl = headImgUrl;
        }
    }
}

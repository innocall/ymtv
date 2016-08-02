package com.lemon95.ymtv.bean;

/**
 * Created by WXT on 2016/8/2.
 * 微信扫码支付
 */
public class ForWechat {
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

    public ForWechat.Data getData() {
        return Data;
    }

    public void setData(ForWechat.Data data) {
        Data = data;
    }

    public static class Data {
        private String Subject;
        private String TotalFee;
        private String NotifyUrl;
        private String OutTradeNo;

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public String getTotalFee() {
            return TotalFee;
        }

        public void setTotalFee(String totalFee) {
            TotalFee = totalFee;
        }

        public String getNotifyUrl() {
            return NotifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            NotifyUrl = notifyUrl;
        }

        public String getOutTradeNo() {
            return OutTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            OutTradeNo = outTradeNo;
        }
    }
}

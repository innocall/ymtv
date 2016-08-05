package com.lemon95.ymtv.utils;

import com.lemon95.ymtv.bean.WxPayDto;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 微信支付工具类
 * @author Administrator
 *
 */
public class WeixinUtil {
	
	// 统一下单接口
	//public static final String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	

	/**
	 * 生成微信扫码订单签名
	 * @param wxPay
	 * @return
	 */
	public static String getmSign(WxPayDto wxPay) {
		SortedMap<String, String> packageParams = new TreeMap();
		packageParams.put("appid", wxPay.getAppid());
		packageParams.put("mch_id", wxPay.getMch_id());
		packageParams.put("device_info", wxPay.getDevice_info());
		packageParams.put("nonce_str", wxPay.getNonce_str());
		packageParams.put("body", wxPay.getBody());
//		packageParams.put("attach", "");
		packageParams.put("out_trade_no", wxPay.getOrderId());
		packageParams.put("total_fee", wxPay.getTotalFee());
		packageParams.put("spbill_create_ip", wxPay.getSpbillCreateIp());
		packageParams.put("notify_url", wxPay.getNotifyUrl());
		packageParams.put("trade_type", "NATIVE");
//		packageParams.put("product_id", wxPay.getProduct_id());
		RequestHandler reqHandler = new RequestHandler();
		reqHandler.init(wxPay.getAppid(), null, wxPay.getPartnerkey());
		String sign = reqHandler.createSign(packageParams);
		return sign;
	}
	
	
	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}

	/**
	 *获取微信扫码支付二维码连接
	 * 
	 */
	public static String getCodeUrl(WxPayDto wxPay) {
		String xml = "<xml><appid>" + wxPay.getAppid() + "</appid>"
				+ "<mch_id>" + wxPay.getMch_id() + "</mch_id>" + "<device_info><![CDATA[" + wxPay.getDevice_info() + "]]></device_info><nonce_str>"
				+ wxPay.getNonce_str() + "</nonce_str>" + "<sign>"
				+ wxPay.getSign() + "</sign>" + "<body><![CDATA["
				+ wxPay.getBody() + "]]></body>" + "<out_trade_no>"
				+ wxPay.getOrderId() + "</out_trade_no>" + "<total_fee>"
				+ wxPay.getTotalFee() + "</total_fee>" + "<spbill_create_ip>"
				+ wxPay.getSpbillCreateIp() + "</spbill_create_ip>"
				+ "<notify_url>" + wxPay.getNotifyUrl() + "</notify_url>"
				+ "<trade_type>NATIVE</trade_type>"
				+  "</xml>";
		System.out.println(xml);
		//String	code_url = new GetWxOrderno().getCodeUrl(unifiedorder_url, xml);
		//System.out.println("获取微信扫码支付二维码连接：" + code_url);
		return xml;
	}

	/**
	 * 元转换成分
	 * @param amount
	 * @return
	 */
	public static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// 金额转化为分为单位
		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
	}

}

package com.lemon95.ymtv.utils;

import android.util.Xml;

import com.lemon95.ymtv.bean.Unifiedorder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

/**
 * Created by WXT on 2016/8/3.
 */
public class XmlUtils {

    /**
     * <xml><return_code><![CDATA[SUCCESS]]></return_code>
     <return_msg><![CDATA[OK]]></return_msg>
     <appid><![CDATA[wx427eb35b163fb705]]></appid>
     <mch_id><![CDATA[1264108101]]></mch_id>
     <nonce_str><![CDATA[qX4e9TmWohr9DTJY]]></nonce_str>
     <sign><![CDATA[A72927E3FC734F8F72B612BA72A7FEA6]]></sign>
     <result_code><![CDATA[SUCCESS]]></result_code>
     <prepay_id><![CDATA[wx20160804085840a9227e85a50392528560]]></prepay_id>
     <trade_type><![CDATA[NATIVE]]></trade_type>
     <code_url><![CDATA[weixin://wxpay/bizpayurl?pr=w2iazEs]]></code_url>
     </xml>
     * @param xmlStr
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static Unifiedorder fiedorder(String xmlStr) {
        Unifiedorder unifiedorder = new Unifiedorder();
        if (StringUtils.isNotBlank(xmlStr)) {
            String return_code = xmlStr.substring(xmlStr.indexOf("<return_code><![CDATA[") + 22,xmlStr.indexOf("]]></return_code>"));
            if ("SUCCESS".equals(return_code)) {
                String code_url = xmlStr.substring(xmlStr.indexOf("<code_url><![CDATA[") + 19,xmlStr.indexOf("]]></code_url>"));
                unifiedorder.setCode_url(code_url);
            }
            unifiedorder.setReturn_code(return_code);
        }
        return unifiedorder;
    }
}

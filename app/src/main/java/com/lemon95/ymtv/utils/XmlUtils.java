package com.lemon95.ymtv.utils;

import android.util.Xml;

import com.lemon95.ymtv.bean.Unifiedorder;
import com.lemon95.ymtv.bean.Version;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

    /**
     * ﻿<?xml version="1.0" encoding="utf-8" ?>
     <Broadcast>
     <Version>1.0.3</Version>
     <Url>http://resource.lemon95.com/App/Android/Broadcast/wyzqTVPlayer1.0.1.apk</Url>
     <IsForced>False</IsForced>
     <Description>1.0.3</Description>
     </Broadcast>
     * @param ver
     * @return
     */
    public static Version formentVersion(String ver) throws Exception {
        Version version = new Version();
        ver = String.format(ver);
        InputStream inputStream = new ByteArrayInputStream(ver.getBytes("UTF-8"));
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream,"utf-8");
        int eventType = xmlPullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xmlPullParser.getName().equals("Version")) {
                        version.setVersionId(xmlPullParser.nextText());
                    } else if (xmlPullParser.getName().equals("Url")) {
                        version.setUrl(xmlPullParser.nextText());
                    } else if (xmlPullParser.getName().equals("IsForced")) {
                        version.setIsUpdate(xmlPullParser.nextText());
                    } else if (xmlPullParser.getName().equals("Description")) {
                        version.setMsg(xmlPullParser.nextText());
                    }
                    break;
            }
            eventType = xmlPullParser.next();
        }
        return version;

    }
}

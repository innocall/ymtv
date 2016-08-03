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

    public static Unifiedorder fiedorder(String xmlStr) throws XmlPullParserException, IOException {
        Unifiedorder unifiedorder = new Unifiedorder();
        XmlPullParser xpp = Xml.newPullParser();
        // 设置输入流 并指明编码方式
        InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
        xpp.setInput(is, "UTF-8");
        // 产生第一个事件
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                // 判断当前事件是否为标签元素开始事件
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("return_code")) { // 判断开始标签元素是否是book
                        unifiedorder.setResult_code(xpp.getText());
                    } else if (xpp.getName().equals("return_msg")) {
                        unifiedorder.setReturn_msg(xpp.getText());
                    } else if (xpp.getName().equals("code_url")) { // 判断开始标签元素是否是book
                        unifiedorder.setCode_url(xpp.getText());
                    }
                    break;
            }
            // 进入下一个元素并触发相应事件
            eventType = xpp.next();
        }
        return unifiedorder;
    }
}

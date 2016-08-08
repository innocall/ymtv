/*
package com.lemon95.ymtv.service;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;

import com.google.gson.Gson;
import com.lemon95.ymtv.bean.DeviceLogin;
import com.lemon95.ymtv.common.AppConstant;
import com.lemon95.ymtv.utils.LogUtils;
import com.lemon95.ymtv.utils.PreferenceUtils;
import com.lemon95.ymtv.utils.StringUtils;
import com.lemon95.ymtv.utils.ToastUtils;
import com.lemon95.ymtv.view.activity.LoginActivity;
import com.lemon95.ymtv.view.activity.NeedMovieActivity;
import com.lemon95.ymtv.view.activity.UserActivity;
import com.umeng.common.message.Log;
import com.umeng.message.UTrack;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.client.BaseConstants;
import org.json.JSONObject;

import java.util.Map;

//完全自定义处理类
//参考文档的1.6.5
//http://dev.umeng.com/push/android/integration#1_6_5
public class MyPushIntentService extends UmengBaseIntentService {
	private static final String TAG = MyPushIntentService.class.getName();

	private Intent intents = new Intent("com.lemon.push.RECEIVER");
	private Intent intent2 = new Intent("com.lemon.login.RECEIVER");
	private Intent intent3 = new Intent("com.lemon.Main.RECEIVER");

	@Override
	protected void onMessage(Context context, Intent intent) {
		// 需要调用父类的函数，否则无法统计到消息送达
		super.onMessage(context, intent);
		try {
			//可以通过MESSAGE_BODY取得消息体 "extra":{"messageType":1}
			String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
			UMessage msg = new UMessage(new JSONObject(message));
			LogUtils.e(TAG, "message=" + message);    //消息体
			LogUtils.e(TAG, "custom=" + msg.custom);    //自定义消息的内容
			LogUtils.e(TAG, "title=" + msg.title);    //通知标题
			LogUtils.e(TAG, "text=" + msg.text);    //通知内容

			// 对完全自定义消息的处理方式，点击或者忽略
			boolean isClickOrDismissed = true;
			if(isClickOrDismissed) {
				//完全自定义消息的点击统计
				UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
			} else {
				//完全自定义消息的忽略统计
				UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
			}
			//JSONObject json = new JSONObject(msg.custom);
            //String topic = json.getString("topic");
			Map<String,String> map = msg.extra;
			if (map != null) {
				String messageType = map.get("messageType");
				String userId = PreferenceUtils.getString(context,AppConstant.USERID);
				LogUtils.e(TAG,"用户ID：" + userId);
				if ("1".equals(messageType) && StringUtils.isBlank(userId)) {
					//扫描登录
					intent2.putExtra("state", msg.custom);
					intent3.putExtra("state", msg.custom);
					sendBroadcast(intent2);
					sendBroadcast(intent3);
				} else if("2".equals(messageType) && StringUtils.isNotBlank(userId)){
					//支付成功{"builder_id":0,"custom":"{\"state\":true}"},"extra":{"messageType":2}}
					JSONObject json = new JSONObject(msg.custom);
					String state = json.getString("state");
					intents.putExtra("state", state);
					sendBroadcast(intents);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
}
*/

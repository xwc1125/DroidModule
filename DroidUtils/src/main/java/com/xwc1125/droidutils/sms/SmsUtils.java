package com.xwc1125.droidutils.sms;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import com.xwc1125.droidutils.StringUtils;

/**
 * 
 * <p>
 * Title: SmsUtils
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年3月16日下午2:15:17
 * 
 */
public class SmsUtils {

	/**
	 * 
	 * <p>
	 * Title: sendSms
	 * </p>
	 * <p>
	 * Description: 发送短信
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param sp
	 * @param content
	 * 
	 * @author zhangqy
	 * @date 2016年3月16日下午2:14:51
	 */
	public static void sendSms(String sp, String content, Boolean isSendText) {
		SmsManager smsManager = SmsManager.getDefault();
		/**
		 * destinationAddress:接收方的手机号码 <br>
		 * scAddress:发送方的手机号码 <br>
		 * text:信息内容<br>
		 * sentIntent:发送是否成功的回执，<br>
		 * DeliveryIntent:接收是否成功的回执。
		 */
		if (isSendText) {
			smsManager.sendTextMessage(sp, null, content, null, null);
		} else {
			smsManager.sendDataMessage(sp, null, (short) 0, content.getBytes(),
					null, null);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: sendSms
	 * </p>
	 * <p>
	 * Description: 指定端口发送短信
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param sp
	 * @param content
	 * @param port
	 * 
	 * @author zhangqy
	 * @date 2016年7月28日下午6:28:12
	 */
	public static void sendSms(String sp, String content, short port) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendDataMessage(sp, null, port, content.getBytes(), null,
				null);
	}

	/**
	 * 
	 * <p>
	 * Title: sendMessage
	 * </p>
	 * <p>
	 * Description: 调用系统界面，给指定的号码发送短信，并附带短信内容
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param phoneNumber
	 * @param message
	 * 
	 * @author zhangqy
	 * @date 2016年8月20日 下午2:24:06
	 */
	public static void sendMessage(Context context, String phoneNumber,
			String message) {
		Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
		sendIntent.setData(Uri.parse("smsto:" + phoneNumber));
		sendIntent.putExtra("sms_body", message);
		context.startActivity(sendIntent);
	}

	public static void sendMessage(Context context, String address,
			String content, String broadcastReceive) {
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> divideMessage = smsManager.divideMessage(content);
		Intent intent = null;
		PendingIntent sentIntent = null;
		if (StringUtils.isNotEmpty(broadcastReceive)) {
			intent = new Intent(broadcastReceive);
			sentIntent = PendingIntent.getBroadcast(context, 0, intent,
					PendingIntent.FLAG_ONE_SHOT);
		}
		for (String text : divideMessage) {
			smsManager.sendTextMessage(address, // 对方手机号
					null, // 短信中心号码
					text, // 内容
					sentIntent, // 当短信发送成功时回调, 回调方式: 延期意图(延期意图指向的是广播接收者)
					null); // 当对方收到短信时回调
		}
	}
}

package com.pangu.neusoft.baidu;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;

public class MyPushMessageReceiver extends FrontiaPushMessageReceiver
{
	/** TAG to Log */
	public static final String TAG = MyPushMessageReceiver.class
			.getSimpleName();

	/**
	 * 调用PushManager.startWork后，sdk将对push
	 * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。
	 */
	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId)
	{
		// TODO Auto-generated method stub
		String responseString = "onBind errorCode=" + errorCode + " appid="
				+ appid + " userId=" + userId + " channelId=" + channelId
				+ " requestId=" + requestId;
	}    

	/**
	 * 接收透传消息的函数。
	 */
	@Override
	public void onMessage(Context context, String message,
			String customContentString)
	{
		// TODO Auto-generated method stub
		String messageString = "透传消息 message=" + message
				+ " customContentString=" + customContentString;
		Log.d(TAG, messageString);
		// 自定义内容获取方式，mykey和myvalue对应透传消息推送时自定义内容中设置的键和值
		if (customContentString != null & customContentString != "")
		{
			JSONObject customJson = null;
			try
			{
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (customJson.isNull("mykey"))
				{
					myvalue = customJson.getString("mykey");
				}
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDelTags(Context arg0, int arg1, List<String> arg2,
			List<String> arg3, String arg4)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onListTags(Context arg0, int arg1, List<String> arg2,
			String arg3)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 接收通知点击的函数。注：推送通知被用户点击前，应用无法通过接口获取通知的内容。
	 */
	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString)
	{
		// TODO Auto-generated method stub
		String notifyString = "通知点击 title=" + title + " description="
				+ description + " customContent=" + customContentString;
		Log.d(TAG, notifyString);
		
		// 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
		if (customContentString != null & customContentString != "")
		{
			JSONObject customJson = null;
			try
			{
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (customJson.isNull("mykey"))
				{
					myvalue = customJson.getString("mykey");
				}
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * setTags() 的回调函数。
	 */
	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId)
	{
		// TODO Auto-generated method stub
		String responseString = "onSetTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
	}
	
	
	
	
	
	/**
	 * PushManager.stopWork() 的回调函数。
	 */
	@Override
	public void onUnbind(Context context, int errorCode, String requestId)
	{
		String responseString = "onUnbind errorCode=" + errorCode
				+ " requestId = " + requestId;
	}
}

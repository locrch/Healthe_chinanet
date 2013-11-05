package com.pangu.neusoft.tools;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import com.pangu.neusoft.healthe.Setting;

public class DialogShow {

	public static void showDialog(Activity activity,String res){
		final Dialog dialog = new AlertDialog.Builder(
				activity)
				.setCancelable(true)
				.setTitle("提示")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage(res).create();// 创建

		// 显示对话框
		try{
			dialog.show();
		}catch(Exception ex){
			
		}
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				if(dialog.isShowing())
					dialog.dismiss();
				t.cancel(); 
			}
		}, Setting.dialogtimeout);
		
	}
}

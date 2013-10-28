package com.pangu.neusoft.healthe;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class HealthActivityGroup extends ActivityGroup
{

	/**
	 * 一个静态的ActivityGroup变量，用于管理本Group中的Activity
	 */
	public static ActivityGroup group;

	private ArrayList<View> history;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.history = new ArrayList<View>(); 
		group = this;

		View view = getLocalActivityManager().startActivity(
				"TabActivity2",
				new Intent(HealthActivityGroup.this, TabActivity2.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView();

		replaceView(view);
	}

	public void replaceView(View v)
	{
		// 可在這插入換頁動畫
		history.add(v);
		setContentView(v);
	}

	 public void back() {
		 // 原本的範例是寫 > 0，但會發生錯誤         
		 if(history.size() > 1) {             
			 history.remove(history.size()-1);             
			 View v = history.get(history.size()-1);             
			 // 可在這插入換頁動畫             
			 setContentView(v);   
			 }else {
				 // back stack 沒有其他頁面可顯示，直接結束             
				 finish();             
				 } 
		 
	 }

	 @Override    
	 public boolean onKeyDown(int keyCode, KeyEvent event) {         
		 switch (keyCode) {             
		 case KeyEvent.KEYCODE_BACK:                 
			 back();                 
			 break;         
			 }      
		 return true;     
		 } 

}

package com.pangu.neusoft.healthe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;



public class Tab_Button_Cut extends Button
{
	public Tab_Button_Cut(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public Tab_Button_Cut(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public Tab_Button_Cut(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setBackgroundResource(int resid)
	{
		// TODO Auto-generated method stub
		this.setBackgroundResource(R.drawable.tab_btn);
		
	}
	
	
	
	/*public void setBackground(final Button b,final int def_img,final int press_img)
	{
		// TODO Auto-generated method stub
		
		super.setBackgroundResource(def_img);
		
		
		
		b.setOnTouchListener(new OnTouchListener()
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				  
				   case MotionEvent.ACTION_DOWN:
				   {
				    //按住事件发生后执行代码的区域
					   
					   b.setBackgroundResource(press_img);
				    break;
				   }
				   case MotionEvent.ACTION_MOVE:
				   {
				    //移动事件发生后执行代码的区域
					   b.setBackgroundResource(press_img);
					   
				    break;
				   }
				   case MotionEvent.ACTION_UP:
				   {
				    //松开事件发生后执行代码的区域
					   b.setBackgroundResource(def_img);
				    break;
				   }
				  
				   default:
				   
				    break;
				   }
				return false;
			}
		});
	}*/
	
}

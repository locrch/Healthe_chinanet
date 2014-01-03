package com.pangu.neusoft.healthe;

import com.pangu.neusoft.tools.DensityUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SetTextSizeActivity extends FatherActivity
{
	private SharedPreferences sp;
	private Editor editor;
	int width;
	int height;
	
	Button text_size_big,text_size_normal,text_size_small;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settextsize);
		
		text_size_big = (Button)findViewById(R.id.text_size_big);
		text_size_normal = (Button)findViewById(R.id.text_size_normal);
		text_size_small = (Button)findViewById(R.id.text_size_small);
		
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
		height = metric.heightPixels;		
		width=DensityUtil.px2dip(this, width);
		height=DensityUtil.px2dip(this, height);
		
		text_size_big.setTextSize(width/Setting.fontsizel);
		text_size_normal.setTextSize(width/Setting.fontsizex);
		text_size_small.setTextSize(width/Setting.fontsizes);
		
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		
		text_size_big.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				editor.putInt("fontsize", width/Setting.fontsizel);
				editor.commit();
				Toast.makeText(getApplicationContext(), "设置成功！"+width/Setting.fontsizel, Toast.LENGTH_SHORT).show();
			}
		});
		
		text_size_normal.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				editor.putInt("fontsize", width/Setting.fontsizex);
				editor.commit();
				Toast.makeText(getApplicationContext(), "设置成功！"+width/Setting.fontsizex, Toast.LENGTH_SHORT).show();
			}
		});
		
		text_size_small.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				editor.putInt("fontsize", width/Setting.fontsizes);
				editor.commit();
				Toast.makeText(getApplicationContext(), "设置成功！"+width/Setting.fontsizes, Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}

	

}

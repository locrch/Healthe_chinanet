package com.pangu.neusoft.healthe;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SetTextSizeActivity extends FatherActivity
{
	Button text_size_big,text_size_normal,text_size_small;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settextsize);
		
		text_size_big = (Button)findViewById(R.id.text_size_big);
		text_size_normal = (Button)findViewById(R.id.text_size_normal);
		text_size_small = (Button)findViewById(R.id.text_size_small);
		
		
		text_size_big.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Setting.fontsizex =15;
				Setting.fontsizes =20;
				Toast.makeText(getApplicationContext(), "设置成功！", Toast.LENGTH_SHORT).show();
			}
		});
		
		text_size_normal.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Setting.fontsizex =20;
				Setting.fontsizes =25;
				Toast.makeText(getApplicationContext(), "设置成功！", Toast.LENGTH_SHORT).show();
			}
		});
		
		text_size_small.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Setting.fontsizex =25;
				Setting.fontsizes =30;
				Toast.makeText(getApplicationContext(), "设置成功！", Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_text_size, menu);
		return true;
	}

}

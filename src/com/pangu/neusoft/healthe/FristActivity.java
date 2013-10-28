package com.pangu.neusoft.healthe;

import com.baidu.platform.comapi.map.r;
import com.pangu.neusoft.tools.SysApplication;
import com.pangu.neusoft.tools.update.UpdateOperation;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class FristActivity extends Activity {
	ImageButton zhineng, shuzi,phone;
	
	Button zhuce, denglu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		setContentView(R.layout.activity_frist);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_frist);
		
		phone = (ImageButton)findViewById(R.id.phone);
		
		zhineng = (ImageButton) findViewById(R.id.zhineng);
		
		shuzi = (ImageButton) findViewById(R.id.shuzi);

		zhuce = (Button) findViewById(R.id.zhuce);

		denglu = (Button) findViewById(R.id.denglu);

		zhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FristActivity.this,
						com.pangu.neusoft.user.RegisterActivity.class));
			}
		});
		
		denglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FristActivity.this,
						com.pangu.neusoft.user.LoginActivity.class));
			}
		});
		
		zhineng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				
				intent.putExtra("extra", "zhineng");
			    
			    intent.setClass(FristActivity.this,TabHostActivity.class);
				
				startActivity(intent);
			}
		});

		shuzi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Toast.makeText(getApplicationContext(), "正在建设中，敬请期待", Toast.LENGTH_LONG).show();
				
			  /*Intent intent = new Intent();
				 
				intent.putExtra("extra", "shuzi");
			    
			    intent.setClass(FristActivity.this,TabHostActivity.class);
				
			    startActivity(intent);*/
			}
		});
		
		phone.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				Intent tell = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"12320"));
				
				FristActivity.this.startActivity(tell);
			}
		});
		SysApplication.getInstance().addActivity(this);
		
		UpdateOperation update=new UpdateOperation(FristActivity.this);
    	update.checkUpdate();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		this.onDestroy();
		
		
		return super.onKeyDown(keyCode, event);
	}
	
}

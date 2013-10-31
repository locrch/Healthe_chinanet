package com.pangu.neusoft.healthe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TabActivity2 extends Activity
{
	ImageButton tab2_booking,tab2_tellphone;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//点击后加载不同的layout
		/*Intent intent = getIntent();

		String Value = intent.getStringExtra("extra");

		if (Value.equals("zhineng"))
		{
			setContentView(R.layout.tab_zhineng);

		} else
		{
			setContentView(R.layout.tab_shuzi);
		}*/
		
		setContentView(R.layout.tab_zhineng);
		
		TextView huoqu = (TextView) findViewById(R.id.huoqu);

		huoqu.setText("欢迎你");

		tab2_booking = (ImageButton) findViewById(R.id.tab2_booking);
		tab2_tellphone = (ImageButton)findViewById(R.id.tab2_tellphone);
		
		
		
		tab2_booking.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				/*View view = HealthActivityGroup
						.group
						.getLocalActivityManager()
						.startActivity("Activity2", 
								new Intent(TabActivity2.this, BookingMainActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView(); */
				
				Intent intent = new Intent(TabActivity2.this,
						BookingMainActivity.class);
				
				startActivity(intent);
				
				}
		});
		
		tab2_tellphone.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				final String[] args = new String[]{"12320","114"};
				
				new AlertDialog.Builder(TabActivity2.this).setTitle("电话表").setItems(
						args, new DialogInterface.OnClickListener() {  
							 
		                    @Override 
		                    public void onClick(DialogInterface dialog, int which) {  
		                    	String phone=args[which];
								
								  Intent myIntentDial = new Intent(  
			                                Intent.ACTION_CALL,Uri.parse("tel:"+phone)  
			                        );  
			                          
			                        TabActivity2.this.startActivity(myIntentDial);  
			                      
		                    }  
		                }).setNegativeButton(
						"确定", null).show();
				Toast.makeText(TabActivity2.this, "nihao", Toast.LENGTH_SHORT).show();
				
				}
			
		});
		
	}
	
}
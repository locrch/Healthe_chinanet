package com.pangu.neusoft.healthe;


import com.pangu.neusoft.tools.update.UpdateOperation;
import com.pangu.neusoft.user.LoginActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	private ImageButton booking_btn;
	private Button book_btn;
	private Button userinfo_btn;
	private int width;
	private int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        booking_btn=(ImageButton)findViewById(R.id.booking_btn);
        book_btn=(Button)findViewById(R.id.book_btn);
        userinfo_btn=(Button)findViewById(R.id.userinfo_btn);
        
    	getScreenSize();
    	booking_btn.getLayoutParams().width=width/4;
    	booking_btn.getLayoutParams().height=height/4;
    	
    	
    	book_btn.setOnClickListener(booking_btn_click);
    	booking_btn.setOnClickListener(booking_btn_click);
    	userinfo_btn.setOnClickListener(userinfo_btn_click);
    	
    
    }

    OnClickListener booking_btn_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			 startActivity(new Intent (MainActivity.this, BookingMainActivity.class)); 
		}
    };
    
    OnClickListener userinfo_btn_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			startActivity(new Intent (MainActivity.this, LoginActivity.class));
		}
    };
    
   

    public void getScreenSize(){
    	DisplayMetrics metric = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metric);
	    width = metric.widthPixels; 
	    height = metric.heightPixels;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

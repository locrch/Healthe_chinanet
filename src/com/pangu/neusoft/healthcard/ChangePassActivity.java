package com.pangu.neusoft.healthcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;

public class ChangePassActivity extends FatherActivity{
	
	EditText userphone_text;
	EditText ver_pass_text;
	EditText new_pass_text;
	EditText confirm_pass_text;
	Button get_ver_btn;
	Button reset_pass_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pass);
		
		userphone_text=(EditText)findViewById(R.id.userphone);
		ver_pass_text=(EditText)findViewById(R.id.ver_pass);
		new_pass_text=(EditText)findViewById(R.id.new_pass);
		confirm_pass_text=(EditText)findViewById(R.id.confirm_pass);
		get_ver_btn=(Button)findViewById(R.id.get_ver);
		reset_pass_btn=(Button)findViewById(R.id.reset_pass);
		Intent intent = getIntent();
		String userphone=intent.getStringExtra("userphone");
		if(userphone!=null){
			userphone_text.setText(userphone);
		}
		
	}

}

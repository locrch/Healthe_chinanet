package com.pangu.neusoft.healthcard;

import com.pangu.neusoft.healthe.FatherActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Area;
import com.pangu.neusoft.core.models.AreaReq;
import com.pangu.neusoft.core.models.Member;
import com.pangu.neusoft.core.models.MemberReg;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.db.People;
import com.pangu.neusoft.healthe.BookingMainActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.tools.StringMethods;

import android.R.integer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends FatherActivity
{
	private SharedPreferences sp;
	private Editor editor;
	private WebService service;
	private ProgressDialog mProgressDialog;
	private EditText username;
	private EditText password;

	private EditText confirm_password;
	private Spinner sex;
	private EditText card_num;
	private EditText membername;

	private EditText ver_pass_text;
	private Button get_ver_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_layout);
		setactivitytitle("注册");
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		service = new WebService();
		mProgressDialog = new ProgressDialog(RegisterActivity.this);
		mProgressDialog.setMessage("正在加载数据...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCanceledOnTouchOutside(false);// 设置进度条是否可以按退回键取消
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		Button reg_btn = (Button) findViewById(R.id.reg_reg_btn);
		reg_btn.setOnClickListener(login);

		username = (EditText) findViewById(R.id.reg_username);
		password = (EditText) findViewById(R.id.reg_password);
		confirm_password = (EditText) findViewById(R.id.reg_password_con);
		sex = (Spinner) findViewById(R.id.reg_sex);
		card_num = (EditText) findViewById(R.id.card_num);
		membername = (EditText) findViewById(R.id.reg_name);

		ver_pass_text = (EditText) findViewById(R.id.ver_pass_text);
		get_ver_btn = (Button) findViewById(R.id.get_ver);
		get_ver_btn.setOnClickListener(getver);
		// Setting.bookingdata=null;//清除本次预约数据
		// username.setText(sp.getString("username", ""));
		// password.setText(sp.getString("password", ""));
	}

	OnClickListener getver = new OnClickListener()
	{
		@Override
		public void onClick(View arg0)
		{
			final String phone = username.getText().toString();
			if (phone != null || !phone.equals(""))
			{
				if (!StringMethods.isMobileNO(username.getText().toString())
						|| username.getText().length() != 11)
				{
					String msg = "用户名必须为11位手机号码";
					username.setText("");
					username.setHint(msg);
					username.setHintTextColor(Color.RED);
					Toast.makeText(RegisterActivity.this, msg,
							Toast.LENGTH_SHORT).show();
				} else
				{
					SendCaptcha sendAction = new SendCaptcha(phone,
							mProgressDialog, service, RegisterActivity.this,
							get_ver_btn);
					sendAction.sendData();
				}
			} else
			{
				Toast.makeText(RegisterActivity.this, "请先输入用户名(手机号)",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	public String checkData()
	{
		String msg = "";
		if (username.getText().toString().equals(""))
		{
			msg += "用户名不能为空\n";
			username.setHint(msg);
			username.setHintTextColor(Color.RED);
			return msg;
		} else if (password.getText().toString().equals(""))
		{
			msg += "密码不能为空\n";
			password.setHint(msg);
			password.setHintTextColor(Color.RED);
			return msg;
		} else if (!StringMethods.isMobileNO(username.getText().toString())
				|| username.getText().length() != 11)
		{
			msg += "用户名必须为11位手机号码";
			username.setText("");
			username.setHint(msg);
			username.setHintTextColor(Color.RED);
			return msg;
		} else if (!password.getText().toString()
				.equals(confirm_password.getText().toString()))
		{
			msg += "两次密码输入不同\n";
			confirm_password.setText("");
			password.setText("");
			confirm_password.setHint(msg);
			confirm_password.setHintTextColor(Color.RED);
			return msg;
		} else if (password.getText().toString().length() != 6)
		{
			msg += "请输入六位数密码\n";
			confirm_password.setText("");
			password.setText("");
			password.setHint(msg);
			password.setHintTextColor(Color.RED);
			return msg;
		} else
		/*
		 * if(card_num.getText().toString().equals("")||card_num.getText().length
		 * ()!=18){ msg+="身份证号码必须是18位\n"; card_num.setText("");
		 * card_num.setHint(msg); card_num.setHintTextColor(Color.RED); return
		 * msg; }
		 */
		if (membername.getText().toString().equals(""))
		{
			msg += "姓名不能为空\n";
			membername.setText("");
			membername.setHint(msg);
			membername.setHintTextColor(Color.RED);
			return msg;
		} else if (ver_pass_text.getText().toString().equals(""))
		{
			msg += "请输入验证码\n";
			ver_pass_text.setText("");
			ver_pass_text.setHint(msg);
			ver_pass_text.setHintTextColor(Color.RED);
			return msg;
		}

		return msg;
	}

	OnClickListener login = new OnClickListener()
	{// 注册
		@Override
		public void onClick(View v)
		{
			String msg = checkData();
			if (msg.equals(""))
			{
				new AsyncTask<Void, Void, Boolean>()
				{
					String msg = "注册失败";

					@SuppressWarnings("deprecation")
					@Override
					protected void onPreExecute()
					{
						super.onPreExecute();
						mProgressDialog.show();
					}

					@Override
					protected Boolean doInBackground(Void... params)
					{
						MemberReg member = new MemberReg();

						member.setUserName(username.getText().toString().trim());
						member.setPassword(password.getText().toString());

						member.setIDCardNo(card_num.getText().toString());
						member.setMemberName(membername.getText().toString());
						String gender = sex.getSelectedItem().toString();
						if (gender.equals("男"))
							member.setSex(1);
						else
							member.setSex(0);

						member.setCAPTCHA(ver_pass_text.getText().toString());

						member.setAucode(GET.Aucode);
						SoapObject obj = service.sendMemberData(member,
								"userRegister");

						if (obj != null)
						{
							String IsRegisterSuccess = obj.getProperty(
									"IsRegisterSuccess").toString();// 0000成功1111报错
							String resultCode = obj.getProperty("resultCode")
									.toString();// 0000成功1111报错
							msg = obj.getProperty("msg").toString();// 返回的信息

							if (IsRegisterSuccess.equals("true"))
							{
								if (!member.getUserName().equals(
										sp.getString("username", "")))
								{
									int totalcards = sp
											.getInt("total_cards", 0);
									for (int i = 0; i < totalcards; i++)
									{
										editor.remove("card" + i + "_"
												+ "owner");
										editor.remove("card" + i + "_"
												+ "cardnum");
										editor.remove("card" + i + "_"
												+ "cardtype");
										editor.remove("card" + i + "_"
												+ "idnumber");
										editor.remove("card" + i + "_"
												+ "idtype");
										editor.remove("card" + i + "_"
												+ "phonenumber");
									}
									editor.remove("defaultcardno");
									editor.remove("total_cards");
									editor.commit();
								}
								editor.putString("username",
										member.getUserName());
								editor.putString("password",
										member.getPassword());
								editor.putBoolean("loginsuccess", true);
								editor.putString("defaultcardno", "0");
								editor.commit();
								return true;
							} else
							{
								return false;
							}

						} else
						{
							msg = "注册失败";
							return false;
						}

					}

					@SuppressWarnings("deprecation")
					@Override
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);

						if (mProgressDialog.isShowing())
						{
							mProgressDialog.dismiss();
						}
						DialogShow.showDialog(RegisterActivity.this, msg);
						if (result)
						{
							DBManager mgr = new DBManager(RegisterActivity.this);
							People person = new People();
							person.setSex(sex.getSelectedItem().toString());
							person.setUsername(membername.getText().toString());
							person.setPhone(username.getText().toString());
							person.setAddress("");
							person.setLicence_num(card_num.getText().toString());
							person.setLicence_type("身份证");

							mgr.add(person);

							final Timer t = new Timer();
							t.schedule(new TimerTask()
							{
								public void run()
								{
									startActivity(new Intent(
											RegisterActivity.this,
											ListCardActivity.class));
									finish();
									t.cancel();
								}
							}, Setting.dialogtimeout + 1000);

						}
					}

					@SuppressWarnings("deprecation")
					@Override
					protected void onCancelled()
					{
						super.onCancelled();
						if (mProgressDialog.isShowing())
						{
							mProgressDialog.dismiss();
						}
					}
				}.execute();
			}
			// else{
			// DialogShow.showDialog(RegisterActivity.this, msg);
			// }
		}
	};

}

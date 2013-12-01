package com.pangu.neusoft.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.HandleBooking;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.healthcard.ShowHistoryActivity;
import com.pangu.neusoft.healthe.DoctorDetailActivity;
import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.HospitalDetailActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.DialogShow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryListAdapter extends SimpleAdapter{
	private SharedPreferences sp;
	private Editor editor;
	ShowHistoryActivity activity;
	//ArrayList<HashMap<String,String>> list; 
	private ProgressDialog mProgressDialog; 
	DBManager mgr;
	String cancleid;
	String hospitalid;
	String message="";
	TextView cancleid_text;
	TextView hospitalid_text;
	
	public HistoryListAdapter(Context context,List<? extends Map<String, ?>> data, int resource, String[] from,int[] to) {
		super(context, data, resource, from, to);
		activity=(ShowHistoryActivity) context;
		sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		//list=(ArrayList<HashMap<String, String>>) data;
		mProgressDialog = new ProgressDialog(activity);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mgr=activity.getMgr();
	}

	boolean showing_detail=false;
	@Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
		final View res=super.getView(position, convertView, parent);
		
		final LinearLayout his_list_layout=(LinearLayout)res.findViewById(R.id.his_list_layout);
		final Button his_detail_btn=(Button)res.findViewById(R.id.his_detail_btn);
		final Button his_cancle_btn=(Button)res.findViewById(R.id.his_cancle_btn);
		final LinearLayout his_detail=(LinearLayout)res.findViewById(R.id.his_detail);
		
		final TextView his_doctor_text=(TextView)res.findViewById(R.id.his_doctor);
		final TextView his_hospital_text=(TextView)res.findViewById(R.id.his_hospital);
		final TextView his_doctorid_text=(TextView)res.findViewById(R.id.his_doctorid);
		final TextView his_hospitalid_text=(TextView)res.findViewById(R.id.his_hospitalid);
		
		his_doctor_text.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(activity,DoctorDetailActivity.class);
				intent.putExtra("doctorId", his_doctorid_text.getText().toString());
				//intent.putExtra("doctorVersion", doctorlist.getVersion());
				activity.startActivity(intent);
			}
		});
		
		his_hospital_text.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(activity,HospitalDetailActivity.class);
				intent.putExtra("hospitalId", his_hospitalid_text.getText().toString());
				//intent.putExtra("hospitalVersion", imageAndText.getVersion());
				activity.startActivity(intent);
			}
		});
		
		final TextView his_state=(TextView)res.findViewById(R.id.his_state);
		if(his_state.getText().toString().equals(" 有效 ")){
			
			
			his_state.setBackgroundColor(Color.RED);
			his_state.setTextColor(Color.WHITE);
			his_cancle_btn.setVisibility(View.VISIBLE);
			his_detail_btn.setVisibility(View.VISIBLE);
			his_cancle_btn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					cancleid_text=(TextView)res.findViewById(R.id.his_cancle_id);
					hospitalid_text=(TextView)res.findViewById(R.id.his_hospitalid);
					//cancleid_text.setText("uuuuuu");
					cancledialog();
				}
			});
			his_detail_btn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					if(showing_detail){
						his_detail.setVisibility(View.GONE);
						showing_detail=false;
					}else{
						his_detail.setVisibility(View.VISIBLE);
						showing_detail=true;
					}
				}
			});
			
			
		}else if(his_state.getText().toString().equals("已取消")){
			his_state.setBackgroundColor(Color.GRAY);
			his_state.setTextColor(Color.BLACK);
			his_cancle_btn.setVisibility(View.GONE);
			his_detail_btn.setVisibility(View.GONE);
			his_cancle_btn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					Toast.makeText(activity, "已经被取消！", Toast.LENGTH_SHORT).show();
				}
			});
			his_detail_btn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					Toast.makeText(activity, "已经被取消！", Toast.LENGTH_SHORT).show();
				}
			});
		}else{
			his_state.setBackgroundColor(Color.YELLOW);
			his_state.setTextColor(Color.BLACK);
			his_cancle_btn.setVisibility(View.GONE);
			his_detail_btn.setVisibility(View.GONE);
			his_cancle_btn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					Toast.makeText(activity, "已经过期", Toast.LENGTH_SHORT).show();
				}
			});
			his_detail_btn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					Toast.makeText(activity, "已经过期", Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		
		if(position%2==0){
			his_list_layout.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.schedule_btn_style_layout_click));
		}else{
			his_list_layout.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.schedule_btn_style_layout));
		}
		
		
		
		
		return res;
	}

	
	private void cancledialog() {
		
	
		
		AlertDialog.Builder builder = new Builder(activity);
		builder.setMessage("确认要取消预约吗？");
		builder.setTitle("提示");

		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				new AsyncTask<Void, Void, Boolean>(){
				    
					@Override  
			        protected void onPreExecute() {   
			            super.onPreExecute(); 
			            mProgressDialog.show();
			        }			
					@Override
					protected Boolean doInBackground(Void... params){
						hospitalid=hospitalid_text.getText().toString();
						cancleid=cancleid_text.getText().toString();
						HandleBooking req=new HandleBooking();
						req.setAucode(GET.Aucode);
						Log.e("cancleid", cancleid);
						req.setCancleID(cancleid);
						req.setCardNum("");
						Log.e("hospitalid", hospitalid);
						req.setHospitalId(hospitalid);
						req.setOrderNum("");
						req.setReserveDate("");
						req.setReserveTime("");
						
						WebService service = new WebService();
						SoapObject  obj = service.cancelBooking(req);
						
						if(obj!=null){
							String isSuccessful=obj.getProperty("isSuccessful").toString();
							message=obj.getProperty("Message").toString();

							//String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
							//String msg=obj.getProperty("msg").toString();//返回的信息
							//Log.e("error1", message);
							//Log.e("error2", msg);
							if(isSuccessful.equals("true")){
								return true;
								
							}else{
								return false;
							}
						}
						else{
							message="取消失败";
							return false;
						}
					}
					
					@Override
					protected void onPostExecute(Boolean result){
						super.onPostExecute(result);
						if(mProgressDialog.isShowing()){
							mProgressDialog.dismiss();
						}
						if (result){
							mgr.cancleBooking(cancleid);
							mgr.closeDB();
							DialogShow.showDialog(activity, "取消成功！");
						}else{
							FatherActivity.SetNotice(message);
							mgr.cancleBooking(cancleid);
							mgr.closeDB();
							DialogShow.showDialog(activity, message);
						}
						
						final Timer t = new Timer();
						t.schedule(new TimerTask() {
							public void run() {
								activity.runOnUiThread(new Runnable()    
						        {    
						            public void run()    
						            {    
						            	activity.refresh();
						            }    
						    
						        });   
								t.cancel(); 
							}
						}, Setting.dialogtimeout+500);
						
					}
					@Override
					protected void onCancelled()
					{
						super.onCancelled();
				
					}
				
				}.execute();
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	
}

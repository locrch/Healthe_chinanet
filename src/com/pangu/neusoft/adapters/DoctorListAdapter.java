package com.pangu.neusoft.adapters;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;  
import java.util.Map;


import com.pangu.neusoft.core.models.ConnectDoctor;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.healthe.DoctorDetailActivity;
import com.pangu.neusoft.healthe.HospitalDetailActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.AsyncBitmapLoader;
import com.pangu.neusoft.tools.AsyncBitmapLoader.ImageCallBack;
import com.pangu.neusoft.tools.SortListByItem;
  
  
  
import android.R.integer;
import android.app.Activity;  
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;  
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;  
import android.widget.ImageButton;
import android.widget.ImageView;  
import android.widget.LinearLayout;
import android.widget.ListView;  
import android.widget.TextView;  
  
public class DoctorListAdapter extends ArrayAdapter<DoctorList> {  
  
          
        private AsyncBitmapLoader asyncImageLoader;  
  
        public DoctorListAdapter(Activity activity, List<DoctorList> imageAndTexts) {  
            super(activity, 0, imageAndTexts);  
            
            asyncImageLoader = new AsyncBitmapLoader();  
        }  
  
        public View getView(int position, View convertView, ViewGroup parent) {  
            final Activity activity = (Activity) getContext();  
          
            
            // Inflate the views from XML  
            View rowView = convertView;  
            DoctorListViewHolder viewCache;  
            if (rowView == null) {  
                LayoutInflater inflater = activity.getLayoutInflater();  
                rowView = inflater.inflate(R.layout.list_doctor_content, null);  
                viewCache = new DoctorListViewHolder(rowView);  
                rowView.setTag(viewCache);  
            } else {  
                viewCache = (DoctorListViewHolder) rowView.getTag();  
            }  
            final DoctorList doctorlist = getItem(position);  
  
            // Load the image and set it on the ImageView  
            String imageUrl = doctorlist.getImageUrl();  
            ImageView imageView = viewCache.getImageView();  
            imageView.setTag(imageUrl);  
            
            Bitmap bitmap=asyncImageLoader.loadBitmap(imageView, imageUrl, new ImageCallBack() {  
                
                @Override  
                public void imageLoad(ImageView imageView, Bitmap bitmap) {  
                    // TODO Auto-generated method stub  
                		imageView.setImageBitmap(bitmap);  
                }  
            });  
            if (bitmap == null) {  
                imageView.setImageResource(R.drawable.da);  
            }else{  
            	imageView.setImageBitmap(bitmap); 
            }  
            SharedPreferences sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
            int width=sp.getInt("screen_width", 0);
            int height=sp.getInt("screen_height", 0);            
            imageView.getLayoutParams().width=width/6;
            
            imageView.getLayoutParams().height=height/6;
            //imageView.getLayoutParams().height=width/4;
            // Set the text on the TextView  
            TextView textView = viewCache.getTextView();  
            textView.setText(doctorlist.getText());  
            textView.setTextSize(width/Setting.fontsizex);
            
            TextView idView = viewCache.getIdView();
            idView.setText(doctorlist.getId());
            
          
            
            TextView levelView = viewCache.getLevelView();  
            levelView.setText(doctorlist.getLevel());  
            levelView.setTextSize(width/Setting.fontsizes);
            levelView.setTextColor(Color.RED);
            
            
            TextView versionView = viewCache.getVersionView();  
            versionView.setText(doctorlist.getVersion());  
            
            ImageView moreInfo = viewCache.getMoreInfoView();
            moreInfo.getLayoutParams().width=width/10;
            moreInfo.getLayoutParams().height=height/10;
            moreInfo.setClickable(true);
            
            //动态生成按钮
            
            LinearLayout scheduleLayout=viewCache.getScheduleView();
            scheduleLayout.removeAllViews();
            SortListByItem sort=new SortListByItem();
            
            List<Schedule> scheduleList=doctorlist.getScheduleList();
            scheduleList=sort.sortScheduleByDay(scheduleList);
            if(scheduleList!=null&&scheduleList.size()>0){
            	
            	Map<String,String> maps=new LinkedHashMap<String,String>();
            
	            for(Schedule schedule:scheduleList){
	            	String day=schedule.getOutcallDate();
	            	String number=schedule.getAvailableNum();
	            	String now=maps.get(day);
	            	if(now!=null){
	            		int res=Integer.parseInt(now)+Integer.parseInt(number);
	            		number=res+"";
	            	}
	            	maps.put(day, number);
	            }
	            //maps.clear();//暂时不在列表显示排班表
	            
	            if(maps.size()>0){
	            //int count=0;
	            String scheduletext="可预约日期：\n";
		            for (String key : maps.keySet()) {
		            	scheduletext+=key+" ";
		            	/*
		            	if(count==Setting.schedule_show_num)
		            		break;
		                String value= maps.get(key);
		                
		                LayoutInflater inflater = activity.getLayoutInflater();  
		                View scheduleView = inflater.inflate(R.layout.schedule_day_number, null); 
		                TextView day=(TextView) scheduleView.findViewById(R.id.schedule_day);
		                TextView number=(TextView) scheduleView.findViewById(R.id.schedule_number);
		                day.setText(key);
		                number.setText(value);                
		                scheduleView.setPadding(2, 2, 2, 2);
		                
		                scheduleLayout.addView(scheduleView);
		                
		               count++;
		               */                
		            }
		            TextView scheduleText=viewCache.getScheduleText();
		            scheduleText.setText(scheduletext);//显示有排班的日期：
	            }
            }
           
           
            
           // moreInfo.setFocusable(true);
            moreInfo.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// 显示详细内容
					if(activity.getLocalClassName().equals("DoctorListActivity")){
						Intent intent=new Intent(activity,DoctorDetailActivity.class);
						intent.putExtra("doctorId", doctorlist.getId());
						intent.putExtra("doctorVersion", doctorlist.getVersion());
						activity.startActivity(intent);
		            }else{
		            	
		            	Intent intent=new Intent(activity,DoctorDetailActivity.class);
						
						
		            			
						String doctorText=doctorlist.getText(); //获得AreaName
						//获得名称
						String[] arraydoctorText=doctorText.split("\n");
						String hospitalName=arraydoctorText[0];
						String departmentName=arraydoctorText[1];
						String doctorName=arraydoctorText[2];
						//获得ID
						String doctorIds=doctorlist.getId();
						String[] arraydoctorId=doctorIds.split("\\|");
						String hospitalId=arraydoctorId[0];
						String departmentId=arraydoctorId[1];
						String doctorId=arraydoctorId[2];
						
						SharedPreferences sp= activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
						Editor editor=sp.edit();;
						
						editor.putString("hospitalId", hospitalId);
						editor.putString("hospitalName", hospitalName);
						editor.putString("departmentId", departmentId);
						editor.putString("departmentName", departmentName);
						editor.commit();
						
						intent.putExtra("doctorId", doctorId);
						intent.putExtra("doctorVersion", doctorlist.getVersion());
						activity.startActivity(intent);
		            	
		            }
					
				}
            	
            });            
            
            return rowView;  
        }  
  
     
        
}  

package com.pangu.neusoft.healthe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.tools.SortListByItem;
import com.pangu.neusoft.user.ListPeopleActivity;

public class GetSchedule {
	private Activity activity;
	
	private SharedPreferences sp;
	private Editor editor;
	Map<String,Schedule> schedules;
	public View getScheduleView(Activity activity,List<Schedule> scheduleList){
		this.activity=activity;
		sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		schedules=new HashMap<String,Schedule>();
		//添加Schedule表
		String firstdate="";
		SortListByItem sort=new SortListByItem();
		 
            scheduleList=sort.sortScheduleByDay(scheduleList);
            
            Map<String,String> maps=new LinkedHashMap<String,String>();
            
            for(Schedule schedule:scheduleList){
            	String day=schedule.getDayOfWeek();//星期
            	String date=schedule.getOutcallDate();//日期
            	String parts="AM";//上下午
            	String number=schedule.getAvailableNum();//号数
            	
            	
            	
            	if(Integer.parseInt(schedule.getTimeRange().substring(0,2))<12){
            		parts="AM";
            	}else{
            		parts="PM";
            	}
            	
            	
            	
            	String now=maps.get(date+"|"+parts+"|"+day);
            	
            	if(now!=null){
            		try{
	            		int res=Integer.parseInt(now)+Integer.parseInt(number);
	            		number=res+"";
            		}catch(Exception ex){
            		}
            	}
            	maps.put(date+"|"+parts+"|"+day, number);
            	schedules.put(date+"|"+parts+"|"+day,schedule);
            	if(firstdate.equals("")){
            		firstdate=date+"|"+parts+"|"+day;
            	}
            }
           
            
            LayoutInflater inflater = activity.getLayoutInflater();  
            View scheduleView = inflater.inflate(R.layout.schedule_table, null); 
            
            
            TextView day1=(TextView) scheduleView.findViewById(R.id.day1);
            TextView day2=(TextView) scheduleView.findViewById(R.id.day2);
            TextView day3=(TextView) scheduleView.findViewById(R.id.day3);
            TextView day4=(TextView) scheduleView.findViewById(R.id.day4);
            TextView day5=(TextView) scheduleView.findViewById(R.id.day5);
            TextView day6=(TextView) scheduleView.findViewById(R.id.day6);
            TextView day7=(TextView) scheduleView.findViewById(R.id.day7);
            TextView day8=(TextView) scheduleView.findViewById(R.id.day8);
            
            TextView day1am=(TextView) scheduleView.findViewById(R.id.day1am);
            TextView day1pm=(TextView) scheduleView.findViewById(R.id.day1pm);			            
            TextView day2am=(TextView) scheduleView.findViewById(R.id.day2am);
            TextView day2pm=(TextView) scheduleView.findViewById(R.id.day2pm);
            TextView day3am=(TextView) scheduleView.findViewById(R.id.day3am);
            TextView day3pm=(TextView) scheduleView.findViewById(R.id.day3pm);
            TextView day4am=(TextView) scheduleView.findViewById(R.id.day4am);
            TextView day4pm=(TextView) scheduleView.findViewById(R.id.day4pm);
            TextView day5am=(TextView) scheduleView.findViewById(R.id.day5am);
            TextView day5pm=(TextView) scheduleView.findViewById(R.id.day5pm);
            TextView day6am=(TextView) scheduleView.findViewById(R.id.day6am);
            TextView day6pm=(TextView) scheduleView.findViewById(R.id.day6pm);
            TextView day7am=(TextView) scheduleView.findViewById(R.id.day7am);
            TextView day7pm=(TextView) scheduleView.findViewById(R.id.day7pm);
            TextView day8am=(TextView) scheduleView.findViewById(R.id.day8am);
            TextView day8pm=(TextView) scheduleView.findViewById(R.id.day8pm);
            
            
            Date dt=new Date();
            if(maps.size()>0){
	       
	            //String day1detail=maps.get(firstdate);
	            String[] day1detailArray=firstdate.split("\\|");
	            
	            
	            //获得第一天，进行计算
	            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	            
                try {
					dt=sdf.parse(day1detailArray[0]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               
               /*
                * 第一个
                * */ 
                /*
                day1.setText((day1detailArray[0]).substring(8,10)+"\n周"+day1detailArray[2]);
                if(maps.get(day1detailArray[0]+"|AM|"+day1detailArray[2])!=null){
                	day1am.setText(maps.get(day1detailArray[0]+"|AM|"+day1detailArray[2]).toString());
                }
                if(maps.get(day1detailArray[0]+"|PM|"+day1detailArray[2])!=null){
                	day1pm.setText(maps.get(day1detailArray[0]+"|PM|"+day1detailArray[2]).toString());
                }*/
                /*
	                * 第二到七个
	                * */ 
                setTable(dt,sdf,maps,day1,day1am,day1pm,0);
                setTable(dt,sdf,maps,day2,day2am,day2pm,1);
                setTable(dt,sdf,maps,day3,day3am,day3pm,2);
                setTable(dt,sdf,maps,day4,day4am,day4pm,3);
                setTable(dt,sdf,maps,day5,day5am,day5pm,4);
                setTable(dt,sdf,maps,day6,day6am,day6pm,5);
                setTable(dt,sdf,maps,day7,day7am,day7pm,6);
                setTable(dt,sdf,maps,day8,day8am,day8pm,7);
                
                /*
                Calendar daysCal = Calendar.getInstance();			                
                daysCal.setTime(dt);			                
                daysCal.add(Calendar.DATE,1);
                Date dt2=daysCal.getTime();			                
                String datas2=sdf.format(dt2);
                day2.setText(datas2.substring(8,10)+"\n周"+dt2.getDay());
                if(maps.get(datas2+"|AM|"+dt2.getDate())!=null){
                	day2am.setText(maps.get(datas2+"|AM|"+dt2.getDate()).toString());
                }
                if(maps.get(datas2+"|PM|"+dt2.getDate())!=null){
                	day2pm.setText(maps.get(datas2+"|PM|"+dt2.getDate()).toString());
                }
                */
                
            }

                scheduleView.setPadding(2, 2, 2, 2);
                return scheduleView;
		
	}
	
	public void setTable(Date dt,SimpleDateFormat sdf,Map maps,TextView dayX,TextView dayXam,TextView dayXpm,int x){
		   Calendar daysCal = Calendar.getInstance();			                
         daysCal.setTime(dt);			                
         daysCal.add(Calendar.DATE,x);
         Date dtx=daysCal.getTime();			                
         String datasX=sdf.format(dtx);
         dayX.setText(datasX.substring(8,10)+"\n周"+dtx.getDay());
         
         if(maps.get(datasX+"|AM|"+dtx.getDay())!=null){
        	 String counta=maps.get(datasX+"|AM|"+dtx.getDay()).toString();
         	 dayXam.setText(counta);
         	 
         	 
         	 if(Integer.parseInt(counta)>0){
         		dayXam.setBackgroundColor(Color.GREEN);
          		dayXam.setTag(schedules.get(datasX+"|AM|"+dtx.getDay()));
          		dayXam.setOnClickListener(click);
         	 }else{
         		dayXam.setBackgroundColor(Color.RED);
         	 }
         	 
         }else{
        	 dayXam.setBackgroundColor(Color.GRAY);
         }
         if(maps.get(datasX+"|PM|"+dtx.getDay())!=null){
        	 String countp=maps.get(datasX+"|PM|"+dtx.getDay()).toString();
          	 dayXpm.setText(countp);
          	 if(Integer.parseInt(countp)>0){          		
          		dayXpm.setBackgroundColor(Color.GREEN);
          		dayXpm.setTag(schedules.get(datasX+"|PM|"+dtx.getDay()));
          		dayXpm.setOnClickListener(click);
          	 }else{
          		dayXpm.setBackgroundColor(Color.RED);
          	 }
          	
         }else{
        	 dayXpm.setBackgroundColor(Color.GRAY);
         }
		
	}
	
	public OnClickListener click=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Schedule schedule=(Schedule)v.getTag();
			Intent intent=new Intent();
			
			intent.setClass(activity, ListPeopleActivity.class);
			editor.putString("SchState", schedule.getSchState());
			editor.putString("ScheduleID", schedule.getScheduleID());
			editor.putString("RegId", schedule.getRegId());
			editor.putString("ReserveDate", schedule.getOutcallDate());
			editor.putString("ReserveTime", schedule.getTimeRange());
			editor.putString("IdType", "");
			editor.putString("IdCode", "");
			editor.commit();
			activity.startActivity(intent);
		}
		
	};
	
}

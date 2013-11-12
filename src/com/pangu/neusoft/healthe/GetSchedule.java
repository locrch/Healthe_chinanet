package com.pangu.neusoft.healthe;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.tools.DateWidgetDayCell;
import com.pangu.neusoft.tools.DateWidgetDayHeader;
import com.pangu.neusoft.tools.DayStyle;
import com.pangu.neusoft.tools.SortListByItem;

public class GetSchedule {
	private Activity activity;
	
	private SharedPreferences sp;
	private Editor editor;
	Map<String,Schedule> schedules;
	private List<Schedule> scheduleList;
	TextView selectdate;
	private List<String> timetable=new ArrayList<String>();
	
	//排班表日历显示行数
	private final static int showRows=4;
	// 生成日历，外层容器
	private LinearLayout layContent = null;
	private LinearLayout layContent2 = null;
	private ArrayList<DateWidgetDayCell> days = new ArrayList<DateWidgetDayCell>();

	// 日期变量
	public static Calendar calStartDate = Calendar.getInstance();
	private Calendar calToday = Calendar.getInstance();
	private Calendar calCalendar = Calendar.getInstance();
	private Calendar calSelected = Calendar.getInstance();

	// 当前操作日期
	private int iMonthViewCurrentMonth = 0;
	private int iMonthViewCurrentYear = 0;
	private int iFirstDayOfWeek = Calendar.MONDAY;

	private int Calendar_Width = 0;
	private int Cell_Width = 0;

	// 页面控件
	TextView Top_Date = null;
	
	TextView arrange_text = null;
	LinearLayout mainLayout = null;
	LinearLayout arrange_layout = null;
	LinearLayout arrange_layout2 = null;
	// 数据源
	
	Boolean[] flag = null;
	Calendar startDate = null;
	Calendar endDate = null;
	int dayvalue = -1;

	public static int Calendar_WeekBgColor = 0;
	public static int Calendar_DayBgColor = 0;
	public static int isHoliday_BgColor = 0;
	public static int unPresentMonth_FontColor = 0;
	public static int isPresentMonth_FontColor = 0;
	public static int isToday_BgColor = 0;
	public static int special_Reminder = 0;
	public static int common_Reminder = 0;
	public static int Calendar_WeekFontColor = 0;
	
	public static int have_schedule_color=0;
	String UserName = "";
	
	
	public View getScheduleView(Activity activity,List<Schedule> scheduleList){
		this.activity=activity;
		
		SortListByItem sort=new SortListByItem();
		this.scheduleList=sort.sortScheduleByDay(scheduleList);
		sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		schedules=new HashMap<String,Schedule>();
		//添加日历
            LayoutInflater inflater = activity.getLayoutInflater();  
           // View scheduleView = inflater.inflate(R.layout.schedule_table, null); 
            
        	// 获得屏幕宽和高，并計算出屏幕寬度分七等份的大小
    		WindowManager windowManager = activity.getWindowManager();
    		Display display = windowManager.getDefaultDisplay();
    		int screenWidth = display.getWidth();
    		Calendar_Width = screenWidth;
    		Cell_Width = Calendar_Width / 7 + 1;

    		// 制定布局文件，并设置属性
    		mainLayout = (LinearLayout) activity.getLayoutInflater().inflate(
    				R.layout.schedule_table, null);
    		//mainLayout.setPadding(0, 0, 0, 0);
    		

    		// 声明控件，并绑定事件
    		Top_Date = (TextView) mainLayout.findViewById(R.id.Top_Date);
    		

    		// 计算本月日历中的第一天(一般是上月的某天)，并更新日历
    		calStartDate = getCalendarStartDate();
    		//生成日历主体
    		mainLayout.addView(generateCalendarMain());
    		DateWidgetDayCell daySelected = updateCalendar();

    		if (daySelected != null)
    			daySelected.requestFocus();

    		LinearLayout.LayoutParams Param1 = new LinearLayout.LayoutParams(
    				ViewGroup.LayoutParams.MATCH_PARENT,
    				ViewGroup.LayoutParams.MATCH_PARENT);

    		ScrollView view = new ScrollView(activity);
    		arrange_layout = createLayout(LinearLayout.VERTICAL);
    		arrange_layout.setPadding(5, 2, 0, 0);
    		arrange_layout2 = createLayout(LinearLayout.VERTICAL);
    		arrange_layout2.setPadding(5, 2, 0, 0);
    		arrange_text = new TextView(activity);
    		mainLayout.setBackgroundColor(Color.WHITE);
    		arrange_text.setTextColor(Color.BLACK);
    		arrange_text.setTextSize(18);
    		arrange_layout.addView(arrange_text);

    		startDate = GetStartDate();
    		calToday = GetTodayDate();

    		endDate = GetEndDate(startDate);
    		
    		view.addView(arrange_layout, Param1);
    		
    		mainLayout.addView(view);

    		mainLayout.addView(arrange_layout2,Param1);

    		Calendar_WeekBgColor = activity.getResources().getColor(
    				R.color.Calendar_WeekBgColor);
    		Calendar_DayBgColor = activity.getResources().getColor(
    				R.color.Calendar_DayBgColor);
    		isHoliday_BgColor = activity.getResources().getColor(
    				R.color.green);
    		unPresentMonth_FontColor = activity.getResources().getColor(
    				R.color.unPresentMonth_FontColor);
    		isPresentMonth_FontColor = activity.getResources().getColor(
    				R.color.isPresentMonth_FontColor);
    		isToday_BgColor = activity.getResources().getColor(R.color.isToday_BgColor);
    		special_Reminder = activity.getResources()
    				.getColor(R.color.specialReminder);
    		common_Reminder = activity.getResources().getColor(R.color.commonReminder);
    		Calendar_WeekFontColor = activity.getResources().getColor(
    				R.color.Calendar_WeekFontColor);
    		have_schedule_color = activity.getResources().getColor(
    				R.color.green);
            
            return mainLayout;
	}
	
	
	protected String GetDateShortString(Calendar date) {
		String returnString = date.get(Calendar.YEAR) + "/";
		returnString += date.get(Calendar.MONTH) + 1 + "/";
		returnString += date.get(Calendar.DAY_OF_MONTH);
		
		return returnString;
	}

	// 得到当天在日历中的序号
	private int GetNumFromDate(Calendar now, Calendar returnDate) {
		Calendar cNow = (Calendar) now.clone();
		Calendar cReturnDate = (Calendar) returnDate.clone();
		setTimeToMidnight(cNow);
		setTimeToMidnight(cReturnDate);
		
		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;
		int index = millisecondsToDays(intervalMs);
		
		return index;
	}

	private int millisecondsToDays(long intervalMs) {
		return Math.round((intervalMs / (1000 * 86400)));
	}

	private void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	// 生成布局
	private LinearLayout createLayout(int iOrientation) {
		LinearLayout lay = new LinearLayout(activity);
		lay.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		lay.setOrientation(iOrientation);
		
		return lay;
	}

	// 生成日历头部
	private View generateCalendarHeader() {
		LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);
		// layRow.setBackgroundColor(Color.argb(255, 207, 207, 205));
		
		for (int iDay = 0; iDay < 7; iDay++) {
			DateWidgetDayHeader day = new DateWidgetDayHeader(activity, Cell_Width,
					35);
			
			final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
			day.setData(iWeekDay);
			layRow.addView(day);
		}
		
		return layRow;
	}

	// 生成日历主体
	private View generateCalendarMain() {
		layContent = createLayout(LinearLayout.VERTICAL);
		// layContent.setPadding(1, 0, 1, 0);
		layContent.setBackgroundColor(Color.argb(255, 105, 105, 103));
		layContent.addView(generateCalendarHeader());
		days.clear();
		
		for (int iRow = 0; iRow < showRows; iRow++) {
			layContent.addView(generateCalendarRow());
		}
		
		return layContent;
	}
	
	
	private View generateScheduleMain(String date) {
		
		layContent2 = createLayout(LinearLayout.VERTICAL);
		// layContent.setPadding(1, 0, 1, 0);
		// layContent2.setBackgroundColor(Color.argb(255, 105, 105, 103));
		List<Schedule> schedulesort = new ArrayList<Schedule>();
		for(Schedule schedule:scheduleList){
	    	 if(schedule.getOutcallDate().equals(date)){
	    		 schedulesort.add(schedule);
	    		 	    		 
	    	 }
		}
		
		SortListByItem sort=new SortListByItem();
		schedulesort=sort.sortScheduleByTimeRange(schedulesort);
		
		for(Schedule schedule:schedulesort){
	    	 
	    		 layContent2.addView(generateScheduleRow(schedule) );
	    	
		}
		
		return layContent2;
	}
	
	// 生成schedule中的一行
		private View generateScheduleRow(Schedule schedule) {
			
			Button oneButton=new Button(activity);
			oneButton.setWidth(Calendar_Width);
			oneButton.setPadding(10, 5, 10, 5);
			oneButton.setText(schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
			oneButton.setOnClickListener(booking);
			oneButton.setTag(schedule);
			return oneButton;
		}
	
		
		
		OnClickListener booking=new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Schedule schedule=(Schedule)(((Button)arg0).getTag());
				editor.putString("SchState", schedule.getSchState());
      			editor.putString("ScheduleID", schedule.getScheduleID());
      			editor.putString("RegId", schedule.getRegId());
      			editor.putString("ReserveDate", schedule.getOutcallDate());
      			editor.putString("ReserveTime", schedule.getTimeRange());
      			editor.putString("IdType", "");
      			editor.putString("IdCode", "");
      			editor.commit();
      			Log.e("",""+schedule.getTimeRange()+" " +schedule.getOutcallDate());
      			Intent intent=new Intent();
      			intent.setClass(activity, ListCardActivity.class);
      			activity.startActivity(intent);
      			
			}
			
		};
		

	// 生成日历中的一行，仅画矩形
	private View generateCalendarRow() {
		LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);
		
		for (int iDay = 0; iDay < 7; iDay++) {
			DateWidgetDayCell dayCell = new DateWidgetDayCell(activity, Cell_Width,
					Cell_Width);
			dayCell.setItemClick(mOnDayCellClick);
			days.add(dayCell);
			layRow.addView(dayCell);
		}
		
		return layRow;
	}

	// 设置当天日期和被选中日期
	private Calendar getCalendarStartDate() {
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);

		if (calSelected.getTimeInMillis() == 0) {
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else {
			calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}
		
		UpdateStartDateForMonth();
		return calStartDate;
	}

	// 由于本日历上的日期都是从周一开始的，此方法可推算出上月在本月日历中显示的天数
	private void UpdateStartDateForMonth() {
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);
		iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.HOUR_OF_DAY, 0);
		calStartDate.set(Calendar.MINUTE, 0);
		calStartDate.set(Calendar.SECOND, 0);
		// update days for week
		UpdateCurrentMonthDisplay();
		int iDay = 0;
		int iStartDay = iFirstDayOfWeek;
		
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
	}

	// 更新日历
	private DateWidgetDayCell updateCalendar() {
		DateWidgetDayCell daySelected = null;
		boolean bSelected = false;
		final boolean bIsSelection = (calSelected.getTimeInMillis() != 0);
		final int iSelectedYear = calSelected.get(Calendar.YEAR);
		final int iSelectedMonth = calSelected.get(Calendar.MONTH);
		final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH);
		calCalendar.setTimeInMillis(calStartDate.getTimeInMillis());
		
		for (int i = 0; i < days.size(); i++) {
			final int iYear = calCalendar.get(Calendar.YEAR);
			final int iMonth = calCalendar.get(Calendar.MONTH);
			final int iDay = calCalendar.get(Calendar.DAY_OF_MONTH);
			final int iDayOfWeek = calCalendar.get(Calendar.DAY_OF_WEEK);
			DateWidgetDayCell dayCell = days.get(i);

			// 判断是否当天
			boolean bToday = false;
			
			if (calToday.get(Calendar.YEAR) == iYear) {
				if (calToday.get(Calendar.MONTH) == iMonth) {
					if (calToday.get(Calendar.DAY_OF_MONTH) == iDay) {
						bToday = true;
					}
				}
			}

			// 是否被选中
			bSelected = false;
			
			if (bIsSelection)
				if ((iSelectedDay == iDay) && (iSelectedMonth == iMonth)
						&& (iSelectedYear == iYear)) {
					bSelected = true;
				}			

			if (bSelected)
				daySelected = dayCell;
			//是否有排班表
			boolean bHoliday = false;
			String currectdate=iYear+"-"+String.format("%02d",iMonth+1)+"-"+String.format("%02d", iDay);
			
		     for(Schedule schedule:scheduleList){
		    	 if(schedule.getOutcallDate().equals(currectdate)){
		    		 bHoliday=true;
		    		 break;
		    	 }
		     }
		     
		     dayCell.setData(iYear, iMonth, iDay, bToday, bHoliday,
						iMonthViewCurrentMonth);
		     dayCell.setSelected(bSelected);
			calCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		layContent.invalidate();
		
		return daySelected;
	}

	// 更新日历标题上显示的年月
	private void UpdateCurrentMonthDisplay() {
		String date = calStartDate.get(Calendar.YEAR) + "年"
				+ (calStartDate.get(Calendar.MONTH) + 1) + "月";
		Top_Date.setText(date);
	}

	

	// 点击日历，触发事件
	private DateWidgetDayCell.OnItemClick mOnDayCellClick = new DateWidgetDayCell.OnItemClick() {
		public void OnClick(DateWidgetDayCell item) {
			calSelected.setTimeInMillis(item.getDate().getTimeInMillis());
			int day = GetNumFromDate(calSelected, startDate);
			arrange_text.setText(getOneDayString(day));
			arrange_layout2.removeAllViews();
			arrange_layout2.addView(generateScheduleMain(arrange_text.getText().toString()));
			item.setSelected(true);
			updateCalendar();
		}
	};

	
	public String getOneDayString(int day){
		 Calendar rightNow =GetStartDate();
		 
		 rightNow.add(Calendar.DAY_OF_YEAR, +day);
		 Date dt1=rightNow.getTime();
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	     return sdf.format(dt1);
	}
	
	public Calendar GetTodayDate() {
		Calendar cal_Today = Calendar.getInstance();
		cal_Today.set(Calendar.HOUR_OF_DAY, 0);
		cal_Today.set(Calendar.MINUTE, 0);
		cal_Today.set(Calendar.SECOND, 0);
		cal_Today.setFirstDayOfWeek(Calendar.MONDAY);

		return cal_Today;
	}

	// 得到当前日历中的第一天
	public Calendar GetStartDate() {
		int iDay = 0;
		Calendar cal_Now = Calendar.getInstance();
		cal_Now.set(Calendar.DAY_OF_MONTH, 1);
		cal_Now.set(Calendar.HOUR_OF_DAY, 0);
		cal_Now.set(Calendar.MINUTE, 0);
		cal_Now.set(Calendar.SECOND, 0);
		cal_Now.setFirstDayOfWeek(Calendar.MONDAY);

		iDay = cal_Now.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
		
		if (iDay < 0) {
			iDay = 6;
		}
		
		cal_Now.add(Calendar.DAY_OF_WEEK, -iDay);
		
		return cal_Now;
	}

	public Calendar GetEndDate(Calendar startDate) {
		// Calendar end = GetStartDate(enddate);
		Calendar endDate = Calendar.getInstance();
		endDate = (Calendar) startDate.clone();
		endDate.add(Calendar.DAY_OF_MONTH, 41);
		return endDate;
	}	
	
}

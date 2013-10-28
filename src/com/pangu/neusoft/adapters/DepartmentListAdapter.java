package com.pangu.neusoft.adapters;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Department;
import com.pangu.neusoft.core.models.DoctorReq;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.healthe.BookingMainActivity;
import com.pangu.neusoft.healthe.DepartmentListActivity;
import com.pangu.neusoft.healthe.DoctorListActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.tools.SortListByItem;
import com.pangu.neusoft.user.LoginActivity;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DepartmentListAdapter extends BaseAdapter implements SectionIndexer {  
    private LayoutInflater inflater;  
    private List<Department> list;  
    private int width;
    private int height;
    private String hospitalId;
    
    private HashMap<String, Integer> alphaIndexer;//保存每个索引在list中的位置【#-0，A-4，B-10】   
    private String[] sections;//每个分组的索引表【A,B,C,F...】   
    
    Activity activity;
    public DepartmentListAdapter(Activity activity, List<Department> list,int width,int height,String hospitalId) {
    	//排序
    	this.hospitalId=hospitalId;
    	this.width=width;
    	this.height=height;
    	this.activity=activity;
    	SortListByItem sortList=new SortListByItem();
    	
    	list=sortList.sortDepartmentListByName(list);
    	
        this.inflater = LayoutInflater.from(activity);  
        this.list = list; // 该list是已经排序过的集合，有些项目中的数据必须要自己进行排序。   
        this.alphaIndexer = new HashMap<String, Integer>();  

        for (int i =0; i <list.size(); i++) {  
            String name = getAlpha(list.get(i).getSortKey());  
            if(!alphaIndexer.containsKey(name)){//只记录在list中首次出现的位置   
                alphaIndexer.put(name, i);  
            }  
        }  
        Set<String> sectionLetters = alphaIndexer.keySet();  
        ArrayList<String> sectionList = new ArrayList<String>(  
                sectionLetters);  
        Collections.sort(sectionList);  
        sections = new String[sectionList.size()];  
        sectionList.toArray(sections);  
    }
    /** 
     * 提取英文的首字母，非英文字母用#代替 
     *  
     * @param str 
     * @return 
     */  
    private String getAlpha(String str) {  
        if (str == null) {  
            return "#";  
        }  
  
        if (str.trim().length() == 0) {  
            return "#";  
        }  
  
        char c = str.trim().substring(0, 1).charAt(0);  
        // 正则表达式，判断首字母是否是英文字母   
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");  
        if (pattern.matcher(c + "").matches()) {  
            return (c + "").toUpperCase(); // 大写输出   
        } else {  
            return "#";  
        }  
    }
	@Override
	public int getCount() {
		 return list.size();  
	}
	@Override
	public Object getItem(int position) {
		 return list.get(position); 
	}
	@Override
	public long getItemId(int position) {
		 return position; 
	}
	
	private static class ViewHolder {  
	      TextView alpha;  
	      TextView name;  
	      TextView number;  
	      ImageView detail;
	}  
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;			  
	        if (convertView == null) {  
	            convertView = inflater.inflate(R.layout.list_department_content, null);  
	            holder = new ViewHolder();  
	            holder.alpha = (TextView) convertView.findViewById(R.id.department_list_alpha);  
	            holder.name = (TextView) convertView.findViewById(R.id.department_list_name);  
	            holder.number = (TextView) convertView.findViewById(R.id.department_list_number);
	            holder.detail = (ImageView) convertView.findViewById(R.id.department_more_info);
	            holder.detail.getLayoutParams().width=width/12;
	            holder.detail.getLayoutParams().height=height/12;
	            holder.detail.setClickable(true);
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
	        Department department=list.get(position); 
	        final String name = department.getDepartmentName();  
	        final String number = department.getDepartmentId();
	        final String sort = department.getSortKey();
	        holder.name.setText(name);  
	        holder.number.setText(number); 	        
	        holder.number.setVisibility(View.INVISIBLE);
	
	        // 当前联系人的sortKey   
	        String currentStr = getAlpha(list.get(position).getSortKey());  
	        // 上一个联系人的sortKey   
	        String previewStr = (position - 1) >= 0 ? getAlpha(list.get(  
	                position - 1).getSortKey()) : " ";  
	        /** 
	         * 判断显示#、A-Z的TextView隐藏与可见 
	         */  
	        if (!previewStr.equals(currentStr)) { // 当前联系人的sortKey！=上一个联系人的sortKey，说明当前联系人是新组。   
	            holder.alpha.setVisibility(View.VISIBLE);  
	            holder.alpha.setText(currentStr);  
	        } else {  
	            holder.alpha.setVisibility(View.GONE);  
	        }  
	        final ProgressDialog mProgressDialog = new ProgressDialog(activity);  
	        
	        OnClickListener detail=new OnClickListener(){
	    		@Override
	    		public void onClick(View v) {
	    			// TODO Auto-generated method stub
	    			
	    			new AsyncTask<Void, Void, Boolean>(){
	    				String info="";
	    				String msg="";
	    				@Override  
	    		        protected void onPreExecute() {   
	    		            super.onPreExecute(); 
	    		            mProgressDialog.setMessage("正在加载数据...");   
	    		            mProgressDialog.setIndeterminate(false);  
	    		            mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
	    		            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    		            mProgressDialog.show();
	    		        }			
	    				@Override
	    				protected Boolean doInBackground(Void... params){
	    					WebService service=new WebService();
	    	    			DoctorReq req=new DoctorReq();
	    	    			req.setDepartmentId(number);
	    	    			req.setAucode(GET.Aucode);
	    	    			req.setHospitalId(hospitalId);
	    	    			
	    	    			
	    					SoapObject  obj = service.getDepartmentInfo(req);
	    					
	    					if(obj!=null){
	    						SoapObject detailObject=(SoapObject)obj.getProperty("departmentdetail");
	    						//info=detailObject.getProperty("info").toString();接口有info取消此注释且注释下一行
	    						info=detailObject.getProperty("departmentName").toString();
	    						String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
	    						msg=obj.getProperty("msg").toString();//返回的信息
	    						//Log.e("",msg+"  ;;; "+resultCode+" .... "+msg);
	    						
	    						return true;
	    					}
	    					else
	    						return false;
	    				}
	    				
	    				@Override
	    				protected void onPostExecute(Boolean result){
	    					super.onPostExecute(result);
	    					mProgressDialog.dismiss();
	    					if (result){
	    						DialogShow.showDialog(activity, info);   
	    					}else{
	    						DialogShow.showDialog(activity, msg);    			
	    					}
	    					
	    						    					
	    				}
	    				@Override
	    				protected void onCancelled()
	    				{
	    					mProgressDialog.dismiss();
	    					super.onCancelled();
	    			
	    				}

	    			}.execute();
	    			
	    		}
	    	};
	    	holder.detail.setOnClickListener(detail);
	       
	        
	        
	        return convertView;  
	}
	
	
	
    /* 
     * 此方法根据联系人的首字母返回在list中的位置 
     */  
	@Override
	public int getPositionForSection(int section) {
        String later = sections[section];  
        return alphaIndexer.get(later);  
	}
	@Override
	public int getSectionForPosition(int position) {
		 String key = getAlpha(list.get(position).getSortKey());  
         for (int i = 0; i < sections.length; i++) {  
             if (sections[i].equals(key)) {  
                 return i;  
             }  
         }  
         return 0;  
	}
	
	
	
	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return sections; 
	}  
}

package com.pangu.neusoft.tools;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.pangu.neusoft.core.models.Department;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.tools.SortListByItem;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class DepartmentListAdapter extends BaseAdapter implements SectionIndexer {  
    private LayoutInflater inflater;  
    private List<Department> list;  
    
    private HashMap<String, Integer> alphaIndexer;//保存每个索引在list中的位置【#-0，A-4，B-10】   
    private String[] sections;//每个分组的索引表【A,B,C,F...】   
    
    
    public DepartmentListAdapter(Context context, List<Department> list) {
    	//排序
    	SortListByItem sortList=new SortListByItem();
    	
    	list=sortList.sortDepartmentListByName(list);
    	
        this.inflater = LayoutInflater.from(context);  
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
	}  
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;  
			  
	        if (convertView == null) {  
	            convertView = inflater.inflate(R.layout.list_department_content, null);  
	            holder = new ViewHolder();  
	            holder.alpha = (TextView) convertView.findViewById(R.id.department_list_alpha);  
	            holder.name = (TextView) convertView.findViewById(R.id.department_list_name);  
	            holder.number = (TextView) convertView  
	                    .findViewById(R.id.department_list_number);  
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
	        Department department=list.get(position); 
	        String name = department.getDepartmentName();  
	        String number = department.getDepartmentId();
	        String sort = department.getSortKey();
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

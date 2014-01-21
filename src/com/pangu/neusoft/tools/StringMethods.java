package com.pangu.neusoft.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMethods {
	public static boolean isMobileNO(String mobiles){  
		  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");  
		Matcher m = p.matcher(mobiles);  		  
		return m.matches();  
		  
	}  
	
	public static List<Map<String,String>> getPhone(String data){
		boolean rs=false;
		//((\\d+)-(\\d+))+
		String sp="";
		Pattern p;
		Matcher m;
		int i=0; 
        String regEx3=";|\\s|,";
        p=Pattern.compile(regEx3);
        String[] r=p.split(data);
        
		List<Map<String,String>> res=new ArrayList<Map<String,String>>();
        
        for(i=0;i<r.length;i++){
		        p = Pattern.compile("([\\D&&[^\\(\\（]]*)[\\(|\\（]?(\\d*)[\\)|\\）]?[- -]?(\\d+)");
		        m = p.matcher(r[i]); 
	      while (m.find()) {
	    	  Map<String,String> map=new HashMap<String,String>();
	    	       // System.out.println(m.group(1));
	    	  map.put(m.group(1), m.group(2)+m.group(3));
	    	        res.add(map);
		          
	       }
        }
        return res;
	}
}

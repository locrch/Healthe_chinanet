package com.pangu.neusoft.tools;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.widget.Toast;

public class HttpUtils {  
	//static Activity activity;
//	public HttpUtils(Activity activity) {
//		// TODO Auto-generated constructor stub
//		this.activity=activity;
//		
//	}
	
    public static InputStream getStreamFromURL(String imageURL) {  
        InputStream in=null;  
        try {  
            URL url=new URL(imageURL);  
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();  
            in=connection.getInputStream();  
              
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();
            //Toast.makeText(activity, "请检查网络！", Toast.LENGTH_SHORT).show();
        }  
        return in;  
          
    }  
} 
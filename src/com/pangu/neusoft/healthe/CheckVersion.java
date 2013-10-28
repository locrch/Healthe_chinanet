package com.pangu.neusoft.healthe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.os.Environment;
import android.widget.Toast;

public class CheckVersion extends Activity
{
	public static String check_url = "http://202.103.160.158:678/V1/HealthE.apk";
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
	Boolean version;
	
	public Boolean CheckVersion()
	{
		
		if (check_url == "http://202.103.160.158:678/V1/HealthE.apk")
		{
			Toast.makeText(getApplicationContext(), "已经是最新版本！", Toast.LENGTH_SHORT).show();
			
			return false;
		}
		else {
			this.getFileStreamPath(check_url);
			
			return true;
		}
		
	}
	
}

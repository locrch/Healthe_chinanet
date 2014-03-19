package com.pangu.neusoft.drugstore;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.CustomView.CustomProgressDialog;
import com.pangu.neusoft.adapters.DS_DrugCompanyList;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.DS_DrugCompanyResponse;
import com.pangu.neusoft.core.models.DS_GetDrugCompanyReq;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.layout;

import android.R.integer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Drugstore_main_activity extends FatherSonDrugActivity
{
	private ImageView drugstore_1_img,drugstore_2_img,drugstore_3_img,drugstore_4_img;
	private TextView drugstore_1_name,drugstore_2_name,drugstore_3_name,drugstore_4_name;
	private CustomProgressDialog progressDialog;
	String DrugCompanyID,DrugCompanyName,LogoURL,Introduction,Address,Telephone;
	
	public static Bitmap bitmap;
	WebService service;
	DS_DrugCompanyList list;
	ArrayList<DS_DrugCompanyResponse> storeArrayList;
	SharedPreferences sp;
	Editor editor;
	private void Init()
	{
		drugstore_1_img = (ImageView) findViewById(R.id.drugstore_1_img);
		drugstore_2_img = (ImageView) findViewById(R.id.drugstore_2_img);
		drugstore_3_img = (ImageView) findViewById(R.id.drugstore_3_img);
		
		drugstore_1_name = (TextView)findViewById(R.id.drugstore_1_name);
		drugstore_2_name = (TextView)findViewById(R.id.drugstore_2_name);
		drugstore_3_name = (TextView)findViewById(R.id.drugstore_3_name);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drugstore_main_activity);
		Init();
		progressDialog = new CustomProgressDialog(Drugstore_main_activity.this);
		
		service=new WebService();
		
		list = new DS_DrugCompanyList();
		
		sp = this.getSharedPreferences("SP", 0);
		
		editor = sp.edit();
		
		new AsyncTask<Void, Void, Boolean>()
		{
			@Override
			protected void onPreExecute()
			{
				// TODO Auto-generated method stub
				super.onPreExecute();
				
				progressDialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... params)
			{
				// TODO Auto-generated method stub
				DS_GetDrugCompanyReq req = new DS_GetDrugCompanyReq();
				req.setType("2");
				SoapObject obj = service.GetDrugCompany(req);
				
				if (obj!=null)
				{
					SoapObject soapChilds=(SoapObject)obj.getProperty("DrugCompany");
					
					SoapObject DS_DrugCompany = (SoapObject) soapChilds.getProperty("DS_DrugCompany");
					
					DrugCompanyID =  DS_DrugCompany.getProperty("DrugCompanyID").toString();
					
					DrugCompanyName = DS_DrugCompany.getProperty("DrugCompanyName").toString();
					
					LogoURL = DS_DrugCompany.getProperty("LogoUrl").toString();
					
					Introduction = DS_DrugCompany.getProperty("Introduction").toString();
					
					//Address = DS_DrugCompany.getProperty("Address").toString();
					
					//Telephone = DS_DrugCompany.getProperty("Telephone").toString();
					
					list.setDrugCompanyID(DrugCompanyID);
					
					list.setDrugCompanyName(DrugCompanyName);
					
					list.setLogoURL(LogoURL);
					
					list.setIntroduction(Introduction);
					
					//list.setAddress(DS_DrugCompany.getProperty("Address").toString());
					
					//list.setTelephone(DS_DrugCompany.getProperty("Telephone").toString());
					
					bitmap = getHttpBitmap(LogoURL);
					
					editor.putString("DrugCompanyID",DrugCompanyID);
					
					editor.commit();
					
					return true;
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result)
			{
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				progressDialog.dismiss();
				//Toast.makeText(getApplicationContext(), LogoURL, Toast.LENGTH_LONG).show();
				
				drugstore_1_name.setText(list.getDrugCompanyName());
				
				drugstore_1_img.setImageBitmap(bitmap);
			}
		}.execute();

		drugstore_1_img.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(Drugstore_main_activity.this,
						Drugstore_detail.class);

				intent.putExtra("drugstorename", DrugCompanyName);

				startActivity(intent);

			}
		});
	}
	 

}

package com.pangu.neusoft.drugstore;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.pangu.neusoft.CustomView.CustomProgressDialog;
import com.pangu.neusoft.adapters.DS_DrugCompanyList;
import com.pangu.neusoft.healthe.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StoreDetailFragment extends Fragment
{
	DS_DrugCompanyList list;
	ImageView store_img;
	TextView store_text;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_store_detail, container,
				false);

		store_img = (ImageView) view
				.findViewById(R.id.fragment_store_detail_store_img);
		store_text = (TextView) view
				.findViewById(R.id.fragment_store_detail_store_detail);
		list = new DS_DrugCompanyList();
		store_text.setText(list.getIntroduction());
		
		store_img.setImageBitmap(Drugstore_main_activity.bitmap);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
	}
	
	
}

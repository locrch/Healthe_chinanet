package com.pangu.neusoft.drugstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.CustomView.CustomProgressDialog;
import com.pangu.neusoft.adapters.DS_GetDrupShopAdapter;
import com.pangu.neusoft.adapters.DS_GetDrupShopList;
import com.pangu.neusoft.adapters.DS_GetPreferentialNewsAdapter;
import com.pangu.neusoft.adapters.DS_GetPreferentialNewsList;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.DS_GetDrupShopReq;
import com.pangu.neusoft.core.models.DS_GetPreferentialNewsReq;
import com.pangu.neusoft.healthe.HospitalDetailActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.tools.StringMethods;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class OtherStoreFragment extends Fragment
{
	ListView otherstore_listview;
	SharedPreferences sp;
	Editor editor;
	WebService service;
	CustomProgressDialog progressDialog;
	DS_GetDrupShopList list;
	List<DS_GetDrupShopList> otherstorelist;
	String DrupShopID,DrugCompanyID,DrupShopName,LogoUrl,Introduction,Address,Telephone;
	
	private void init(View view)
	{
		otherstore_listview = (ListView)view.findViewById(R.id.fragment_otherstore_listview);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_drugstore_otherstore, container, false);
		
		sp = getActivity().getSharedPreferences("SP", 0);

		editor = sp.edit();
		
		init(view);
		
		otherstorelist = new ArrayList<DS_GetDrupShopList>();

		service = new WebService();

		progressDialog = new CustomProgressDialog(getActivity());
		
		new AsyncTask<Void, Void, Boolean>()
		{

			protected void onPreExecute()
			{
				progressDialog.show();
			};

			@Override
			protected Boolean doInBackground(Void... params)
			{
				// TODO Auto-generated method stub
				DS_GetDrupShopReq req = new DS_GetDrupShopReq();

				req.setDrugCompanyID(sp.getString("DrugCompanyID", "1"));

				req.setType("2");

				
				SoapObject obj = service.GetDrupShop(req);

				if (obj != null)
				{
					SoapObject soapChilds = (SoapObject) obj
							.getProperty("DrugShop");

					for (int i = 0; i < soapChilds.getPropertyCount(); i++)
					{
						SoapObject DS_DrugShop = (SoapObject) soapChilds
								.getProperty(i);

						DrupShopName = DS_DrugShop.getProperty(
								"DrupShopName").toString();

						
						Address = DS_DrugShop.getProperty(
								"Address").toString();

						Telephone = DS_DrugShop.getProperty(
								"Telephone").toString();

						list = new DS_GetDrupShopList();
						list.setDrupShopName(DrupShopName);
						list.setAddress(Address);
						list.setTelephone(Telephone);
						

						otherstorelist.add(list);

					}

					return true;
				}
				return false;
			}

			protected void onPostExecute(Boolean result)
			{
				progressDialog.dismiss();
				DS_GetDrupShopAdapter adapter = new DS_GetDrupShopAdapter(getActivity(), otherstorelist);
				adapter.notifyDataSetChanged();
				otherstore_listview.setAdapter(adapter);
				View rowView;
				
			};

		}.execute();
			
			
		
		return view;
	}
	
	
	
}

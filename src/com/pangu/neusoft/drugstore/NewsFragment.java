package com.pangu.neusoft.drugstore;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.baidu.mapapi.utils.i;
import com.pangu.neusoft.CustomView.CustomProgressDialog;
import com.pangu.neusoft.adapters.DS_GetPreferentialNewsAdapter;
import com.pangu.neusoft.adapters.DS_GetPreferentialNewsList;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.DS_GetPreferentialNewsReq;
import com.pangu.neusoft.healthe.R;

import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class NewsFragment extends Fragment
{
	ListView news_listview;
	SharedPreferences sp;
	Editor editor;
	WebService service;
	CustomProgressDialog progressDialog;
	String PreferentialNewsID,DrugCompanyID,Title,PictureUrl,NewsContent,ExpireTime;
	DS_GetPreferentialNewsList list;
	List<DS_GetPreferentialNewsList> newslist;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_drugstore_news,
				container, false);

		sp = getActivity().getSharedPreferences("SP", 0);

		editor = sp.edit();
		init(view);

		newslist = new ArrayList<DS_GetPreferentialNewsList>();

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
				DS_GetPreferentialNewsReq req = new DS_GetPreferentialNewsReq();
				
				req.setDrugCompanyID(sp.getString("DrugCompanyID", "1"));
				
				req.setType("2");
				
				//req.setPreferentialNewsID("3");
				
				SoapObject obj = service.GetPreferentialNews(req);
				
				if (obj != null)
				{
					SoapObject soapChilds = (SoapObject) obj
							.getProperty("PreferentialNews");

					for (int i = 0; i < soapChilds.getPropertyCount(); i++)
					{
						SoapObject DS_PreferentialNews = (SoapObject) soapChilds
								.getProperty(i);
						
						PreferentialNewsID = DS_PreferentialNews.getProperty("PreferentialNewsID").toString();
						
						DrugCompanyID = DS_PreferentialNews.getProperty("DrugCompanyID").toString();
						
						Title = DS_PreferentialNews.getProperty("Title").toString();
						
						PictureUrl = DS_PreferentialNews.getProperty("PictureUrl").toString();
						
						NewsContent = DS_PreferentialNews.getProperty("NewsContent").toString();
						
						ExpireTime = DS_PreferentialNews.getProperty("ExpireTime").toString();
						
						list = new DS_GetPreferentialNewsList();
						list.setPreferentialNewsID(PreferentialNewsID);
						list.setDrugCompanyID(DrugCompanyID);
						list.setTitle(Title);
						list.setPictureUrl(PictureUrl);
						list.setNewsContent(NewsContent);
						list.setExpireTime(ExpireTime);
						
						
						newslist.add(list);
						
						
						}
					
					
					return true;
				}
				return false;
			}

			protected void onPostExecute(Boolean result)
			{
				progressDialog.dismiss();
				
				DS_GetPreferentialNewsAdapter adapter = new DS_GetPreferentialNewsAdapter(getActivity(), newslist);
				adapter.notifyDataSetChanged();
				news_listview.setAdapter(adapter);
				news_listview.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3)
					{
						// TODO Auto-generated method stub
						DS_GetPreferentialNewsList list = (DS_GetPreferentialNewsList)news_listview.getItemAtPosition(arg2);
						
						String PictureUrl = list.getPictureUrl();
								
						String	NewsContent = list.getNewsContent();
						
						editor.putString("PictureUrl", PictureUrl);
						
						editor.putString("NewsContent", NewsContent);
						
						editor.commit();
						
						startActivity(new Intent(getActivity(),StoreOfferActivity.class));
					}
				});
			};
		}.execute();

		
		return view;
	}

	void init(View view)
	{
		// TODO Auto-generated method stub
		news_listview = (ListView) view
				.findViewById(R.id.fragment_drugstore_news_listview);
		
		
	}

}

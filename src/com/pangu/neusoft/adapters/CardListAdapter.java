package com.pangu.neusoft.adapters;

import java.util.List;
import java.util.Map;

import com.pangu.neusoft.core.models.MedicalCard;
import com.pangu.neusoft.healthcard.BookingAction;
import com.pangu.neusoft.healthcard.CardInfoActivity;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.healthcard.ShowHistoryActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SimpleAdapter;


public class CardListAdapter extends SimpleAdapter{
	private SharedPreferences sp;
	private Editor editor;
	ListCardActivity activity;
	
	public CardListAdapter(Context context,	List<? extends Map<String, ?>> data, int resource, String[] from,int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		activity=(ListCardActivity) context;
		sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
	}
	

	@Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
	   // TODO Auto-generated method stub
		final View res=super.getView(position, convertView, parent);
		
		final CheckBox namebox=(CheckBox)res.findViewById(R.id.cardname);
		final String name=namebox.getText().toString();
		
		String number=sp.getString("defaultcardno", "");
		if(!number.equals("")){
			String owner=sp.getString("card"+number+"_"+"owner", "");
			String cardtype=sp.getString("card"+number+"_"+"cardtype", "");
			String type="";
			if(cardtype.equals("1")){
				type=("(佛山健康卡)");
			}else{
				type=("(居民健康卡)");
			}
			String defaulttext=owner+type;
			//Log.e("defaulttext:",defaulttext);
			
			if(defaulttext.trim().equals(name.trim())){
				namebox.setChecked(true);
				activity.temp=namebox;
			}
			else{
				namebox.setChecked(false);
			}
		}
		
		namebox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@SuppressLint("ShowToast")
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				String defaultnum=sp.getString("defaultcardno", "0");
				int def_num=Integer.parseInt(defaultnum);
				if(arg1){
					//String name=arg0.getText().toString();
					//Map<String,MedicalCard> cards=ListCardActivity.cards;
					//MedicalCard card=cards.get(name);
					
					editor.putString("defaultcardno", position+"");
					editor.commit();
					arg0.setChecked(true);
					if(ListCardActivity.temp!=null){
						ListCardActivity.temp.setChecked(false);
					}
					ListCardActivity.temp=namebox;
					if(Setting.state.equals("booking")&&Setting.bookingdata!=null){
						BookingAction booking=new BookingAction(activity);
						//设置预约基本信息
						Setting.setDefaultCardNumber(sp,editor);
						booking.confirmBooking();
					}else if(Setting.state.equals("history")){
						//activity.startActivity(new Intent(activity,ShowHistoryActivity.class));
						activity.finish();
					}
					Toast.makeText(activity, "默认诊疗卡:"+arg0.getText(), Toast.LENGTH_SHORT).show();
				}else{
					if(def_num==position){
						arg0.setChecked(true);						
						Toast.makeText(activity, "默认诊疗卡！", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		Button carddetail=(Button) res.findViewById(R.id.card_details_infos);
		carddetail.setOnClickListener(new OnClickListener(){
		    @Override
		    public void onClick(View v) {
		     // TODO Auto-generated method stub
		    	
			activity.showinfo(name);
		    	
		    }
		});
		
	 
		
		
		Button btn=(Button) res.findViewById(R.id.deleteCard);
		btn.setOnClickListener(new OnClickListener(){
		    @Override
		    public void onClick(View v) {
		     // TODO Auto-generated method stub
		    	
		    	activity.confirmDeleteDialog(name);
		    }
		});
		
	   return res;
	  }


}

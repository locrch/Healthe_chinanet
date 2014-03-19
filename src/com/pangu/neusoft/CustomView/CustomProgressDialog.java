package com.pangu.neusoft.CustomView;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgressDialog extends ProgressDialog
{

	public CustomProgressDialog(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		super.setMessage("正在加载数据....");
		super.setCancelable(false);
		super.setCancelable(false);
		super.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}

	public CustomProgressDialog(Context context, int theme)
	{
		super(context, theme);
		// TODO Auto-generated constructor stub

	}

}

package com.pangu.neusoft.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConn extends SQLiteOpenHelper{

	public SQLiteDatabase SqliteDatabase;
	
	public DBConn(Context context) {
		super(context, "panguehealth.db", null, 1);
		// TODO Auto-generated constructor stub
		SqliteDatabase=this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="create table users(username varchar(45),gender varchar(45),phone varchar(45),address varchar(45),licence_type varchar(45),licence_num varchar(45))";
		db.execSQL(sql);
		String sql_card="create table cards(username varchar(45),cardtype varchar(45),cardnum varchar(45),hospitalname varchar(45))";
		db.execSQL(sql_card);
		//db.close();
		String sql_history="create table history(hospitalid varchar(45),hospitalname varchar(45)," +
				"departmentid varchar(45),departmentname varchar(45),doctorid varchar(45),doctorname varchar(45),memberid varchar(45)," +
				"scheduleid varchar(45),schstate varchar(45),phonenumber varchar(45),username varchar(45),regid varchar(45),reservedate varchar(45),reservetime varchar(45),idtype varchar(45),idcode varchar(45),cardnumber varchar(45),cancleid varchar(45),serialnumber varchar(45))";
		db.execSQL(sql_history);
		String sql_doctor="create table doctor(hospitalid varchar(45),hospitalname varchar(45)," +
				"departmentid varchar(45),departmentname varchar(45),doctorid varchar(45),doctorname varchar(45),url varchar(1000),version varchar(45),level varchar(45))";
		
		db.execSQL(sql_doctor);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
	}

}

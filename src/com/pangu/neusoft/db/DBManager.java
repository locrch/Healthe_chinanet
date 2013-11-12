package com.pangu.neusoft.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pangu.neusoft.core.models.BookingInfos;
import com.pangu.neusoft.core.models.ConnectDoctor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
	 private DBConn dbconn;  
	    private SQLiteDatabase db;  
	      
	    public DBManager(Context context) {  
	    	dbconn = new DBConn(context);  
	        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);  
	        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里  
	        db = dbconn.getWritableDatabase();  
	    }  
	    
	    
	    public void clearDB(){
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	    	String sql="delete from users";
			db.execSQL(sql);
			String sql_card="delete from cards";
			db.execSQL(sql_card);
			//db.close();
			String sql_history="delete from history";
			db.execSQL(sql_history);
			String sql_doctor="delete from doctor";
			
			db.execSQL(sql_doctor);
	    }
	    /** 
	     * add persons 
	     * @param persons 
	     */  
	    public void add(People person) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        db.beginTransaction();  //开始事务  
	        try {  
	            db.execSQL("INSERT INTO users VALUES(?, ?, ?, ?, ?, ?)", new Object[]{person.getUsername(), person.getSex(), person.getPhone(),person.getAddress(),person.getLicence_type(),person.getLicence_num()});  
	            db.setTransactionSuccessful();  //设置事务成功完成  
	        } finally {  
	            db.endTransaction();    //结束事务  
	        }  
	    }  
	      
	    /** 
	     * update person's age 
	     * @param person 
	     */  
	    public void update(People person) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        ContentValues cv = new ContentValues();  
	        cv.put("gender", person.getSex());
	        cv.put("phone", person.getPhone());
	        cv.put("address", person.getAddress());
	        cv.put("licence_type", person.getLicence_type());
	        cv.put("licence_num", person.getLicence_num());
	        db.update("users", cv, "username = ?", new String[]{person.getUsername()});  
	    }  
	      
	    /** 
	     * delete old person 
	     * @param person 
	     */  
	    public void deleteOldPerson(People person) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        db.delete("users", "username = ?", new String[]{person.getUsername()});  
	    }  
	      
	    /** 
	     * query all persons, return list 
	     * @return List<Person> 
	     */  
	    public List<People> query() {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        ArrayList<People> persons = new ArrayList<People>();  
	        Cursor c = queryTheCursor();  
	        while (c.moveToNext()) {  
	        	People person = new People();  
	            person.setUsername(c.getString(c.getColumnIndex("username")));  
	            person.setSex(c.getString(c.getColumnIndex("gender")));  
	            person.setPhone(c.getString(c.getColumnIndex("phone")));
	            person.setAddress(c.getString(c.getColumnIndex("address")));
	            person.setLicence_type(c.getString(c.getColumnIndex("licence_type")));
	            person.setLicence_num(c.getString(c.getColumnIndex("licence_num")));
	            persons.add(person);  
	        }  
	        c.close();  
	        return persons;  
	    }  
	    
	    
	    /** 
	     * query all persons, return list 
	     * @return List<Person> 
	     */  
	    public People queryByName(String username) {  
	    	
	    	People person=null;  
	        Cursor c = queryThePeopleByName(username);  
	        while (c.moveToNext()) {  
	        	person = new People();
	            person.setUsername(c.getString(c.getColumnIndex("username")));  
	            person.setSex(c.getString(c.getColumnIndex("gender")));  
	            person.setPhone(c.getString(c.getColumnIndex("phone")));
	            person.setAddress(c.getString(c.getColumnIndex("address")));
	            person.setLicence_type(c.getString(c.getColumnIndex("licence_type")));
	            person.setLicence_num(c.getString(c.getColumnIndex("licence_num")));
	            
	        }  
	        c.close();  
	        return person;  
	    }
	      
	    /** 
	     * query all persons, return cursor 
	     * @return  Cursor 
	     */  
	    public Cursor queryTheCursor() {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        Cursor c = db.rawQuery("SELECT * FROM users", null);  
	        return c;  
	    }  
	      
	    /** 
	     * query all persons, return cursor 
	     * @return  Cursor 
	     */  
	    public Cursor queryThePeopleByName(String username) {
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        Cursor c = db.rawQuery("SELECT * FROM users where username=?", new String[]{username});  
	        return c;  
	    }  
	    
	    /** 
	     * add cards 
	     * @param card 
	     */  
	    public void addCards(Cards card) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        db.beginTransaction();  //开始事务  
	        try {  
	            db.execSQL("INSERT INTO cards VALUES(?, ?, ?,?)", new Object[]{card.getUsername(),card.getCardtype(),card.getCardnum(),card.getHospitalname()});  
	            db.setTransactionSuccessful();  //设置事务成功完成  
	         
	        }
	        finally {  
	            db.endTransaction();    //结束事务
	         
	        }  
	    }  
	    
	    /** 
	     * delete Card
	     * @param card 
	     */  
	    public void deleteCard(Cards card) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        db.delete("cards", "username = ? and cardnum = ? ", new String[]{card.getUsername(),card.getCardnum()});  
	    }  
	      
	    /** 
	     * query all cards, return list 
	     * @return List<Cards> 
	     */  
	    public List<Cards> queryCards() {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        ArrayList<Cards> cards = new ArrayList<Cards>();  
	        Cursor c = queryTheCards();  
	        c.moveToFirst();
	        c.moveToPrevious();
	        while (c.moveToNext()) {  
	        	Cards card = new Cards();  
	        	
	            card.setUsername(c.getString(c.getColumnIndex("username")));
	            card.setCardtype(c.getString(c.getColumnIndex("cardtype")));
	            card.setCardnum(c.getString(c.getColumnIndex("cardnum")));
	            card.setHospitalname(c.getString(c.getColumnIndex("hospitalname")));
	            cards.add(card);  
	        }  
	        c.close();  
	        return cards;  
	    } 
	    
	    /** 
	     * query all cards, return cursor 
	     * @return  Cursor 
	     */  
	    public Cursor queryTheCards() {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        Cursor c = db.rawQuery("SELECT * FROM cards", null);  
	        return c;  
	    }  
	    
	    /** 
	     * query all persons, return cursor 
	     * @return  Cursor 
	     */  
	    public Cursor queryTheCardByName(String username) {
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        Cursor c = db.rawQuery("SELECT * FROM cards where username=?", new String[]{username});  
	        return c;  
	    }  
	    
	    /** 
	     * query all cards, return list 
	     * @return List<Cards> 
	     */  
	    public List<Cards> queryCardByName(String username) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        ArrayList<Cards> cards = new ArrayList<Cards>();  
	        Cursor c = queryTheCardByName(username);  
	        c.moveToFirst();
	        c.moveToPrevious();
	        while (c.moveToNext()) {  
	        	Cards card = new Cards();  
	        	
	            card.setUsername(c.getString(c.getColumnIndex("username")));
	            card.setCardtype(c.getString(c.getColumnIndex("cardtype")));
	            card.setCardnum(c.getString(c.getColumnIndex("cardnum")));
	            card.setHospitalname(c.getString(c.getColumnIndex("hospitalname")));
	            cards.add(card);  
	        }  
	        c.close();  
	        return cards;  
	    } 
	    
	    /** 
	     * close database 
	     */  
	    public void closeDB() {  
	        db.close();  
	    }  
	    /** 
	     * add cards 
	     * @param card 
	     */  
	    public void addBookingRecord(BookingInfos bookingdata) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        db.beginTransaction();  //开始事务  
	        try {  
	            db.execSQL("INSERT INTO history VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{
	            		bookingdata.getHospitalid(),
	            		bookingdata.getHospitalname(),
	            		bookingdata.getDepartmentid(),
	            		bookingdata.getDepartmentname(),
	            		bookingdata.getDoctorid(),
	            		bookingdata.getDoctorname(),
	            		bookingdata.getMemberid(),
	            		bookingdata.getScheduleid(),
	            		bookingdata.getSchstate(),
	            		bookingdata.getPhonenumber(),
	            		bookingdata.getUsername(),
	            		bookingdata.getRegid(),
	            		bookingdata.getReservedate(),
	            		bookingdata.getReservetime(),
	            		bookingdata.getIdtype(),
	            		bookingdata.getIdcode(),
	            		bookingdata.getCardnumber(),
	            		bookingdata.getCancelid(),
	            		bookingdata.getSerialNumber()
	            	});  
	            db.setTransactionSuccessful();  //设置事务成功完成  
	         
	        }
	        finally {  
	            db.endTransaction();    //结束事务
	         
	        }  
	    }  
	    
	    public List<BookingInfos> getBookingList(String username,String type) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        ArrayList<BookingInfos> histories = new ArrayList<BookingInfos>();  
	        Cursor c = queryTheHistoryByUsername(username,type);  
	        c.moveToFirst();
	        c.moveToPrevious();
	        while (c.moveToNext()) {  
	        	BookingInfos history = new BookingInfos();  
	        	history.setUsername(c.getString(c.getColumnIndex("username")));
	        	history.setCardnumber(c.getString(c.getColumnIndex("cardnumber")));
	        	history.setCancelid(c.getString(c.getColumnIndex("cancleid")));
	        	history.setHospitalname(c.getString(c.getColumnIndex("hospitalname")));
	        	history.setDepartmentname(c.getString(c.getColumnIndex("departmentname")));
	        	history.setHospitalid(c.getString(c.getColumnIndex("hospitalid")));
	        	history.setDoctorname(c.getString(c.getColumnIndex("doctorname")));
	        	history.setReservedate(c.getString(c.getColumnIndex("reservedate")));
	        	history.setReservetime(c.getString(c.getColumnIndex("reservetime")));
	        	history.setSerialNumber(c.getString(c.getColumnIndex("serialnumber")));
	        	
	        	history.setMedicalCardTypeID("");
	        	histories.add(history);  
	        }  
	        c.close();  
	        return histories; 
	    	
	    }
	    /** 
	     * query all persons, return cursor 
	     * @return  Cursor 
	     */  
	    public Cursor queryTheHistoryByUsername(String username,String type) {
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	    	String sql="SELECT * FROM history where username=?";
	    	if(type.equals("booking")){
	    		sql= "SELECT * FROM history where username=? and cancleid <> ? and reservedate >= ? order by reservedate,reservetime desc";
	    	}else if (type.equals("cancled")){
	    		sql= "SELECT * FROM history where username=? and cancleid = ? and reservedate <> ? order by reservedate,reservetime desc";
	    	}else if (type.equals("passed")){
	    		sql= "SELECT * FROM history where username=? and cancleid <> ? and reservedate < ? order by reservedate,reservetime desc";
	    	}
	    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    		Date date = new Date();                               
    		String nowdate=sf.format(date);
	        Cursor c = db.rawQuery(sql, new String[]{username,"0",nowdate});  
	        return c;  
	    }  
	    
	    
	    /** 
	     * update person's age 
	     * @param person 
	     */  
	    public void cancleBooking(String cancleid) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        ContentValues cv = new ContentValues();  
	        cv.put("cancleid", 0);	        
	        db.update("history", cv, " cancleid = ?", new String[]{cancleid});  
	    }  
	    
	    /** 
	     * add doctor 
	     * @param card 
	     */  
	    public boolean addDoctor(ConnectDoctor doctorinfo) {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	    	/*
	    	 * hospitalid varchar(45),hospitalname varchar(45)," +
				"departmentid varchar(45),departmentname varchar(45),doctorid varchar(45),doctorname varchar(45),url varchar(1000)
	    	*/
	    	String sql="SELECT * FROM doctor where hospitalid=? and departmentid=? and doctorid=?";
	    	Cursor c = db.rawQuery(sql, new String[]{doctorinfo.getHospitalid(),doctorinfo.getDepartmentid(),doctorinfo.getDoctorid(),});
	    	int s=c.getCount();
	    	if(s>0){
	    		return false;
	    	}else{
		        db.beginTransaction();  //开始事务  
		        try {  
		            db.execSQL("INSERT INTO doctor VALUES(?,?,?,?,?,?,?,?,?)", new Object[]{
		            		doctorinfo.getHospitalid(),
		            		doctorinfo.getHospitalname(),
		            		doctorinfo.getDepartmentid(),
		            		doctorinfo.getDepartmentname(),
		            		doctorinfo.getDoctorid(),
		            		doctorinfo.getDoctorname(),
		            		doctorinfo.getImageUrl(),
		            		doctorinfo.getVersion(),
		            		doctorinfo.getLevel()
		            	});  
		            db.setTransactionSuccessful();  //设置事务成功完成  
		         
		        }
		        finally {  
		            db.endTransaction();    //结束事务
		         
		        }  
		        return true;
	    	}
	    }  
	    
	    public List<ConnectDoctor> queryConnectDoctors() {  
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	        ArrayList<ConnectDoctor> doctors = new ArrayList<ConnectDoctor>();  
	        Cursor c = queryAllDoctor();  
	        c.moveToFirst();
	        c.moveToPrevious();
	        while (c.moveToNext()) {  
	        	ConnectDoctor doctor = new ConnectDoctor();  
	        	
	        	doctor.setHospitalid(c.getString(c.getColumnIndex("hospitalid")));
	        	doctor.setHospitalname(c.getString(c.getColumnIndex("hospitalname")));
	        	doctor.setDepartmentid(c.getString(c.getColumnIndex("departmentid")));
	        	doctor.setDepartmentname(c.getString(c.getColumnIndex("departmentname")));
	        	doctor.setDoctorid(c.getString(c.getColumnIndex("doctorid")));
	        	doctor.setDoctorname(c.getString(c.getColumnIndex("doctorname")));
	        	doctor.setImageUrl(c.getString(c.getColumnIndex("url")));
	        	doctor.setVersion(c.getString(c.getColumnIndex("version")));
	        	doctor.setLevel(c.getString(c.getColumnIndex("level")));
	        	doctors.add(doctor);  
	        }  
	        c.close();  
	        return doctors;  
	    } 
	    
	    public Cursor queryAllDoctor() {
	    	if(!db.isOpen()){
	    		 db = dbconn.getWritableDatabase(); 
	    	}
	    	String sql="SELECT * FROM doctor";
	        Cursor c = db.rawQuery(sql,null);  
	        return c;  
	    }  
}

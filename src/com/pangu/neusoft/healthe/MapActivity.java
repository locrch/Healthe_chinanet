package com.pangu.neusoft.healthe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.content.Intent;
import android.content.res.Configuration;    
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;  
import android.widget.Toast;  

import com.baidu.mapapi.BMapManager;  
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKMapViewListener;  
import com.baidu.mapapi.map.MapController;  
import com.baidu.mapapi.map.MapPoi;  
import com.baidu.mapapi.map.MapView;  
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.drawable;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;


public class MapActivity extends FatherActivity {
	public static final String TAG = "BaiduMapGeocodeActivity";
	BMapManager mBMapMan = null;  
	MapView mMapView = null;
	MKSearch mMKSearch=null;
	MapController mMapController=null;
	//List<Address> addresses = null;
	//Geocoder geo;
	//mSearch.gecode("具体地址"，"所在城市");
	
	private String address="佛山市";
	 
    @Override 
    public void onCreate(Bundle savedInstanceState) {    	
    	super.onCreate(savedInstanceState);
        mBMapMan=new BMapManager(getApplication());  
        mBMapMan.init(Setting.mapkey, null);    
        //注意：请在试用setContentView前初始化BMapManager对象，否则会报错  
        setContentView(R.layout.activity_map); 
        setactivitytitle("地图");
        Intent intent = getIntent();
        address=intent.getStringExtra("address");
        String latitude=intent.getStringExtra("latitude");
        String longitude=intent.getStringExtra("longitude");
   
        double  lat;
        double lon;
        try{
        	lat=Double.parseDouble(latitude)*1E6;
        	lon=Double.parseDouble(longitude)*1E6;
        	if(lat==0.0D){
        		lat=Setting.default_lat;
        	}
        	if(lon==0.0D){
        		lon=Setting.default_lon;
        	}
        	Log.e("lon",lon+"");
        	Log.e("lat",lat+"");
        }catch(Exception ex){
        	lat=Setting.default_lat;
        	lon=Setting.default_lon;
        }
        //113.13072,23.01138
        mMapView=(MapView)findViewById(R.id.bmapsView);
        mMapView.setBuiltInZoomControls(true);  
        //设置启用内置的缩放控件  
        mMapController=mMapView.getController();  
        // 得到mMapView的控制权,可以用它控制和驱动平移和缩放  
        // 得到mMapView的控制权,可以用它控制和驱动平移和缩放  
        GeoPoint point =new GeoPoint((int)lat,(int)lon);    	  
       
        //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)  
        
        
       mMapController.setCenter(point);//设置地图中心点  
        mMapController.setZoom(Setting.map_zoom);//设置地图zoom级别 
        Drawable mark= getResources().getDrawable(R.drawable.dingwei);  
        OverlayItem item1 = new OverlayItem(point,address,address);  
        OverlayTest itemOverlay = new OverlayTest(mark, mMapView);  
        mMapView.getOverlays().clear();  
        mMapView.getOverlays().add(itemOverlay); 
        itemOverlay.addItem(item1);
        mMapView.refresh();
    }

    
    class OverlayTest extends ItemizedOverlay<OverlayItem> {  
        //用MapView构造ItemizedOverlay  
        public OverlayTest(Drawable mark,MapView mapView){  
                super(mark,mapView);  
        }  
        protected boolean onTap(int index) {  
            //在此处理item点击事件  
            System.out.println("item onTap: "+index);  
            return true;  
        }  
            public boolean onTap(GeoPoint pt, MapView mapView){  
                    //在此处理MapView的点击事件，当返回 true时  
                    super.onTap(pt,mapView);  
                    return false;  
            }  
            // 自2.1.1 开始，使用 add/remove 管理overlay , 无需重写以下接口  
            /* 
            @Override 
            protected OverlayItem createItem(int i) { 
                    return mGeoList.get(i); 
            } 
            
            @Override 
            public int size() { 
                    return mGeoList.size(); 
            } 
            */  
    }          
    
    
    
    
    @Override  
    protected void onDestroy(){  
            mMapView.destroy();  
            if(mBMapMan!=null){  
                    mBMapMan.destroy();  
                    mBMapMan=null;  
            }  
            super.onDestroy();  
    }  
    @Override  
    protected void onPause(){  
            mMapView.onPause();  
            if(mBMapMan!=null){  
                   mBMapMan.stop();  
            }  
            super.onPause();  
    }  
    @Override  
    protected void onResume(){  
            mMapView.onResume();  
            if(mBMapMan!=null){  
                    mBMapMan.start();  
            }  
           super.onResume();  
    }
    
    
    
    @Override  
    public void onConfigurationChanged(Configuration newConfig) {  
        super.onConfigurationChanged(newConfig);  
    }  
  
    @Override  
    protected void onSaveInstanceState(Bundle outState) {  
        super.onSaveInstanceState(outState);  
        mMapView.onSaveInstanceState(outState);  
    }  
  
    @Override  
    protected void onRestoreInstanceState(Bundle savedInstanceState) {  
        super.onRestoreInstanceState(savedInstanceState);  
        mMapView.onRestoreInstanceState(savedInstanceState);  
    }  
    
    
    public class MyOverlay extends Overlay  
    {  
        GeoPoint geoPoint;
        String address;
        public MyOverlay(GeoPoint geoPoint,String address){
        	this.geoPoint=geoPoint;
        	this.address=address;
        }
        Paint paint = new Paint();  
  
        public void draw(Canvas canvas, MapView mapView, boolean shadow)  
        {  
            // 在天安门的位置绘制一个String  
            Point point = mapView.getProjection().toPixels(geoPoint, null);  
            canvas.drawText("★"+address, point.x, point.y, paint);  
        }  
    }  
    
    
    
    /*class MyOverlay extends ItemizedOverlay<OverlayItem>{  
    	  
        private ArrayList<OverlayItem> overlayList = new ArrayList<OverlayItem>();  
          
        public MyOverlay(Drawable drawable, GeoPoint point, String address) {  
            super(drawable);  
            //overlayList.add(new OverlayItem(point, address, address));  
            //populate();  
        }  
  
        @Override  
        protected OverlayItem createItem(int index) {  
            return overlayList.get(index);  
        }  
  
        @Override  
        public int size() {  
            return overlayList.size();  
        }  
          
    }  */
    
 public GeoPoint getGeoPointBystr(String str) {
    	  GeoPoint gpGeoPoint = null;
    	  if (str!=null) {
    	    Geocoder gc = new Geocoder(MapActivity.this,Locale.CHINA);
    	      List<Address> addressList = null;
    	    try {
    	 
    	    addressList = gc.getFromLocationName(str,1);
    	        if (!addressList.isEmpty()) {
    	      Address address_temp = addressList.get(0);
    	      //计算经纬度
    	      double Latitude=address_temp.getLatitude()*1E7;
    	      double Longitude=address_temp.getLongitude()*1E7;
    	      //System.out.println("经度："+Latitude);
    	      //System.out.println("纬度："+Longitude);
    	      //生产GeoPoint
    	      
    	      
    	      gpGeoPoint = new GeoPoint((int)Latitude, (int)Longitude);
    	    }
    	    } catch (Exception e) {
    	      e.printStackTrace();
    	    }
    	  }
    	  return gpGeoPoint;
    	  }
 
 private void showGeocode(String address, GeoPoint mPoint)  
 {  
     // 将以微度的整数形式存储的地理坐标点转换成GPS纬度经度值  
     double latitude  = mPoint.getLatitudeE6()/1E6; // 纬度  
     double longitude = mPoint.getLongitudeE6()/1E6;// 经度  
     Log.i(TAG, "latitude = " + latitude + "\t longitude = " + longitude);  
       
     StringBuilder sb = new StringBuilder(address);  
     sb.append("\n")  
     .append("纬度:\t").append(latitude)  
     .append("\n")  
     .append("经度:\t").append(longitude);  
       
     new AlertDialog.Builder(this)   
     .setTitle("根据地址名获取GPS纬度、经度值")  
     .setMessage(sb)  
     .show();  
 }  
}


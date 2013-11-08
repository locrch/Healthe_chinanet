package com.pangu.neusoft.tools.update;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;


import com.pangu.neusoft.healthe.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;



public class UpdateOperation {
	
	private final int UPDATA_NONEED = 0;
	private final int UPDATA_CLIENT = 1;
	private final int GET_UNDATAINFO_ERROR = 2;
	private final int SDCARD_NOMOUNTED = 3;
	private final int DOWN_ERROR = 4;
	private final String TAG = "UPDATE:";
	private UpdataInfo info;
	private String localVersion;
	private Activity activity;
	
	/* 
	 * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号) 
	 */  
	
	public UpdateOperation(Activity activity){
		this.activity=activity;
		//checkUpdate();
	}
	public void checkUpdate(){
		try {
			localVersion = getVersionName();
			CheckVersionTask cv = new CheckVersionTask();
			new Thread(cv).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("error",e.toString());
		}
	}
	public static UpdataInfo getUpdataInfo(InputStream is) throws Exception{  
	    XmlPullParser  parser = Xml.newPullParser();    
	    parser.setInput(is, "utf-8");//设置解析的数据源   
	    int type = parser.getEventType();  
	    UpdataInfo info = new UpdataInfo();//实体  
	    while(type != XmlPullParser.END_DOCUMENT ){  
	        switch (type) {  
	        case XmlPullParser.START_TAG:  
	            if("version".equals(parser.getName())){  
	                info.setVersion(parser.nextText()); //获取版本号  
	            }else if ("url".equals(parser.getName())){  
	                info.setUrl(parser.nextText()); //获取要升级的APK文件  
	            }else if ("description".equals(parser.getName())){  
	                info.setDescription(parser.nextText()); //获取该文件的信息  
	            }  
	            break;  
	        }  
	        type = parser.next();  
	    }  
	    return info;  
	}
	
	/* 
	 * 从服务器获取xml解析并进行比对版本号  
	 */  
	private String getVersionName() throws Exception{  
	    //获取packagemanager的实例   
	    PackageManager packageManager = activity.getPackageManager();  
	    //getPackageName()是你当前类的包名，0代表是获取版本信息  
	    PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);  
	    return packInfo.versionName;   
	}  
	
	public class CheckVersionTask implements Runnable{  
	  
	    public void run() {  
	        try {  
	            //从资源文件获取服务器 地址   
	            String path = activity.getResources().getString(R.string.serverurl);  
	            //包装成url的对象   
	            URL url = new URL(path);  
	            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
	            conn.setConnectTimeout(5000);  
	            InputStream is =conn.getInputStream();   
	            info =  UpdataInfoParser.getUpdataInfo(is);  
	              
	            if(info.getVersion().equals(localVersion)){  
	                Log.i(TAG,"版本号相同无需升级");  
	                
	            }else{  
	                Log.i(TAG,"版本号不同 ,提示用户升级 ");  
	                Message msg = new Message();  
	                msg.what = UPDATA_CLIENT;  
	                handler.sendMessage(msg);  
	            }  
	        } catch (Exception e) {  
	            // 待处理   
	            Message msg = new Message();  
	            msg.what = GET_UNDATAINFO_ERROR;  
	            handler.sendMessage(msg);  
	            e.printStackTrace();  
	        }   
	    }  
	}  
	  
	Handler handler = new Handler(){  
	      
	    @Override  
	    public void handleMessage(Message msg) {  
	        // TODO Auto-generated method stub  
	        super.handleMessage(msg);  
	        switch (msg.what) {  
	        case UPDATA_CLIENT:  
	            //对话框通知用户升级程序   
	            showUpdataDialog();  
	            break;  
	        case GET_UNDATAINFO_ERROR:  
	            //服务器超时   
	            //Toast.makeText(activity.getApplicationContext(), "获取服务器更新信息失败", 1).show();  
	            
	            break;    
	        case DOWN_ERROR:  
	            //下载apk失败  
	            Toast.makeText(activity.getApplicationContext(), "下载新版本失败", 1).show();  
	              
	            break;    
	            
	        }  
	    }  
	};  
	  
	/* 
	 *  
	 * 弹出对话框通知用户更新程序  
	 *  
	 * 弹出对话框的步骤： 
	 *  1.创建alertDialog的builder.   
	 *  2.要给builder设置属性, 对话框的内容,样式,按钮 
	 *  3.通过builder 创建一个对话框 
	 *  4.对话框show()出来   
	 */  
	protected void showUpdataDialog() {  
	    AlertDialog.Builder builer = new Builder(activity) ;   
	    builer.setTitle("版本升级");  
	    builer.setMessage(info.getDescription());  
	    //当点确定按钮时从服务器上下载 新的apk 然后安装   
	    builer.setPositiveButton("确定", new OnClickListener() {  
	    public void onClick(DialogInterface dialog, int which) {  
	            Log.i(TAG,"下载apk,更新");  
	            downLoadApk();  
	        }     
	    });  
	    //当点取消按钮时进行登录  
	    builer.setNegativeButton("取消", new OnClickListener() {  
	        public void onClick(DialogInterface dialog, int which) {  
	            
	        }  
	    });  
	    
	    AlertDialog dialog = builer.create();  
	    dialog.show();  
	}  
	  
	/* 
	 * 从服务器中下载APK 
	 */  
	protected void downLoadApk() {  
	    final ProgressDialog pd;    //进度条对话框  
	    pd = new  ProgressDialog(activity);  
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	    pd.setMessage("正在下载更新");  
	    pd.show();  
	    new Thread(){  
	        @Override  
	        public void run() {  
	            try {  
	                File file = DownLoadManager.getFileFromServer(info.getUrl(), pd);  
	                sleep(3000);  
	                installApk(file);  
	                pd.dismiss(); //结束掉进度条对话框  
	            } catch (Exception e) {  
	                Message msg = new Message();  
	                msg.what = DOWN_ERROR;  
	                handler.sendMessage(msg);  
	                e.printStackTrace();  
	            }  
	        }}.start();  
	}  
	  
	//安装apk   
	protected void installApk(File file) {  
	    Intent intent = new Intent();  
	    //执行动作  
	    intent.setAction(Intent.ACTION_VIEW);  
	    //执行的数据类型  
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
	    activity.startActivity(intent);  
	}  
}

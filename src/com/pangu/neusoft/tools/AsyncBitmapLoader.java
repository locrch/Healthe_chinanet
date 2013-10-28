package com.pangu.neusoft.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URLDecoder;
import java.util.HashMap;

import com.pangu.neusoft.healthe.Setting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class AsyncBitmapLoader {
	/**
	 * 内存图片软引用缓冲
	 */
	private HashMap<String, SoftReference<Bitmap>> imageCache = null;

	public AsyncBitmapLoader() {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	public Bitmap loadBitmap(final ImageView imageView, final String imageURL,
			final ImageCallBack imageCallBack) {
		// 在内存缓存中，则返回Bitmap对象
		if (imageCache.containsKey(imageURL)) {
			SoftReference<Bitmap> reference = imageCache.get(imageURL);
			Bitmap bitmap = reference.get();
			if (bitmap != null) {
				Log.e("Loging image ", " From Cache Map "+imageURL);
				return bitmap;
			}
		} else {
			/**
			 * 加上一个对本地缓存的查找
			 */
			if(hasSdcard()){
			
				String[] array=imageURL.split("/");
				
				if(array.length>2){
				
					//String bitmapName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
					String bitmapName = array[array.length-1]+Setting.image_;
					
					String folder = URLDecoder.decode(array[array.length-2]);
					
					//Log.e("TTTTTTTTTTTTTTTTTTTTTT","bitmapName"+bitmapName);
					
					File cacheDir = new File(Setting.catche_dir+"/"+folder);
					
					 if(!cacheDir.exists()){
						 cacheDir.mkdirs();
					 }
					
					File[] cacheFiles = cacheDir.listFiles();
					int i = 0;
					if (null != cacheFiles) {
						for (; i < cacheFiles.length; i++) {
							if (bitmapName.equals(cacheFiles[i].getName())) {
								break;
							}
						}
		
						if (i < cacheFiles.length) {
							Log.e("Loging image ", " From Cache file");
							return BitmapFactory.decodeFile(Setting.catche_dir+"/"+folder+"/"+ bitmapName);
						}
					}
				}
			}
		}

		final Handler handler = new Handler() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				imageCallBack.imageLoad(imageView, (Bitmap) msg.obj);
			}
		};

		Log.e("Loging image ", " From Internet");
		// 如果不在内存缓存中，也不在本地（被jvm回收掉），则开启线程下载图片
		new Thread() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InputStream bitmapIs = HttpUtils.getStreamFromURL(imageURL);
				if(bitmapIs!=null){
					//放到缓存中
					Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);
					imageCache.put(imageURL, new SoftReference<Bitmap>(bitmap));
					Message msg = handler.obtainMessage(0, bitmap);
					handler.sendMessage(msg);
	
					//放到文件中
					if(hasSdcard()){
						//创建目录
						File dir = new File(Setting.catche_dir);
						if (!dir.exists()) {
							dir.mkdirs();
						}
						
						String[] array=imageURL.split("/");
						if(array.length>2){
							String bitmapName = array[array.length-1]+Setting.image_;
							String folder = URLDecoder.decode(array[array.length-2]);
			
							 File folder_dir = new File(Setting.catche_dir+"/"+folder);
							 
							 if(!folder_dir.exists()){
								 folder_dir.mkdirs();
							 }
							//创建文件
							File bitmapFile = new File(Setting.catche_dir+"/"+folder+"/"+ bitmapName);
							//Log.e("file", Setting.catche_dir+"/"+folder+"/"+ bitmapName);
							if (!bitmapFile.exists()) {
								try {
									bitmapFile.createNewFile();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							//写入数据
							FileOutputStream fos;
							try {
								fos = new FileOutputStream(bitmapFile);
								bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
								fos.close();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}.start();

		return null;
	}

	public static boolean hasSdcard() {
	     String status = Environment.getExternalStorageState();
		     if (status.equals(Environment.MEDIA_MOUNTED)) {
		         return true;
		     } else {
		         return false;
		     }
		 }
	
	public interface ImageCallBack {
		public void imageLoad(ImageView imageView, Bitmap bitmap);
	}
}
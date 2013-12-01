package com.pangu.neusoft.tools;


import android.annotation.SuppressLint;  
import android.content.Context;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.DashPathEffect;  
import android.graphics.Paint;  
import android.graphics.Path;
import android.graphics.PathEffect;  
import android.util.AttributeSet;  
import android.widget.TextView;
 
  
  
@SuppressLint({ "ResourceAsColor", "DrawAllocation" })  
public class TextViewWithLine extends TextView {  
//    private Paint mPaint = new Paint();  
  
  
    public TextViewWithLine(Context context) {  
        super(context);  
        
    }  
  
  
    public TextViewWithLine(Context context, AttributeSet attrs) {  
        super(context, attrs);  
          
    }  
  
  
    public TextViewWithLine(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
          
    }  
    
    @Override protected void onDraw(Canvas canvas) {  

    		int left = getLeft();  
    		int right = getRight();
    		int height = getHeight();
    		
    	   Paint paint = new Paint();      
           paint.setStyle(Paint.Style.STROKE);      
           paint.setColor(Color.DKGRAY);      
           Path path = new Path();           
           path.moveTo(0, height-1);      
           path.lineTo(right,height-1);            
           PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);      
           paint.setPathEffect(effects);      
           canvas.drawPath(path, paint);
        super.onDraw(canvas);  
    }  
} 
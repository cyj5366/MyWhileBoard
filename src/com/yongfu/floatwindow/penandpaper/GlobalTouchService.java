package com.yongfu.floatwindow.penandpaper;

import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class GlobalTouchService extends Service implements OnTouchListener{

	private String TAG = this.getClass().getSimpleName();
	// window manager 
	private WindowManager mWindowManager;
	// linear layout will use to detect touch event
	private LinearLayout touchLayout;
	IntentCreator intentCreator;
	WindowManager.LayoutParams mParams;
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		
		
		this.intentCreator = new IntentCreator(getApplicationContext());
		// create linear layout
		touchLayout = new LinearLayout(this);
		// set layout width 30 px and height is equal to full screen
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		touchLayout.setLayoutParams(lp);
		// set color if you want layout visible on screen
		touchLayout.setBackgroundColor(Color.CYAN); 
		// set on touch listener
		touchLayout.setOnTouchListener(this);

		// fetch window manager object 
		 mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		 // set layout parameter of window manager
		  mParams = new WindowManager.LayoutParams(
				  30,//WindowManager.LayoutParams.MATCH_PARENT30, // width of layout 30 px
	        		WindowManager.LayoutParams.MATCH_PARENT, // height is equal to full screen
	                WindowManager.LayoutParams.TYPE_PHONE, // Type Ohone, These are non-application windows providing user interaction with the phone (in particular incoming calls).
	                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // this window won't ever get key input focus  
	                PixelFormat.TRANSLUCENT);      
         mParams.gravity = Gravity.LEFT | Gravity.TOP;   
 		Log.i(TAG, "add View");

	     mWindowManager.addView(touchLayout, mParams);
		
	}
	

	@Override
	public void onDestroy() {
		 if(mWindowManager != null) {
	            if(touchLayout != null) mWindowManager.removeView(touchLayout);
	        }
		super.onDestroy();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		
		mWindowManager.updateViewLayout(v, mParams);
		
		if(event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN )
		{
			try {
				intentCreator.getPendingIntent(IntentCommands.touch.toString()).send();
			} catch (CanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :"+ event.getRawY());
		}
		return true;
	}
	
	

}
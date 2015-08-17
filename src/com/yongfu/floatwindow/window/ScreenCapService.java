package com.yongfu.floatwindow.window;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class ScreenCapService extends Service {
	private static final String TAG = "ScreenCapService";
	
	private static final String ACTION_CAPTURE_SCREEN = 
		"com.android.CAPTURE_SCREEN";
		
	private BroadcastReceiver mCaptureScreenReceiver;
	private ToneGenerator mToneGenerator;
	
    @Override
    public void onCreate() {
        /* capture screen receiver */
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_CAPTURE_SCREEN);
        mCaptureScreenReceiver = new CaptureScreenReceiver();
        registerReceiver(mCaptureScreenReceiver, filter);           
        
        /* capture tone */
        initializeCaptureTone();
    }
	
    @Override
    public void onDestroy() {
        unregisterReceiver(mCaptureScreenReceiver);    
        releaseCaptureTone();
    }
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

    private final class CaptureScreenReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
			if (ACTION_CAPTURE_SCREEN.equals(intent.getAction())) {
				Log.d(TAG, "CaptureScreenReceiver: " + intent.getAction());	
                			
                ToneGenerator tg = mToneGenerator;
                if (tg != null) {
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                }                			
                
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyyMMddHHmmss");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String file_name = "/mnt/usb/sda1/" + formatter.format(curDate) + ".png";	
          
                
                			
//				String file_name = this.startCaptureScreen();	
				if (file_name != null) {	                
					sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, 
						Uri.parse("file://" + file_name)));	
					
					Log.d(TAG, "CaptureScreen as file: " + file_name);
				}
			}            
        }
    }	
	
    private void initializeCaptureTone() {
        // Initialize tone generator.
        try {
            mToneGenerator = new ToneGenerator(
                    AudioManager.STREAM_SYSTEM, 100);
        } catch (Throwable ex) {
            Log.w(TAG, "Exception caught while creating tone generator: ", ex);
            mToneGenerator = null;
        }
    }     	
	
	private void releaseCaptureTone() {
	    if (mToneGenerator != null) {
            mToneGenerator.release();
            mToneGenerator = null;
        }
	}	
	    
	
}

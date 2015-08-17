package com.yongfu.floatwindow.penandpaper;



import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		String action = arg1.getAction();
		if(action.equals("com.yongfu.floatwindow.TOUCH_ACTION"))
		{
			Log.e("MES", "----------------------");
			IntentCreator intentCreator = new IntentCreator(arg0);
			try {
				intentCreator.getPendingIntent(IntentCommands.touch.toString()).send();
			} catch (CanceledException e) {
				e.printStackTrace();
			}
		}
		else if(action.equals("com.yongfu.intent.action.ACTION_SET_VGA_TYPE"))
		{
			int vga = arg1.getIntExtra("vga_type",-1);
			if(vga != -1)
			{
				vga = -1;
			}
			
		}
		else if(action.equals("android.intent.action.BOOT_COMPLETED"))
		{
			//Intent intent = new Intent(arg0, StartServiceActivity.class);
			//arg0.startService(intent);
			
			ComponentName comp = new ComponentName(arg0.getPackageName(), StartServiceActivity.class.getName());  
            
			arg0.startActivity(new Intent().setComponent(comp).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));  
            
		}
        Log.i("dddd", action);  
	}

}

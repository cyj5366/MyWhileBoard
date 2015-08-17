package com.yongfu.floatwindow.penandpaper;



import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class StartServiceActivity extends Activity
{
  public static String CACHE_PATH;
  public static StartServiceActivity app;
  public static int versionCode;
  public static final String TAG = "StartServiceActivity"; 
	
  IntentCreator intentCreator;

  private int getVersionCode()
  {
    int i;
    try
    {
      i = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
      return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return 0;
  }

  private void sendHelpIntent()
  {
    try
    {
      this.intentCreator.getPendingIntent(IntentCommands.help.toString()).send();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void sendVersionUpgradeIntent()
  {
    try
    {
      this.intentCreator.getPendingIntent(IntentCommands.whatsnew.toString()).send();
      return;
    }
    catch (Exception localException)
    {
    }
  }
  
  private void sendStopIntent()
  {
    try
    {
      this.intentCreator.getPendingIntent(IntentCommands.stop.toString()).send();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void startApp()
  {
    try
    {
      Log.d(TAG, "IntentCommands == "+IntentCommands.start.toString()); 
      this.intentCreator.getPendingIntent(IntentCommands.start.toString()).send();
      return;
    }
    catch (PendingIntent.CanceledException localCanceledException)
    {
      localCanceledException.printStackTrace();
    }
  }

  public void createSettings()
  {
    new Settings(getPreferences(0));
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    //versionCode = getVersionCode();
    createSettings();
   // startService(new Intent(this, GlobalTouchService.class));
    CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/InkOverApps/cache";
    Log.i("TAG", CACHE_PATH);
    this.intentCreator = new IntentCreator(getApplicationContext());
    FTUE localFTUE = new FTUE(getPreferences(0));
    //if (localFTUE.checkFTUE())
     // sendHelpIntent();
   // while (true)
    //{
      //while (true)
      //{
        app = this;
        //return;
       // if (!(localFTUE.checkVersionFTUE(versionCode)))
        //  break;
        
        //sendVersionUpgradeIntent();
     // }
      boolean kill = getIntent().getBooleanExtra("kill", false);
      if(kill)
      {
    	  this.finish();
    	  sendStopIntent();
    	  return;
      }
      startApp();
      this.finish();
    //}
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    if (paramIntent.getExtras().getBoolean("kill"));
    try
    {
      this.intentCreator.getPendingIntent(IntentCommands.stop.toString()).send();
      finish();
      return;
    }
    catch (PendingIntent.CanceledException localCanceledException)
    {
      localCanceledException.printStackTrace();
    }
  }


  
  protected void onResume()
  {
    super.onResume();
    moveTaskToBack(true);
  }
  
	
	

  
}
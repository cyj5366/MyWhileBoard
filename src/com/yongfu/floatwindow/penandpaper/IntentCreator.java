package com.yongfu.floatwindow.penandpaper;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IntentCreator
{
  public static final String KILL_STRING = "kill";
  public static int intentsCreated = 1;
  private Context context;

  public IntentCreator(Context paramContext)
  {
    this.context = paramContext;
  }

  private Intent getIntent(String paramString)
  {
    Intent localIntent = new Intent(this.context, DrawingService.class);
    localIntent.putExtra(IntentCommands.command.toString(), paramString);
    return localIntent;
  }

  public PendingIntent getKillIntent()
  {
    Intent localIntent = new Intent(this.context, StartServiceActivity.class);
    localIntent.addFlags(536870912);
    localIntent.putExtra("kill", true);
    return PendingIntent.getActivity(this.context, 0, localIntent, 0);
  }

  public PendingIntent getPendingIntent(String paramString)
  {
	  Log.d("jack.chen","getPendingIntent="+paramString);
    Context localContext = this.context;
    int i = intentsCreated;
    intentsCreated = i + 1;
    return PendingIntent.getService(localContext, i, getIntent(paramString),PendingIntent.FLAG_UPDATE_CURRENT);
  }
}
package com.yongfu.floatwindow.penandpaper;

import com.yongfu.floatwindow.R;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.RemoteViews;

public class NotificationCreator
{
  private static final int NOTIFICATION_ICON = 2130837511;
  private static final int NOTIFICATION_ID = 75;
  private String APP_TITLE;
  private String NOTIFICATION_CONTENT_TEXT;
  private String NOTIFICATION_CONTENT_TITLE;
  private String NOTIFICATION_TICKER_TEXT;
  private Context context;
  PendingIntent hideIntent;
  PendingIntent killapp;
  Notification notification;
  PendingIntent startIntent;
  PendingIntent stopIntent;
  PendingIntent toggleIntent;

  public NotificationCreator(Context paramContext)
  {
    this.context = paramContext;
   // createStrings();
    IntentCreator localIntentCreator = new IntentCreator(paramContext);
    this.startIntent = localIntentCreator.getPendingIntent(IntentCommands.start.toString());
    this.hideIntent = localIntentCreator.getPendingIntent(IntentCommands.hide.toString());
    this.stopIntent = localIntentCreator.getPendingIntent(IntentCommands.stop.toString());
    this.toggleIntent = localIntentCreator.getPendingIntent(IntentCommands.toggle.toString());
    this.killapp = localIntentCreator.getKillIntent();
    //this.notification = createNotification();
  }

  private Notification createNotification()
  {
    return createNotificationJellyBean();
  }

 
  private Notification createNotificationJellyBean()
  {
    RemoteViews localRemoteViews = new RemoteViews(this.context.getPackageName(), R.layout.notification);
    localRemoteViews.setImageViewResource(R.id.appIcon, R.drawable.ic_launcher);
    localRemoteViews.setTextViewText(R.id.appName, this.context.getText(R.string.app_name));
    localRemoteViews.setTextViewText(R.id.appHelp, this.context.getText(R.string.notification_content_subtext));
    localRemoteViews.setCharSequence(R.id.notificationShow, "setText", this.context.getText(R.string.action_show));
    localRemoteViews.setCharSequence(R.id.notificationHide, "setText", this.context.getText(R.string.action_hide));
    localRemoteViews.setCharSequence(R.id.notificationQuit, "setText", this.context.getText(R.string.action_quit));
    localRemoteViews.setOnClickPendingIntent(R.id.notificationShow, this.startIntent);
    localRemoteViews.setOnClickPendingIntent(R.id.notificationHide, this.hideIntent);
    localRemoteViews.setOnClickPendingIntent(R.id.notificationQuit, this.killapp);
    Notification localNotification = new NotificationCompat.Builder(this.context).setSmallIcon(R.drawable.ic_status).setContentText(this.NOTIFICATION_CONTENT_TEXT).setTicker(this.NOTIFICATION_TICKER_TEXT).setContentTitle(this.NOTIFICATION_CONTENT_TITLE).build();
    localNotification.contentView = localRemoteViews;
    localNotification.contentIntent = this.startIntent;
    return localNotification;
  }

  private void createStrings()
  {
    Resources localResources = this.context.getResources();
    this.APP_TITLE = localResources.getString(R.string.app_name);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.APP_TITLE;
    this.NOTIFICATION_TICKER_TEXT = localResources.getString(R.string.notification_ticker_text, arrayOfObject);
    this.NOTIFICATION_CONTENT_TITLE = this.APP_TITLE;
    this.NOTIFICATION_CONTENT_TEXT = localResources.getString(R.string.notification_content_text);
  }

  public int getID()
  {
    return 75;
  }

  //public Notification getNotification()
  //{
   // return this.notification;
  //}
}
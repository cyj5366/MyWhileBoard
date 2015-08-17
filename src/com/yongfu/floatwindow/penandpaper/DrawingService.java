package com.yongfu.floatwindow.penandpaper;



import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
//import android.os.IGpioControl;
import android.os.Message;
import android.os.RemoteException;
//import android.os.ServiceManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.yongfu.floatwindow.R;
import com.yongfu.floatwindow.paintview.ColorDefine;
import com.yongfu.floatwindow.window.ButtonWireing;
import com.yongfu.floatwindow.window.GlobalData;
import com.yongfu.floatwindow.window.GlobalWindowManager;
import com.yongfu.floatwindow.window.MyAnimation;
import com.yongfu.floatwindow.window.NavWindowController;
import com.yongfu.floatwindow.window.UIController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;


public class DrawingService extends Service
{
	private static final String TAG = "DrawingService.java";
	private boolean appToggled = true;
	private ButtonWireing buttonWireing;
	private GlobalWindowManager globalWindowManager;
	private Service thisService;
	private UIController uIController;
	private GlobalData data;
	private  int RecType;	
//	private IGpioControl  m_IGpioControl;
	private static final String RECEIVE_ACTION = "com.labwe.service.receiver";
	private static final String ANNOTATE_ACTION = "com.labwe.annotate"; 
	private static final String ShowCircle = "com.labwe.ShowCircle";
	private static final String HideCircle = "com.labwe.HideCircle";
	Timer timer;




	public void SendMsgToService(int state, int io) {
		Log.d("jack.chen","drawingservice.java SendMsgToService state="+state+" io="+io);
//		m_IGpioControl = IGpioControl.Stub.asInterface(ServiceManager.getService("gpio"));
//		try
//		{
//			m_IGpioControl.gpioControl(state, io);
//		}
//		catch (RemoteException e)
//		{
//
//		}
	}  

	private void hideNotifications()
			throws ClassNotFoundException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
			{
		Log.d("jack.chen","drawingservice.java hideNotifications() ");
		try{  
			Object localObject = getSystemService("statusbar");
			Class.forName("android.app.StatusBarManager").getMethod("collapse", new Class[0]).invoke(localObject, new Object[0]);
		}
		catch(Exception e){
			e.printStackTrace();
		}
			}
	private void onIntentCommandHelp()
	{
		Log.d("jack.chen","drawingservice.java onIntentCommandHelp() ");
		if (this.uIController == null)
		{
			onIntentCommandStart();
			this.uIController.hideAll();
		}
		//this.uIController.showHelp();
	}

	boolean touch_flag = false;
	boolean close_flag = false;
	TimerTask task ;


	Handler touch_SendMsg = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.d("jack.chen","DrawingService.java touch_SendMsg = "+msg.what);
			try {
				switch (msg.what) {
				case 0://hide
				if (uIController != null)
				{
					Log.d("jack.chen","DrawingService.java uIController != null");
					if(data.getValue()==1)//PC模式
						SendMsgToService(10,11);//发android PC
					Log.i("pizhu", "HideNavWindow");
					uIController.HideNavWindow();
					
					close_flag = true;
					if(timer!=null){
						timer.cancel();
						timer = null;
					}
					if(task!=null){	    		      
						task.cancel();	    		      
						task = null;
					}
				}
				break;
				case 1:
					if (uIController != null)
					{
						if(data.getValue()==1)//PC模式
							SendMsgToService(11,11);//发android 
						((com.yongfu.floatwindow.circlemenu.CircleImageView)uIController.getMainLayout().findViewById(R.id.mainButton)).setImageResource(R.drawable.edit_unsel);
						uIController.ShowNavWindow();
						Log.i("pizhu", "ShowNavWindow");
						StartTimer(20000);
						close_flag = false;
					}
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	};

	private void onIntentCommandHide()
	{
		Log.d("jack.chen","drawingservice.java onIntentCommandHide() ");
		if (this.uIController != null)
			this.uIController.hideAll();
	}

	private void onIntentCommandNew()
	{
		Log.d("jack.chen","drawingservice.java onIntentCommandNew() ");
		if (this.uIController == null)
		{
			onIntentCommandStart();
			this.uIController.hideAll();
		}
		//this.uIController.showWhatsNew();
	}

	private void onIntentCommandStart()
	{
		Log.d("jack.chen","drawingservice.java onIntentCommandStart() ");
		if (this.uIController == null)
		{
			this.globalWindowManager = new GlobalWindowManager(this);
			this.uIController = new UIController(this.globalWindowManager);

			setButtonListeners();
			this.uIController.getNavWindow().setMoveHandles();
			return;
		}
		//StartTimer(5000);//jack.chen add 20150811
		this.uIController.restore();//jack.chen add 重新点击app图标显示圆盘ui
	}

	private void onIntentCommandStop()
	{
		Log.d("jack.chen","drawingservice.java onIntentCommandStop() ");
		if (this.uIController != null)
		{
			this.uIController.clear();

		}
		this.thisService.stopSelf();
	}

	private void onIntentCommandToggle()
	{
		Log.d("jack.chen","drawingservice.java onIntentCommandToggle() ");
		if (!(this.appToggled))
		{
			onIntentCommandStart();
			this.appToggled = true;
			return;
		}
		onIntentCommandHide();
		this.appToggled = false;
	}

	public DrawingService()
	{

	}
	private void setButtonListeners()
	{
		Log.d("jack.chen","drawingservice.java setButtonListeners() ");
		this.buttonWireing = new ButtonWireing(this.uIController);
		this.buttonWireing.setAllButtons(this.uIController.getNavWindow(), this.uIController.getBrushEditLayout(),    		
				this.uIController.getPaintView(),
				this.uIController.getVideoSrcFileLayout(),
				this.uIController.getHistoryLayout(),
				this.uIController.getOpenFileLayout(),
				this.uIController.getSaveFileLayout(),
				new Runnable()
		{
			public void run()
			{
				try
				{
					new IntentCreator(getApplicationContext()).getKillIntent().send();
					return;
				}
				catch (Exception localException)
				{
				}
			}
		});

	}

	/*
  Thread mThread = new Thread("InputDeviceReader") {
      public void run() {
          RawInputEvent ev = new RawInputEvent();
          while (true) {//开始消息读取循环

              {
            	  InputDevice di;
                  //本地方法实现，读取用户输入事件
                  readEvent(ev);
                  }


      }
     }*/

	public IBinder onBind(Intent paramIntent)
	{
		return null;
	}

	public void onConfigurationChanged(Configuration paramConfiguration)
	{
		Log.d("jack.chen","drawingservice.java onConfigurationChanged() ");
		super.onConfigurationChanged(paramConfiguration);
		//if (this.uIController != null)
			//this.uIController.changeOrientation(this.globalWindowManager.windowManager.getDefaultDisplay().getRotation());
	}


	public void StartTimer(long t)
	{
		Log.d("jack.chen","drawingservice.java StartTimer() ");
		timer = new Timer(true);

		task = new TimerTask(){  
			public void run() {  
				Log.d("jack.chen","drawingservice.java TimerTask() ");
				if (uIController != null)
				{
					if(uIController.getPaintStatus() == false 
							&& uIController.getBrushStatus()==false 
							&& uIController.getHistoryStatus()==false 
							&& uIController.getVideoSrcStatus()==false
							&& uIController.getOpenfileStatus()==false
							&& !data.getDownOrUpValue())
					{
						if(touch_flag == false && close_flag ==false)
						{
							Log.d("jack.chen","drawingservice.java TimerTask() touch_SendMsg.sendEmptyMessage(0) invisible ");
							touch_SendMsg.sendEmptyMessage(0);//invisible
						}
						touch_flag = false;
					}
				}
				else
				{
					Log.d("jack.chen","drawingservice.java TimerTask() uIController == null");
				}
			}  
		};  

		timer.schedule(task,t, t); //延时1000ms后执行，1000ms执行一次

	}
	public void onCreate()
	{
		//Toast.makeText(this, "Released by POPDA ( Persian OPDA ) NimA79 - A.l.i", 1).show();
		super.onCreate();
		Log.i("pizhu", "DrawingService.java onCreate");
		SendMsgToService(11,11);
		this.thisService = this;
		Notification n = new Notification();
		startForeground(1,n);
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(RECEIVE_ACTION);	
		mFilter.addAction(ANNOTATE_ACTION);	
		mFilter.addAction(HideCircle);
		registerReceiver(DataReceiverBroadcastReceiver, mFilter);
		//NotificationCreator localNotificationCreator = new NotificationCreator(getApplicationContext());
		//startForeground(localNotificationCreator.getID(), localNotificationCreator.getNotification());
		data = (GlobalData)this.getApplication();
		StartTimer(5000);
	}

	private BroadcastReceiver DataReceiverBroadcastReceiver = new BroadcastReceiver() {


		@Override
		public void onReceive(Context context, Intent intent) {
			//				Log.d(TAG, "Data onReceive!");
			Log.d("jack.chen","drawingservice.java onReceive() action="+intent.getAction());
			if(intent.getAction().equals(RECEIVE_ACTION)){
				//					Log.d(TAG, "Data onReceive!");
				RecType = intent.getIntExtra("type", 0);
				Log.d(TAG, "RecType == " + RecType);
				int temp = intent.getIntExtra("temp", 0);					
				Log.d(TAG, "temp == " + intent.getIntExtra("temp", 0));
				switch(RecType){					
				case (int)(0x8B & 0x0ff):
					if(uIController!=null)
						((TextView)uIController.getMainLayout().findViewById(R.id.temp)).setText(String.valueOf(temp)+"℃");
				break;
				}
			}
			if(intent.getAction().equals(ANNOTATE_ACTION)){	
				Log.d(TAG, String.valueOf(intent.getIntExtra("parameter", 0)));
				data.setValue(intent.getIntExtra("parameter", 0));
				if(intent.getIntExtra("parameter", 0)==1){//进入PC
					if(close_flag == false){
						SendMsgToService(11,11);//显示发android 
					}
					else{
						SendMsgToService(10,11);//隐藏发android pc
					}
				}
				else{
					SendMsgToService(11,11);//退出PC发android
				}
			}			
			if(intent.getAction().equals(HideCircle)){
				
				touch_SendMsg.sendEmptyMessage(0);
			}
			//				if(intent.getAction().equals(HideCircle)){
			//					int status = intent.getIntExtra("status", 0);
			//					Log.d(TAG, "status == " + status);
			//					if(status == 1){
			//				    	//((com.yongfu.floatwindow.circlemenu.CircleLayout)uIController.getMainLayout().findViewById(R.id.main_circle_layout)).setVisibility(8);
			//						MyAnimation.startAnimationOUT(level3, 500, 0);
			//					}
			//					else{
			//						//((com.yongfu.floatwindow.circlemenu.CircleLayout)uIController.getMainLayout().findViewById(R.id.main_circle_layout)).setVisibility(0);
			//						MyAnimation.startAnimationIN(level3, 500);
			//					}
			//					try {
			//						Thread.sleep(500);
			//					} catch (InterruptedException e) {
			//						// TODO Auto-generated catch block
			//						e.printStackTrace();
			//					}
			//				}				


		}
	};	

	public void restoreBrushButton(View paramView, boolean paramBoolean)
	{
		Log.d("jack.chen","drawingservice.java restoreBrushButton() ");
		setBackground(paramView, paramBoolean);
	}

	private void setBackground(View paramView, boolean paramBoolean)
	{
		Log.d("jack.chen","drawingservice.java setBackground() paramBoolean="+paramBoolean);
		if (paramBoolean)
		{
			//	      paramView.setBackgroundColor(ColorDefine.SELECTED_COLOR_COLOR);
			return;
		}
		paramView.setBackgroundColor(ColorDefine.BACKGROUND_COLOR);

	}  

	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
		unregisterReceiver(DataReceiverBroadcastReceiver);
	}	

	public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
	{
		Log.d("jack.chen","drawingservice.java onStartCommand() paramInt1="+paramInt1+" paramInt2="+paramInt2);
		super.onStartCommand(paramIntent, paramInt1, paramInt2);
		//onIntentCommandStart();

		if ((paramIntent != null) && (paramIntent.getExtras() != null))
		{
			String str = paramIntent.getExtras().getString(IntentCommands.command.toString());
			Log.d(TAG,"onStartCommand()="+ str);
			if (str != null)
				switchCommand(IntentCommands.valueOf(str));
		}
		try
		{
			//      hideNotifications();
			return 1;
		}
		catch (Exception localException)
		{
			Log.e(TAG, localException.getMessage(), localException);
		}
		return 1;
	}

	public void switchCommand(IntentCommands paramIntentCommands)
	{
		Log.d("jack.chen","DrawingService.java +switchCommand()="+paramIntentCommands.toString());
		//    if (paramIntentCommands == IntentCommands.stop)
		//      onIntentCommandStop();
		do
		{
			// return;
			if (paramIntentCommands == IntentCommands.start)
			{
				
				onIntentCommandStart();
				return;
			}
			if (paramIntentCommands == IntentCommands.hide)
			{
				onIntentCommandHide();
				return;
			}
			if (paramIntentCommands == IntentCommands.help)
			{
				onIntentCommandHelp();
				return;
			}
			if (paramIntentCommands == IntentCommands.whatsnew)
			{
				onIntentCommandNew();
				return;
			}
			if(paramIntentCommands == IntentCommands.touch )
			{  
				//StartTimer();
				if(close_flag)
				{
					touch_SendMsg.sendEmptyMessage(1);//visible
				}
				return;
			}
		}
		while (paramIntentCommands != IntentCommands.toggle);
		onIntentCommandToggle();
	}
	
}

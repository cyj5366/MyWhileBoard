package com.yongfu.floatwindow.window;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yongfu.floatwindow.R;
import com.yongfu.floatwindow.circlemenu.CircleLayout;
import com.yongfu.floatwindow.window.MyAnimation;
import com.yongfu.floatwindow.penandpaper.Settings;
import com.yongfu.floatwindow.paintview.ColorDefine;
import com.yongfu.floatwindow.paintview.PaintView;

public class NavWindowController extends BaseController
{
	public View.OnClickListener HDMI1ClickListener;  //jack.chen add 20150812n
	public View.OnClickListener HDMI2ClickListener;
	public View.OnClickListener HDMI3ClickListener;
	public View.OnClickListener VGAClickListener;
	
	
  public View.OnClickListener brushClickListener;
  public View.OnClickListener helpClickListener;
  public View.OnClickListener backClickListener;  
  public View.OnClickListener videoClickListener;
  public View.OnClickListener openClickListener;
  public View.OnClickListener saveClickListener;
  public View.OnClickListener centerClickListener;
  public View.OnClickListener historyClickListener;  
  private static final String TAG = "NavWindowController";  
  private final String hideText;
  private boolean isMinimized = false;
  FrameLayout mainLayout;
  private UIController minController;
  private int minWidth;
  public View.OnClickListener paintClickListener;
  private boolean[] save;
  private final String showText;
  private Object wasBrushShowing;
  private Object wasHelpShowing;
  private Object wasNavShowing;
  private Object wasNewShowing;
  private Object wasPaintShowing;
  private com.yongfu.floatwindow.circlemenu.CircleLayout level3;
  private static final String ShowOrHideCircle = "com.labwe.ShowOrHideCircle";
  private static final String HideCircle = "com.labwe.HideCircle";
  public NavWindowController(GlobalWindowManager paramGlobalWindowManager)
  {
	  
    super(paramGlobalWindowManager, R.dimen.nav_total_width, R.dimen.nav_total_height);
    Log.d("jack.chen","NavWindowController.java NavWindowController()");
    this.hideText = paramGlobalWindowManager.context.getResources().getString(R.string.button_hide);
    this.showText = paramGlobalWindowManager.context.getResources().getString(R.string.button_show);
    this.isShowing = true;
    setNavMinWidth();
    createMainControlls();
	IntentFilter mFilter = new IntentFilter();
	mFilter.addAction(ShowOrHideCircle);
	paramGlobalWindowManager.context.registerReceiver(DataReceiverBroadcastReceiver, mFilter);
  }

  

  
  private boolean is_paint = false;

  
  private void createMainControlls()
  {
	  Log.d("jack.chen","NavWindowController.java createMainControlls()");
    this.mainLayout = new FrameLayout(this.base.context);
    this.mainLayout.setBackgroundColor(0);
    this.base.layoutInflater.inflate(R.layout.circle_nav, this.mainLayout);
    this.layoutParams = this.base.setWindowLayoutParams(getWidth(), getHeight(), this.layoutParams);
//	level3 = (com.yongfu.floatwindow.circlemenu.CircleLayout)this.mainLayout.findViewById(R.id.main_circle_layout);

    
    
    setOnMoveListener(new BaseController.MoveListener() {
		
		@Override
		public void onMove(int paramInt1, int paramInt2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onDoneMoving(int paramInt1, int paramInt2) {
			// TODO Auto-generated method stub
			Settings.getInstance().setNavX(paramInt1);
	        Settings.getInstance().setNavY(paramInt2);
		}
	});
   
    this.base.windowManager.addView(this.mainLayout, this.layoutParams);
  }

  private void resetView()
  {
    this.base.showView(this.mainLayout, this.layoutParams, getWidth(), getHeight(), this.locX, this.locY);
  }

  private void setNavMinWidth()
  {
    this.minWidth = (int)this.base.context.getResources().getDimension(R.dimen.nav_min_width);
  }

  public void deleteSave()
  {
    this.save = null;
  }

  public View getMainView()
  {
    return this.mainLayout;
  }

  public boolean[] getSave()
  {
    return this.save;
  }

  public int getWidth()
  {
    return this.width;
  }

  protected void hide()
  {
    super.hide();
    this.base.setFocus(false);
    this.base.hideView(this.mainLayout, this.layoutParams);
  }

  public boolean isShowing()
  {
    return this.isShowing;
  }

  public void resetBrushButton()
  { 
    this.mainLayout.findViewById(R.id.editBrush).setBackgroundColor(0);
  }

  public void restoreTransperancy(boolean paramBoolean)
  {
    if (paramBoolean)
      this.mainLayout.setBackgroundColor(ColorDefine.BACKGROUND_COLOR);
    this.mainLayout.invalidate();
    this.base.windowManager.updateViewLayout(this.mainLayout, this.layoutParams);
  }
  
 
	private BroadcastReceiver DataReceiverBroadcastReceiver = new BroadcastReceiver() {


		@Override
		public void onReceive(Context context, Intent intent) {
//				Log.d(TAG, "Data onReceive!");
			
				if(intent.getAction().equals(ShowOrHideCircle)){
					Log.d("jack.chen","BaseController.java onreceive ShowOrHideCircle to Intent(HideCircle)");
//					int status = intent.getIntExtra("status", 0);
//					Log.d(TAG, "status == " + status);
//					if(status == 1){
				    	//((com.yongfu.floatwindow.circlemenu.CircleLayout)uIController.getMainLayout().findViewById(R.id.main_circle_layout)).setVisibility(8);
//						MyAnimation.startAnimationOUT(level3, 200, 0);
	     		        Intent hideCircle = new Intent(HideCircle);				
						context.sendBroadcast(hideCircle);
						
//					}
//					else{
//						//((com.yongfu.floatwindow.circlemenu.CircleLayout)uIController.getMainLayout().findViewById(R.id.main_circle_layout)).setVisibility(0);
////						MyAnimation.startAnimationIN(level3, 200);
//					}
				}				
				
				
		}
	};  
  
  

  public void setMoveHandles()
  {
	Log.d("jack.chen","NavWindowController.java setMoveHandles()");
	setMoveHandles(this.mainLayout.findViewById(R.id.HDMI1Button), this.mainLayout, true, -1, this.HDMI1ClickListener);//jack.chen add 20150812n
	setMoveHandles(this.mainLayout.findViewById(R.id.HDMI2Button), this.mainLayout, true, -1, this.HDMI2ClickListener);
	setMoveHandles(this.mainLayout.findViewById(R.id.HDMI3Button), this.mainLayout, true, -1, this.HDMI3ClickListener);
	setMoveHandles(this.mainLayout.findViewById(R.id.VGAButton), this.mainLayout, true, -1, this.VGAClickListener);
	
    setMoveHandles(this.mainLayout.findViewById(R.id.mainButton), this.mainLayout, false, -1, this.paintClickListener);
    setMoveHandles(this.mainLayout.findViewById(R.id.editBrush), this.mainLayout, false, -1, this.brushClickListener);
    setMoveHandles(this.mainLayout.findViewById(R.id.info), this.mainLayout, true, -1, this.helpClickListener);
    setMoveHandles(this.mainLayout.findViewById(R.id.BackButton), this.mainLayout, true, -1, this.backClickListener);
    
    
    
    setMoveHandles(this.mainLayout.findViewById(R.id.historyButton), this.mainLayout, true, -1, this.historyClickListener);
    setMoveHandles(this.mainLayout.findViewById(R.id.openFileButton), this.mainLayout, false, -1, this.openClickListener);
    setMoveHandles(this.mainLayout.findViewById(R.id.saveFileButton), this.mainLayout, true, -1, this.saveClickListener);
    setMoveHandles(this.mainLayout.findViewById(R.id.videoSrcButton), this.mainLayout, true, -1, this.videoClickListener);    

    
    setMoveHandles2(this.mainLayout.findViewById(R.id.centerButton), this.mainLayout, true, -1, null);
    this.base.setFocus(false);
    
    
  }

  protected void show()
  {
    super.show();
    Log.d("NAV", this.locX + " " + this.locY);
    this.base.showView(this.mainLayout, this.layoutParams, getWidth(), getHeight(), this.locX, this.locY);
    this.isMinimized = false;
    restoreTransperancy(false);
    this.base.setFocus(false);
  }
  
  public void setFocu_un()
  {
	  this.base.setFocus(false);
  }

  public void toggleMinimize(PaintView paramPaintView, UIController paramUIController)
  {
    if (paramUIController != null)
      this.minController = paramUIController;
    
    
    label52:
    {
        if (this.minController != null)
        {
          if (!(this.isMinimized))
            break label52;
          this.minController.restoreFromSave(this.save);
          this.save = null;
          this.minController.showPaint();
          this.isMinimized = false;
        }
    }
    this.save = this.minController.saveAllShowing();
   
    do
    {
     
       
      this.save[1] = true;
//      this.mainLayout.findViewById(R.id.mainButton).setBackgroundColor(ColorDefine.SELECTED_COLOR_COLOR);
      this.minController.setAll(false, true, false, false, false, false);
      paramPaintView.setTouchToggleNav();
      this.isMinimized = true;
    }
    while (Settings.getInstance().getScreenShotCount() > 5);
    
    
    
    
    Toast.makeText(this.base.context, this.base.context.getString(R.string.toast_min), 0).show();
    Settings.getInstance().incrScreenShotCount();
  }
}
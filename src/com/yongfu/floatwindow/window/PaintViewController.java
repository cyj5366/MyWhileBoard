package com.yongfu.floatwindow.window;



import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.yongfu.floatwindow.R;
import com.yongfu.floatwindow.paintview.BitmapPager;
import com.yongfu.floatwindow.paintview.PaintView;
import java.util.List;

public class PaintViewController extends BaseController
{
  public static final int DEGREE_ROTATION = 90;
  String activityUsed;
  BitmapPager pager;
  PaintView paintView;

  boolean isShow = false;
  
  public PaintViewController(GlobalWindowManager paramGlobalWindowManager, UIController paramUIController)
  {
    super(paramGlobalWindowManager, R.dimen.dummy, R.dimen.dummy);
    this.paintView = new PaintView(paramGlobalWindowManager.context, paramUIController);
    
    this.layoutParams = paramGlobalWindowManager.setWindowLayoutParams(0, 0, this.layoutParams);
    paramGlobalWindowManager.windowManager.addView(this.paintView, this.layoutParams);
    this.pager = new BitmapPager(paramGlobalWindowManager.fullScreenX, paramGlobalWindowManager.fullScreenY);
 
  }

  private String getTopActivity()
  {
    return ((ActivityManager.RunningTaskInfo)((ActivityManager)this.base.context.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName();
  }

  public void clearPaint()
  {
    if (this.isShowing)
      this.paintView.clearPaint();
  }

  public View getMainView()
  {
    return this.paintView;
  }

  protected void hide()
  {
    super.hide();
    this.base.hideView(this.paintView, this.layoutParams);
    this.pager.saveCurrentBitmap();
    isShow = false;
    this.base.setFocus(false);
  }

  public void setScreenOrientation(int paramInt)
  {
    this.pager.setWidthAndHeight(this.base.actuallFullScreenX, this.base.actuallFullScreenY);
    this.paintView.changeOrienation(paramInt);
    if (this.isShowing)
      show();
  }

  public boolean getShowStatus()
  {
	  return isShow;
  }
  protected void show()
  {
    super.show();
    this.base.showView(this.paintView, this.layoutParams);
    isShow = true;
    this.paintView.useNewBitmap(this.pager.getBitmapForApp(getTopActivity()));
  }
}

package com.yongfu.floatwindow.window;



import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class GlobalWindowManager
{
  static final int FULL_SCREEN = -1;
  public int actuallFullScreenX;
  public int actuallFullScreenY;
  public Context context;
  public int fullScreenX;
  public int fullScreenY;
  public int heightDP;
  public LayoutInflater layoutInflater;
  private final HashMap<View, Point> previousLocations;
  public int statusBarHeight = 0;
  public int widthDP;
  public WindowManager windowManager;
  private final ArrayList<BaseController> windowsForFocus;

  public GlobalWindowManager(Context paramContext)
  {
	  Log.d("jack.chen","GlobalWindowManager.java  GlobalWindowManager() ");  
    this.context = paramContext;
    this.previousLocations = new HashMap();
    this.windowsForFocus = new ArrayList(3);
    this.windowManager = ((WindowManager)paramContext.getSystemService("window"));
    this.layoutInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    getStatusBarHeight(paramContext);
    setFullScreenSize();
  }

  private void getStatusBarHeight(Context paramContext)
  {
	  Log.d("jack.chen","GlobalWindowManager.java  getStatusBarHeight() ");  
    this.statusBarHeight = (int)paramContext.getResources().getDimension(2131165188);
  }

  private void setFocusFlag(boolean paramBoolean, View paramView, WindowManager.LayoutParams paramLayoutParams)
  {
	  
	  Log.d("jack.chen","GlobalWindowManager.java  setFocusFlag() paramBoolean="+paramBoolean);
	  //WindowManager.LayoutParams.FLAG_FULLSCREEN
    if (!paramBoolean)
      paramLayoutParams.flags = (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ^ paramLayoutParams.flags);
    else
    {
    	paramLayoutParams.flags = (0x8 | paramLayoutParams.flags);
    }
    //while (true)
    //{
     // paramLayoutParams.flags = (WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | paramLayoutParams.flags);
      this.windowManager.updateViewLayout(paramView, paramLayoutParams);
     // return;
     // 
    //}
  }

  void addFocusWindow(BaseController paramBaseController)
  {
	  Log.d("jack.chen","GlobalWindowManager.java  addFocusWindow() ");  
    this.windowsForFocus.add(paramBaseController);
  }

  void hideView(View paramView, WindowManager.LayoutParams paramLayoutParams)
  {
	Log.d("jack.chen","GlobalWindowManager.java  hideView()");
    this.previousLocations.put(paramView, new Point(paramLayoutParams.x, paramLayoutParams.y));
    paramLayoutParams.width = 1;
    paramLayoutParams.height = 1;
//    paramLayoutParams.y = (-this.fullScreenY);
    this.windowManager.updateViewLayout(paramView, paramLayoutParams);
  }

  void setFocus(boolean paramBoolean)
  {
	  Log.d("jack.chen","GlobalWindowManager.java  setFocus() paramBoolean="+paramBoolean);
	
    Iterator localIterator = this.windowsForFocus.iterator();
    while (true)
    {
      if (!(localIterator.hasNext()))
        return;
      BaseController localBaseController = (BaseController)localIterator.next();
      setFocusFlag(paramBoolean, localBaseController.getMainView(), localBaseController.getLayoutParams());
    }
  }


  public void setFullScreenSize()
  {
	  Log.d("jack.chen","GlobalWindowManager.java  setFullScreenSize()");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    this.windowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    this.fullScreenX = localDisplayMetrics.widthPixels;
    this.fullScreenY = (localDisplayMetrics.heightPixels);// - this.statusBarHeight);
    float f = localDisplayMetrics.density;
    this.widthDP = (int)(this.fullScreenX / f);
    this.heightDP = (int)(this.fullScreenY / f);
    this.actuallFullScreenX = this.fullScreenX;
    this.actuallFullScreenY = (this.fullScreenY);// + this.statusBarHeight);
  }

  WindowManager.LayoutParams setWindowLayoutParams(int paramInt1, int paramInt2, WindowManager.LayoutParams paramLayoutParams)
  {
	  Log.d("jack.chen","GlobalWindowManager.java  setWindowLayoutParams() paramInt1="+paramInt1+" paramInt2="+paramInt2);
    if (paramLayoutParams == null)
    {
      if (paramInt1 == -1)
        paramInt1 = this.fullScreenX;
      if (paramInt2 == -1)
        paramInt2 = this.fullScreenY;
      paramLayoutParams = new WindowManager.LayoutParams(paramInt1, paramInt2, 2002, 40, 1);
      //paramLayoutParams.windowAnimations = 17432587;
      paramLayoutParams.type = 2002;  //type是关键，这里的2002表示系统级窗口，你也可以试试2003。
      paramLayoutParams.format = PixelFormat.RGBA_8888;
      
      
      /**
       *这里的flags也很关键
       *代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
       *40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
       */
      paramLayoutParams.flags = 40;
      paramLayoutParams.flags |=LayoutParams.FLAG_NOT_TOUCH_MODAL;
      
      paramLayoutParams.gravity = 0;
    }
    return paramLayoutParams;
  }

  void showView(View paramView, WindowManager.LayoutParams paramLayoutParams)
  {
	  Log.d("jack.chen","GlobalWindowManager.java  1 showView()");
    showView(paramView, paramLayoutParams, this.fullScreenX, this.fullScreenY);
  }

  void showView(View paramView, WindowManager.LayoutParams paramLayoutParams, int paramInt1, int paramInt2)
  {
	  Log.d("jack.chen","GlobalWindowManager.java  2 showView()");
    Object localObject = this.previousLocations.get(paramView);
    int i = 0;
    int j = 0;
    if (localObject != null)
    {
      i = ((Point)this.previousLocations.get(paramView)).x;
      j = ((Point)this.previousLocations.get(paramView)).y;
    }
    showView(paramView, paramLayoutParams, paramInt1, paramInt2, i, j);
  }

  void showView(View paramView, WindowManager.LayoutParams paramLayoutParams, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
	  Log.d("jack.chen","GlobalWindowManager.java  3 showView() paramInt1="+paramInt1+" paramInt2="+paramInt2+" paramInt3="+paramInt3+" paramInt4="+paramInt4);
    paramLayoutParams.width = paramInt1;
    paramLayoutParams.height = paramInt2;
    paramLayoutParams.x = paramInt3;
    paramLayoutParams.y = paramInt4;
    this.windowManager.updateViewLayout(paramView, paramLayoutParams);
    
  }
}
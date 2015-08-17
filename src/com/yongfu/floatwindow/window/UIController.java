package com.yongfu.floatwindow.window;



import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yongfu.floatwindow.R;
import com.yongfu.floatwindow.penandpaper.Settings;
import com.yongfu.floatwindow.paintview.PaintView;

public class UIController
{
  private static final String TAG = "Window Controller";
  private final GlobalWindowManager base;
  private BrushEditController brushEditController;
  private boolean closedNav = false;

  private boolean isHidden = false;
  private NavWindowController navWindowController;
  private PaintViewController paintViewController;
  private OpenViewController openfileController;
  private VideoSrcViewController videosrcController;
  private HistoryViewController historyController;  
  private boolean wasBrushShowing = false;
  private boolean wasHelpShowing = false;
  private boolean wasNavShowing = true;
  private boolean wasNewShowing = false;
  private boolean wasPaintShowing = false;
  private boolean wasVideoSrcShowing = false;
  private boolean washistoryShowing = false;
  private boolean wasOpenfileShowing = false;
  private GlobalData data;
  //private WhatsNewController whatsNewController;

  public UIController(GlobalWindowManager paramGlobalWindowManager)
  {
	  Log.d("jack.chen","UIController.java UIController()"); 
    this.base = paramGlobalWindowManager;
    this.paintViewController = new PaintViewController(paramGlobalWindowManager, this);
    //this.helpWindowController = new HelpWindowController(paramGlobalWindowManager, this);
    this.navWindowController = new NavWindowController(paramGlobalWindowManager);
    
    this.videosrcController =  new VideoSrcViewController(paramGlobalWindowManager,this.navWindowController.width, this.navWindowController.height, this.navWindowController);

    this.historyController =  new HistoryViewController(paramGlobalWindowManager,this.navWindowController.width, this.navWindowController.height, this.navWindowController);
    
    this.openfileController = new OpenViewController(paramGlobalWindowManager,this.navWindowController.width, this.navWindowController.height, this.navWindowController);
  //  saveController = new SaveViewController(paramGlobalWindowManager,this.navWindowController.width, this.navWindowController.height, this.navWindowController);
    this.brushEditController = new BrushEditController(paramGlobalWindowManager, this.navWindowController.width, this.navWindowController.height, this.navWindowController);
    
    //this.whatsNewController = new WhatsNewController(paramGlobalWindowManager, this);
    this.navWindowController.moveWindow(Settings.getInstance().getNavX(), Settings.getInstance().getNavY());

    paramGlobalWindowManager.setFocus(false);
    data = (GlobalData)this.base.context.getApplicationContext();  
  }

  private void setAll(boolean paramBoolean)
  {
	  Log.d("jack.chen","UIController.java setAll() paramBoolean="+paramBoolean);
    setAll(paramBoolean, paramBoolean, paramBoolean, paramBoolean, paramBoolean, paramBoolean);
  }

  public void changeOrientation(int paramInt)
  {
	Log.d("jack.chen","UIController.java changeOrientation()");   
    this.base.setFullScreenSize();
    this.paintViewController.setScreenOrientation(paramInt);
    if (!(this.isHidden))
    {
      hideAll();
      restore();
    }
  }

  public void clear()
  {
    Log.d("Window Controller", "Clear called");
    this.base.setFocus(false);
    hideAll();
    if (this.navWindowController.mainLayout != null)
      this.base.windowManager.removeView(this.navWindowController.mainLayout);
    if (this.brushEditController.editBrushLayout != null)
      this.base.windowManager.removeView(this.brushEditController.editBrushLayout);
    if (this.paintViewController.paintView != null)
      this.base.windowManager.removeView(this.paintViewController.paintView);
    if(this.openfileController.openviewLayout!= null)
    {    	
    	this.base.windowManager.removeView(this.openfileController.openviewLayout);
    }    
    if(this.videosrcController.videosrcviewLayout!= null)
    {    	
    	this.base.windowManager.removeView(this.videosrcController.videosrcviewLayout);
    }   
    if(this.historyController.historyViewlayout!= null)
    {    	
    	this.base.windowManager.removeView(this.historyController.historyViewlayout);
    }   
    //if(saveController.saveviewLayout != null)
    //{
     //  this.base.windowManager.removeView(this.saveController.saveviewLayout);
   // }
    //if (this.helpWindowController.webPageLayout != null)
    //  this.base.windowManager.removeView(this.helpWindowController.webPageLayout);
    //if (this.whatsNewController.webPageLayout != null)
      //this.base.windowManager.removeView(this.whatsNewController.webPageLayout);
  }

  public void clearPaint()
  {
	  Log.d("jack.chen","UIController.java clearPaint()"); 
    this.paintViewController.clearPaint();
  }
  
  public void ShowNavWindow()
  {
	  Log.d("jack.chen","UIController.java ShowNavWindow()"); 
	  navWindowController.show();
  }
  public void HideNavWindow()
  {
	  Log.d("jack.chen","UIController.java HideNavWindow()");
	  navWindowController.hide();
	  if(this.paintViewController.getShowStatus()){
	       this.paintViewController.setShowing(false);
	       data.setBoolValue(false);	       
	  }
	  if(this.openfileController.isShowing){
	       this.openfileController.setShowing(false);
	  }
	  if(this.brushEditController.isShowing){
	       this.brushEditController.setShowing(false);
	  }	  
	  if(this.videosrcController.isShowing){
	       this.videosrcController.setShowing(false);
	  }
  }

  public boolean closeBrush()
  {
	  Log.d("jack.chen","UIController.java closeBrush()");  
    this.brushEditController.close();
    return false;
  }


  public void set_un_focus()
  {
	  Log.d("jack.chen","UIController.java set_un_focus() ");
	  brushEditController.setFocuse_un();
  }
  public boolean closeOpenfile()
  {	  
	  Log.d("jack.chen","UIController.java closeOpenfile() ");
	  this.openfileController.close();
	  return false;
  }

  
  public boolean closeVideoSrcfile()
  {	  
	  Log.d("jack.chen","UIController.java closeVideoSrcfile() ");
	  this.videosrcController.close();
	  return false;
  }  
  
  public boolean closeHistoryfile()
  {	  
	  Log.d("jack.chen","UIController.java closeHistoryfile() ");
	  this.historyController.close();
	  return false;
  }    
  
  
  public boolean CloseSaveFile()
  {
	  Log.d("jack.chen","UIController.java CloseSaveFile() ");
	 // this.saveController.close();
	  return false;
  }

  public View getBrushEditLayout()
  {
	  Log.d("jack.chen","UIController.java getBrushEditLayout() ");
    return this.brushEditController.editBrushLayout;
  }
  
  public View getOpenFileLayout()
  {
	  Log.d("jack.chen","UIController.java getOpenFileLayout() ");
	  return this.openfileController.openviewLayout;
  }
  
  public View getVideoSrcFileLayout()
  {
	  Log.d("jack.chen","UIController.java getVideoSrcFileLayout() ");
	  return this.videosrcController.videosrcviewLayout;
  }  
  
  public View getHistoryLayout()
  {
	  Log.d("jack.chen","UIController.java getHistoryLayout() ");
	  return this.historyController.historyViewlayout;
  }   
  
  public View getSaveFileLayout()
  {
	  return null;
	 // return this.saveController.saveviewLayout;
  }

  public FrameLayout getMainLayout()
  {
	  Log.d("jack.chen","UIController.java getMainLayout() ");
    return this.navWindowController.mainLayout;
  }

  public NavWindowController getNavWindow()
  {
	  Log.d("jack.chen","UIController.java getNavWindow() ");
    return this.navWindowController;
  }

  public View getPaintLayout()
  {
	  Log.d("jack.chen","UIController.java getPaintLayout() ");
    return this.paintViewController.paintView;
  }
  public boolean getPaintStatus()
  {
	  Log.d("jack.chen","UIController.java getPaintStatus() ");
	  return paintViewController.getShowStatus();
  }
  public boolean getBrushStatus()
  {
	  Log.d("jack.chen","UIController.java getBrushStatus() ");
	  return this.brushEditController.isShowing;
  }

  public PaintView getPaintView()
  {
	  Log.d("jack.chen","UIController.java getPaintView() ");
    return this.paintViewController.paintView;
  }
  
  public boolean getOpenfileStatus()
  {
	  Log.d("jack.chen","UIController.java getOpenfileStatus() ");
	  return this.openfileController.isShowing;
  }  
  
  public boolean getVideoSrcStatus()
  {
	  Log.d("jack.chen","UIController.java getVideoSrcStatus() ");
	  return this.videosrcController.isShowing;
  }  
  
  public boolean getHistoryStatus()
  {
	  Log.d("jack.chen","UIController.java getHistoryStatus() ");
	  return this.historyController.isShowing;
  }  

  public void hideAll()
  {
	Log.d("jack.chen","UIController.java hideAll()"); 
    if (!(this.isHidden))
    {
      saveAllShowing();
      setAll(false);
      this.base.setFocus(false);
      this.isHidden = true;
    }
  }

  public void moveNav(BaseController paramBaseController, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
	Log.d("jack.chen","UIController.java moveNav() paramInt1="+paramInt1+" paramInt2="+paramInt2+" paramInt3="+paramInt3+" paramInt4="+paramInt4); 
    int i = this.navWindowController.layoutParams.x;
    int j = this.navWindowController.layoutParams.y;
    int k = this.navWindowController.height / 2;
    int l = this.navWindowController.width / 2;
    Rect localRect1 = new Rect(i - l, j - k, i + l, j + k);
    Rect localRect2 = new Rect(paramInt3 - paramInt1 / 2, paramInt4 - paramInt2 / 2, paramInt3 + paramInt1 / 2, paramInt4 + paramInt2 / 2);
    this.base.setFullScreenSize();
    if (Rect.intersects(localRect1, localRect2))
    {
      int i1 = this.base.fullScreenY / 2 - paramInt4 + paramInt2 / 2 - k * 2;
      if (i1 < 0)
        paramBaseController.moveWindow(0, paramInt4 + i1);
      this.brushEditController.hide();
      this.videosrcController.hide();
      this.historyController.hide();
      this.navWindowController.resetBrushButton();
      this.navWindowController.moveWindow(paramInt3, paramInt4 + paramInt2 / 2 + this.navWindowController.height / 2);
      this.closedNav = false;
    }
  }

  public void moveNavBack()
  {
	  Log.d("jack.chen","UIController.java moveNavBack() ");
    if (this.closedNav)
      restore();
  }

  public void restore()
  {
	  Log.d("jack.chen","UIController.java restore()"); 
	 label41:
	 {
	  if (this.isHidden)
	    {
	      this.isHidden = false;
	      if (this.navWindowController.getSave() == null)
	        break label41;
	      restoreFromSave(this.navWindowController.getSave());
	      this.navWindowController.deleteSave();
	    }
	  }
	  setAll(true, this.wasPaintShowing, this.wasBrushShowing, this.wasVideoSrcShowing, this.washistoryShowing, this.wasOpenfileShowing);
	  	
    return;
  }

  public void restoreFromSave()
  {
	  Log.d("jack.chen","UIController.java restoreFromSave() ");
    setAll(this.wasNavShowing, this.wasPaintShowing, this.wasBrushShowing, this.wasVideoSrcShowing, this.washistoryShowing, this.wasOpenfileShowing);//  this.wasNewShowing);
  }

  public void restoreFromSave(boolean[] paramArrayOfBoolean)
  {
	  Log.d("jack.chen","UIController.java restoreFromSave() "); 
    setAll(paramArrayOfBoolean[0], paramArrayOfBoolean[1], paramArrayOfBoolean[2], paramArrayOfBoolean[3], paramArrayOfBoolean[4], paramArrayOfBoolean[5]);
  }

  public void restoreTransparency()
  {
	  Log.d("jack.chen","UIController.java restoreTransparency()"); 
    this.navWindowController.restoreTransperancy(false);
  }

  public boolean[] saveAllShowing()
  {
	  Log.d("jack.chen","UIController.java saveAllShowing()"); 
    this.wasPaintShowing = this.paintViewController.isShowing;
    this.wasNavShowing = this.navWindowController.isShowing;
    this.wasBrushShowing = this.brushEditController.isShowing;
    this.wasVideoSrcShowing = this.videosrcController.isShowing;
    this.washistoryShowing = this.historyController.isShowing;
    this.wasOpenfileShowing = this.openfileController.isShowing;    
   // this.wasHelpShowing = this.helpWindowController.isShowing;
   // this.wasNewShowing = this.whatsNewController.isShowing;
    boolean[] arrayOfBoolean = new boolean[6];
    arrayOfBoolean[0] = this.wasNavShowing;
    arrayOfBoolean[1] = this.wasPaintShowing;
    arrayOfBoolean[2] = this.wasBrushShowing;
    arrayOfBoolean[3] = this.wasVideoSrcShowing;
    arrayOfBoolean[4] = this.washistoryShowing;
    arrayOfBoolean[5] = this.wasOpenfileShowing; 
    return arrayOfBoolean;
  }

  public void setAll(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
  {
	  Log.d("jack.chen","UIController.java setAll() paramBoolean="+paramBoolean1+ " paramBoolean2="+paramBoolean2+
			  " paramBoolean3="+paramBoolean3+" paramBoolean4="+paramBoolean4+" paramBoolean5="+paramBoolean5+" paramBoolean6="+paramBoolean6); 
    this.navWindowController.setShowing(paramBoolean1);
    this.paintViewController.setShowing(paramBoolean2);
    this.brushEditController.setShowing(paramBoolean3);
    this.videosrcController.setShowing(paramBoolean4);    
    this.historyController.setShowing(paramBoolean5);    
    this.openfileController.setShowing(paramBoolean6);
//    this.helpWindowController.setShowing(paramBoolean4);
    //this.whatsNewController.setShowing(paramBoolean5);
    this.base.setFocus(false);
  }

  public void showAll()
  {
	  Log.d("jack.chen","UIController.java showAll()"); 
    if (this.isHidden)
    {
      this.isHidden = false;
      setAll(true);
    }
  }

 // public void showHelp()
  //{
   // this.helpWindowController.setShowing(true);
 // }

  public void showPaint()
  {
	  Log.d("jack.chen","UIController.java showPaint()"); 
    this.paintViewController.show();    
  }

 // public void showWhatsNew()
  //{
   // this.whatsNewController.setShowing(true);
  //}

  public boolean toggleBrushEditShowing()
  {
	  Log.d("jack.chen","UIController.java toggleBrushEditShowing()"); 
    return this.brushEditController.toggle();
  }
  
  public boolean toggleOpenFileShowing()
  {
	  Log.d("jack.chen","UIController.java toggleOpenFileShowing()"); 
    return this.openfileController.toggle();
  }
  
  public boolean toggleVideoSrcShowing()
  {
	  Log.d("jack.chen","UIController.java toggleVideoSrcShowing()"); 
    return this.videosrcController.toggle();
  }
  
  public boolean toggleHistoryShowing()
  {
	  Log.d("jack.chen","UIController.java toggleHistoryShowing()"); 
    return this.historyController.toggle();
  }   
  
  public boolean toggleSaveShowing()
  {
	  Log.d("jack.chen","UIController.java toggleSaveShowing()"); 
	  return false;
	  //this.saveController.toggle();
  }

 // public boolean toggleHelpWindow()
 // {
   // return this.helpWindowController.toggle();
  //}

  public void toggleMinimize(PaintView paramPaintView)
  {
	  Log.d("jack.chen","UIController.java toggleMinimize()"); 
    this.navWindowController.toggleMinimize(paramPaintView, this);
  }

  public boolean togglePaintWindow()
  {
	  Log.d("jack.chen","UIController.java togglePaintWindow()"); 
    return this.paintViewController.toggle();
  }
}
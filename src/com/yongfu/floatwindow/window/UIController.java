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
    setAll(paramBoolean, paramBoolean, paramBoolean, paramBoolean, paramBoolean, paramBoolean);
  }

  public void changeOrientation(int paramInt)
  {
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
    this.paintViewController.clearPaint();
  }
  
  public void ShowNavWindow()
  {
	  navWindowController.show();
  }
  public void HideNavWindow()
  {
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
    this.brushEditController.close();
    return false;
  }


  public void set_un_focus()
  {
	  brushEditController.setFocuse_un();
  }
  public boolean closeOpenfile()
  {	  
	  this.openfileController.close();
	  return false;
  }

  
  public boolean closeVideoSrcfile()
  {	  
	  this.videosrcController.close();
	  return false;
  }  
  
  public boolean closeHistoryfile()
  {	  
	  this.historyController.close();
	  return false;
  }    
  
  
  public boolean CloseSaveFile()
  {
	 // this.saveController.close();
	  return false;
  }

  public View getBrushEditLayout()
  {
    return this.brushEditController.editBrushLayout;
  }
  
  public View getOpenFileLayout()
  {
	  return this.openfileController.openviewLayout;
  }
  
  public View getVideoSrcFileLayout()
  {
	  return this.videosrcController.videosrcviewLayout;
  }  
  
  public View getHistoryLayout()
  {
	  return this.historyController.historyViewlayout;
  }   
  
  public View getSaveFileLayout()
  {
	  return null;
	 // return this.saveController.saveviewLayout;
  }

  public FrameLayout getMainLayout()
  {
    return this.navWindowController.mainLayout;
  }

  public NavWindowController getNavWindow()
  {
    return this.navWindowController;
  }

  public View getPaintLayout()
  {
    return this.paintViewController.paintView;
  }
  public boolean getPaintStatus()
  {
	  return paintViewController.getShowStatus();
  }
  public boolean getBrushStatus()
  {
	  return this.brushEditController.isShowing;
  }

  public PaintView getPaintView()
  {
    return this.paintViewController.paintView;
  }
  
  public boolean getOpenfileStatus()
  {
	  return this.openfileController.isShowing;
  }  
  
  public boolean getVideoSrcStatus()
  {
	  return this.videosrcController.isShowing;
  }  
  
  public boolean getHistoryStatus()
  {
	  return this.historyController.isShowing;
  }  

  public void hideAll()
  {
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
    if (this.closedNav)
      restore();
  }

  public void restore()
  {
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
    setAll(this.wasNavShowing, this.wasPaintShowing, this.wasBrushShowing, this.wasVideoSrcShowing, this.washistoryShowing, this.wasOpenfileShowing);//  this.wasNewShowing);
  }

  public void restoreFromSave(boolean[] paramArrayOfBoolean)
  {
    setAll(paramArrayOfBoolean[0], paramArrayOfBoolean[1], paramArrayOfBoolean[2], paramArrayOfBoolean[3], paramArrayOfBoolean[4], paramArrayOfBoolean[5]);
  }

  public void restoreTransparency()
  {
    this.navWindowController.restoreTransperancy(false);
  }

  public boolean[] saveAllShowing()
  {
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
    this.paintViewController.show();    
  }

 // public void showWhatsNew()
  //{
   // this.whatsNewController.setShowing(true);
  //}

  public boolean toggleBrushEditShowing()
  {
    return this.brushEditController.toggle();
  }
  
  public boolean toggleOpenFileShowing()
  {
    return this.openfileController.toggle();
  }
  
  public boolean toggleVideoSrcShowing()
  {
    return this.videosrcController.toggle();
  }
  
  public boolean toggleHistoryShowing()
  {
    return this.historyController.toggle();
  }   
  
  public boolean toggleSaveShowing()
  {
	  return false;
	  //this.saveController.toggle();
  }

 // public boolean toggleHelpWindow()
 // {
   // return this.helpWindowController.toggle();
  //}

  public void toggleMinimize(PaintView paramPaintView)
  {
    this.navWindowController.toggleMinimize(paramPaintView, this);
  }

  public boolean togglePaintWindow()
  {
    return this.paintViewController.toggle();
  }
}
package com.yongfu.floatwindow.window;





import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import com.yongfu.floatwindow.R;

public class OpenViewController extends BaseController
{
  private boolean closedByBrushEditor = false;
  public FrameLayout openviewLayout;
  private final int navHeight;
  private final WindowManager.LayoutParams navLayoutParams;
  private final int navWidth;
  private final NavWindowController navWindow;

  public OpenViewController(GlobalWindowManager paramGlobalWindowManager, int paramInt1, int paramInt2, NavWindowController paramNavWindowController)
  {
    super(paramGlobalWindowManager, R.dimen.brush_settings_total_width, R.dimen.display_show_height);
    this.navLayoutParams = paramNavWindowController.getLayoutParams();
    paramNavWindowController.setOnMoveListener(new BaseController.MoveListener() {
		
		@Override
		public void onMove(int paramInt1, int paramInt2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onDoneMoving(int paramInt1, int paramInt2) {
			// TODO Auto-generated method stub
			
		}
	});
    
    this.navHeight = paramInt2;
    this.navWidth = paramInt1;
    this.navWindow = paramNavWindowController;
    createOpenFIlePanel();
  }

  private void createOpenFIlePanel()
  {
    this.openviewLayout = new FrameLayout(this.base.context);
    this.openviewLayout.setBackgroundColor(0);
    this.base.layoutInflater.inflate(R.layout.open_file, this.openviewLayout);
    
    this.layoutParams = this.base.setWindowLayoutParams(1, 1, this.layoutParams);
    layoutParams.type = 2002;  //type是关键，这里的2002表示系统级窗口，你也可以试试2003。
    layoutParams.format = 1;
    /**
     *这里的flags也很关键
     *代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
     *40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
     */
    layoutParams.flags = 40;
    
   
    this.base.windowManager.addView(this.openviewLayout, this.layoutParams);
  }

  public void checkBrushEditPosition(int paramInt1, int paramInt2)
  {
    if (this.isShowing)
    {
      int i = paramInt2 - this.height / 2 - this.navHeight / 2;
      if (paramInt2 - this.navHeight / 2 + this.base.fullScreenY / 2 < this.height)
        i = paramInt2 + this.height / 2 + this.navHeight / 2;
      this.base.showView(this.openviewLayout, this.layoutParams, this.width, this.height, paramInt1, i);
    }
  }

  public boolean checkHeight()
  {
    int i = 2 * this.height + this.navHeight;
    return (this.base.fullScreenY < i);
  }

  public void close()
  {
    Log.d("CLOSE", "CLOSE");
    hide();
    this.closedByBrushEditor = true; 
    toggleNav();
  }

  public View getMainView()
  {
    return this.openviewLayout;
  }

  protected void hide()
  {
    super.hide();
    this.base.hideView(this.openviewLayout, this.layoutParams);
  }

  protected void show()
  {
    super.show();
    this.closedByBrushEditor = false;
    toggleNav();
    if (!(checkHeight()))
    {
      checkBrushEditPosition(this.navLayoutParams.x, this.navLayoutParams.y);
      return;
    }
    this.base.showView(this.openviewLayout, this.layoutParams, this.width, this.height, 0, -this.base.fullScreenY / 2);
  }

  public void toggleNav()
  {
    //if ((checkHeight()) || (this.closedByBrushEditor))
   // {
    //  if ((!(this.isShowing)) || (!(this.navWindow.isShowing)))
        //return;
      //this.navWindow.hide();
     
      
   // }
   
      //do
      //{
     // else if ((!(checkHeight())) && (this.closedByBrushEditor) && (!(this.navWindow.isShowing)) && (this.isShowing))
      //{
          this.navWindow.show();
       // label82: return;
      //}
      //while ((this.isShowing) || (this.navWindow.isShowing));
      
      //this.navWindow.show();
    //  this.closedByBrushEditor = false;
      //}
    }
  
}
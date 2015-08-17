package com.yongfu.floatwindow.window;


import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.yongfu.floatwindow.R;
import com.yongfu.floatwindow.paintview.ColorDefine;
import com.yongfu.floatwindow.penandpaper.IntentCommands;
import com.yongfu.floatwindow.penandpaper.IntentCreator;
import com.yongfu.floatwindow.window.BaseController.MoveListener;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.IGpioControl;


public abstract class BaseController
{
  private static final int CLICK_TIMEOUT_MS = 200;
  private static final int MIN_DRAG_DISTANCE_PX = 10;
  protected static final int NO_RES = -1;
  protected final GlobalWindowManager base;
  protected int height;
  protected boolean isShowing = false;
  protected WindowManager.LayoutParams layoutParams;
  protected int locX = 0;
  protected int locY = 0;
  private ArrayList<MoveListener> moveListeners;
  protected int width;
  IntentCreator intentCreator;
  private IGpioControl  m_IGpioControl;
  private boolean bmove = false;
  private boolean bopen = false;  
  private static final String ShowOrHideCircle = "com.labwe.ShowOrHideCircle";
  public FrameLayout editBrushLayout;
  private GlobalData data;
  public BaseController(GlobalWindowManager paramGlobalWindowManager, int paramInt1, int paramInt2)
  {
    this.base = paramGlobalWindowManager;
    setDimentions(paramInt1, paramInt2);
    this.moveListeners = new ArrayList<MoveListener>();
    paramGlobalWindowManager.addFocusWindow(this);
    this.intentCreator = new IntentCreator(paramGlobalWindowManager.context);
	data = (GlobalData)this.base.context.getApplicationContext();
  }

  private void notifyMove(int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.moveListeners.iterator();
    while (true)
    {
      if (!(localIterator.hasNext()))
        return;
      ((MoveListener)localIterator.next()).onMove(paramInt1, paramInt2);
    }
  }

  private void notifyMoveDone(int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.moveListeners.iterator();
    while (true)
    {
      if (!(localIterator.hasNext()))
        return;
      ((MoveListener)localIterator.next()).onDoneMoving(paramInt1, paramInt2);
    }
  }

  private void setDimentions(int paramInt1, int paramInt2)
  {
    this.width = (int)this.base.context.getResources().getDimension(paramInt1);
    this.height = (int)this.base.context.getResources().getDimension(paramInt2);
  }

  protected void fixLayoutParams(WindowManager.LayoutParams paramLayoutParams, int paramInt1, int paramInt2)
  {
    int i = this.base.fullScreenY / 2 - paramInt2 / 2;
    int j = this.base.fullScreenY / -2 + paramInt2 / 2;
    int k = this.base.fullScreenX / -2 + paramInt1 / 2;
    int l = this.base.fullScreenX / 2 - paramInt1 / 2;
    
    
    label111: 
    {
    if (paramLayoutParams.y > i)
    {
      paramLayoutParams.y = i;
      if (paramLayoutParams.x <= l)
        break label111;
      paramLayoutParams.x = l;
    }
    }
    
   
    
    /*do
      while (true)
      {
        do
          return;
        while (paramLayoutParams.y >= j);
        paramLayoutParams.y = j;
      }
    while (paramLayoutParams.x >= k);
    paramLayoutParams.x = k;*/
  }

  public int getHeight()
  {
    return this.height;
  }

  public WindowManager.LayoutParams getLayoutParams()
  {
    return this.layoutParams;
  }

  public abstract View getMainView();

  public int getWidth()
  {
    return this.width;
  }

  protected void hide()
  {
	setBackground = false;
    is_paint_pressed = false;  
    this.isShowing = false;
    this.base.setFocus(false);
  }

  public void moveWindow(int paramInt1, int paramInt2)
  {
    this.layoutParams.x = paramInt1;
    this.layoutParams.y = paramInt2;
    this.locX = paramInt1;
    this.locY = paramInt2;
    this.base.windowManager.updateViewLayout (getMainView(), this.layoutParams);
  }

  public void restoreTransperancy(boolean paramBoolean)
  {
  }

  protected void setMoveHandles(View paramView1, View paramView2, boolean paramBoolean)
  {
    setMoveHandles(paramView1, paramView2, paramBoolean, -1, null);
  }

  
  private void sendTouchIntent()
  {
    try
    {
      this.intentCreator.getPendingIntent(IntentCommands.touch.toString()).send();
      return;
    }
    catch (Exception localException)
    {
    }
  }
	public void SendMsgToService(int state, int io) {
		m_IGpioControl = IGpioControl.Stub.asInterface(ServiceManager.getService("gpio"));
		try
		{
			m_IGpioControl.gpioControl(state, io);
		}
		catch (RemoteException e)
		{
			
		}
	}

  
  boolean is_paint_pressed = false;
  boolean setBackground = false;
  protected void setMoveHandles(View paramView1, View paramView2, boolean paramBoolean, int paramInt, View.OnClickListener paramOnClickListener)
  {
    if (paramView1 == null)
      return;
    paramView1.setClickable(true);
    final boolean displayflag = paramBoolean;
   
    final View.OnClickListener jj = paramOnClickListener;
    paramView1.setOnTouchListener(new View.OnTouchListener() {
    	private float deltaXt = 0F;
        private float deltaYt = 0F;
        private long lastTimeClick = System.currentTimeMillis();
        private float prevX = -1.0F;
        private float prevY = -1.0F;

       
        
		
		private void onTouchMove(View v, MotionEvent paramMotionEvent)
	      {
	        base.setFocus(true);
	        if ((this.prevX > -1.0F) && (this.prevY > -1.0F) && (paramMotionEvent.getRawX() > 0F) && (paramMotionEvent.getRawY() > 0F))
	        {
	          int i = (int)(paramMotionEvent.getRawX() - this.prevX);
	          int j = (int)(paramMotionEvent.getRawY() - this.prevY);
	          this.deltaXt += Math.abs(i);
	          this.deltaYt += Math.abs(j);
	          if ((Math.abs(this.deltaXt) > 20.0F) || (Math.abs(this.deltaYt) > 20.0F))
	          {
	            if ( (setBackground))
	            {
	              v.setBackgroundColor(0);
	              restoreTransperancy(false);
	              setBackground = false;
	            }
	            WindowManager.LayoutParams localLayoutParams1 = layoutParams;
	            localLayoutParams1.x = (i + localLayoutParams1.x);
	            WindowManager.LayoutParams localLayoutParams2 = layoutParams;
	            localLayoutParams2.y = (j + localLayoutParams2.y);
	            fixLayoutParams(layoutParams, getWidth(), getHeight());
	            locX = layoutParams.x;
	            locY = layoutParams.y;
	            base.windowManager.updateViewLayout(v, layoutParams);
	          }
	        }
	        this.prevX = paramMotionEvent.getRawX();
	        this.prevY = paramMotionEvent.getRawY();
	        base.setFocus(false);
	      }

	      private void onTouchUp(View v, MotionEvent paramMotionEvent)
	      {
	    	  
	    	  this.lastTimeClick = System.currentTimeMillis();
	    	  //if ((Math.abs(this.deltaXt) < 10.0F) && (Math.abs(this.deltaYt) < 10.0F) && (System.currentTimeMillis() - this.lastTimeClick > 200L))
	          //{
	            
	         // }
	    	  
	    	  IntentCreator intentCreator = new IntentCreator(v.getContext());
				try {
					intentCreator.getPendingIntent(IntentCommands.touch.toString()).send();
				} catch (CanceledException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	  if(displayflag)
	    	  {
	    		  setBackground = false;
//	        	  v.setBackgroundColor(ColorDefine.BACKGROUND_COLOR);
	    	  }
	    	  base.setFocus(false);
	    	  if(jj != null && v.getId() == R.id.saveFileButton)
        	  {
            	jj.onClick(v);
        	  }
	    	  
	    	  
	       /* Log.d("DELTA", Math.abs(this.deltaXt) + " " + Math.abs(this.deltaYt));
	        if ((Math.abs(this.deltaXt) < 10.0F) && (Math.abs(this.deltaYt) < 10.0F) && (System.currentTimeMillis() - this.lastTimeClick > 200L))
	        {
	          if (this.val$onClick != null)
	            this.val$onClick.onClick(this.val$handles);
	          this.lastTimeClick = System.currentTimeMillis();
	        }
	        if ((this.val$feedback) && (this.setBackground))
	        {
	          if (this.val$resource != -1)
	            break label197;
	          this.val$handles.setBackgroundColor(0);
	        }
	        while (true)
	        {
	          this.setBackground = false;
	          this.prevX = -1.0F;
	          this.prevY = -1.0F;
	          this.this$0.restoreTransperancy(false);
	          BaseController.access$1(this.this$0, this.this$0.locX, this.this$0.locY);
	          this.this$0.base.setFocus(false);
	          return;
	          label197: this.val$handles.setBackgroundResource(this.val$resource);
	        }*/
	      }
	      

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			int action = event.getAction();
			if( (v.getId() == R.id.openFileButton 
					|| v.getId() == R.id.saveFileButton) 
					&& is_paint_pressed == false)
			{
				return false;
			}
			BaseController.this.sendTouchIntent();
			switch(action)
			{
			case MotionEvent.ACTION_MOVE:
				//v.getId() == R.id.
				//onTouchMove(v, event);
				break;
			case MotionEvent.ACTION_DOWN:
				 base.setFocus(true);
		          this.deltaXt = 0F;
		          this.deltaYt = 0F;       

		          if (jj!= null)
		          {
		        	  if(v.getId() != R.id.saveFileButton)
		        	  {
		            	jj.onClick(v);
		        	  }
		            	if (!setBackground)
				        {
//				            v.setBackgroundColor(ColorDefine.SELECTED_COLOR_COLOR);
				            setBackground = true;
				            if(v.getId() == R.id.mainButton)
				            {
				            	is_paint_pressed = true;

				            }
				            
				        }
				        else
				        {
//				              v.setBackgroundColor(ColorDefine.BACKGROUND_COLOR);
				        	  setBackground = false;
				        	  if(v.getId() == R.id.mainButton)
					          {
				        		  is_paint_pressed = false;				        		  
					          }				        	  
				        }
		          }
		           this.lastTimeClick = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_UP:
				onTouchUp(v, event);
				break;
			
			}
	       
			return false;
		}
	});  
  }

  protected void setMoveHandles2(View paramView1, final View paramView2, boolean paramBoolean, int paramInt, View.OnClickListener paramOnClickListener)
  {
    if (paramView1 == null)
      return;
    paramView1.setClickable(true);    
    final View mainview = paramView2;    
    final View.OnClickListener jj = paramOnClickListener;
    paramView1.setOnTouchListener(new View.OnTouchListener() {
    	private float deltaXt = 0F;
        private float deltaYt = 0F;        
        private float prevX = -1.0F;
        private float prevY = -1.0F;		
		private void onTouchMove(View v, MotionEvent paramMotionEvent)
	      {
	        base.setFocus(true);
	        if ((this.prevX > -1.0F) && (this.prevY > -1.0F) && (paramMotionEvent.getRawX() > 0F) && (paramMotionEvent.getRawY() > 0F))
	        {
	          int i = (int)(paramMotionEvent.getRawX() - this.prevX);
	          int j = (int)(paramMotionEvent.getRawY() - this.prevY);
	          this.deltaXt += Math.abs(i);
	          this.deltaYt += Math.abs(j);
	          if ((Math.abs(this.deltaXt) > 20.0F) || (Math.abs(this.deltaYt) > 20.0F))
	          {
	        	bmove = true;
	            WindowManager.LayoutParams localLayoutParams1 = layoutParams;
	            localLayoutParams1.x = (i + localLayoutParams1.x);
	            WindowManager.LayoutParams localLayoutParams2 = layoutParams;
	            localLayoutParams2.y = (j + localLayoutParams2.y);
	            fixLayoutParams(layoutParams, getWidth(), getHeight());
	            locX = layoutParams.x;
	            locY = layoutParams.y;
	            base.windowManager.updateViewLayout(mainview, layoutParams);
				setButtonEnable(false);
	          }
	          else{
	        	  
	          }
	        	  
	        }
	        this.prevX = paramMotionEvent.getRawX();
	        this.prevY = paramMotionEvent.getRawY();
	        base.setFocus(false);
	      }
	      
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			int action = event.getAction();

			switch(action)
			{
			case MotionEvent.ACTION_MOVE:
				//v.getId() == R.id.
				onTouchMove(v, event);
				break;
			case MotionEvent.ACTION_DOWN:
				deltaXt = 0F;
		        deltaYt = 0F;
		        this.prevX = event.getRawX();
		        this.prevY = event.getRawY();
				base.setFocus(false);
				data.setDownOrUpValue(true);
				IntentCreator intentCreator = new IntentCreator(v.getContext());
				try {
					intentCreator.getPendingIntent(IntentCommands.touch.toString()).send();
				} catch (CanceledException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				break;
			case MotionEvent.ACTION_UP:
				//onTouchUp(v, event);
//				if(jj != null && v.getId() == R.id.saveFileButton)
//	        	  {
				if(!bmove){					
					if(!bopen){		

	     		        Intent HideCircle = new Intent(ShowOrHideCircle);
//						Bundle bundle = new Bundle();
//						bundle.putInt("status", 1);
//						HideCircle.putExtras(bundle);						
						v.getContext().sendBroadcast(HideCircle);
						}
					else{
	     		        Intent HideCircle = new Intent(ShowOrHideCircle);
//						Bundle bundle = new Bundle();
//						bundle.putInt("status", 2);
//						HideCircle.putExtras(bundle);						
						v.getContext().sendBroadcast(HideCircle);
					}						
					bopen = !bopen;					
	        	  }
				else{
					setButtonEnable(true);
				}
				  base.setFocus(false);
				  this.prevX = -1.0F;
			      this.prevY = -1.0F;
			      deltaXt = 0F;
				  deltaYt = 0F;
				  bmove = false;
				  data.setDownOrUpValue(false);				  
				break;
			}
			return false;
		}
	}); 
  }
  
  
  public void setButtonEnable(boolean enable)
  {
	  ((com.yongfu.floatwindow.circlemenu.CircleImageView)getMainView().findViewById(R.id.mainButton)).setEnabled(enable);
	  ((com.yongfu.floatwindow.circlemenu.CircleImageView)getMainView().findViewById(R.id.editBrush)).setEnabled(enable);
	  ((com.yongfu.floatwindow.circlemenu.CircleImageView)getMainView().findViewById(R.id.videoSrcButton)).setEnabled(enable);
	  ((com.yongfu.floatwindow.circlemenu.CircleImageView)getMainView().findViewById(R.id.BackButton)).setEnabled(enable);
	  ((com.yongfu.floatwindow.circlemenu.CircleImageView)getMainView().findViewById(R.id.info)).setEnabled(enable);
	  ((com.yongfu.floatwindow.circlemenu.CircleImageView)getMainView().findViewById(R.id.historyButton)).setEnabled(enable);
	  ((com.yongfu.floatwindow.circlemenu.CircleImageView)getMainView().findViewById(R.id.openFileButton)).setEnabled(enable);
	  ((com.yongfu.floatwindow.circlemenu.CircleImageView)getMainView().findViewById(R.id.saveFileButton)).setEnabled(enable);					

  } 
  

  public void setOnMoveListener(MoveListener paramMoveListener)
  {
    this.moveListeners.add(paramMoveListener);
  }

  public void setShowing(boolean paramBoolean)
  {	  
    if (paramBoolean == this.isShowing)
      return;
    this.isShowing = paramBoolean;
    if (paramBoolean)
    {
      show();
    }
    else
    {
      hide();
    }
  }

  protected void show()
  {
    this.isShowing = true;
    this.base.setFocus(false);
  }

  public boolean toggle()
  {
    boolean bool = false;
    if (this.isShowing)
    {
      bool = false;
    }
    else
    {
    	bool = true;
    }
    //while (true)
    //{
      setShowing(bool);
      return this.isShowing;
    //  bool = true;
    //}
  }

  public static abstract interface MoveListener
  {
    public abstract void onDoneMoving(int paramInt1, int paramInt2);

    public abstract void onMove(int paramInt1, int paramInt2);
  }
}
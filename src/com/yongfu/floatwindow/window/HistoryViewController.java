

package com.yongfu.floatwindow.window;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.PendingIntent.CanceledException;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongfu.floatwindow.R;
import com.yongfu.floatwindow.paintview.ColorDefine;
import com.yongfu.floatwindow.penandpaper.IntentCommands;
import com.yongfu.floatwindow.penandpaper.IntentCreator;

public class HistoryViewController extends BaseController{
  
  private boolean closedByHistoryApkEditor = false;
  public FrameLayout historyViewlayout;
  private ViewGroup viewgroup;
  private View groupItem;
  private ScrollView mScrollView;
  private final int navHeight;
  private final WindowManager.LayoutParams navLayoutParams;
  private final int navWidth;
  private final NavWindowController navWindow;
  private static final int NUM_BUTTONS = 8;  
  private static int MAX_RECENT_TASKS = 12;    // allow for some discards
  private static int repeatCount = 12;//保证上面两个值相等
  private float deltaXt = 0F;
  private float deltaYt = 0F;
    
  private float prevX = -1.0F;
  private float prevY = -1.0F;
  private float curX = -1.0F;
  private float curY = -1.0F;

//  private boolean bmove = false;
//  private boolean bopen = false;   
  private List<HashMap<String,Object>> appInfos = new ArrayList<HashMap<String, Object>>(); 
  

  public HistoryViewController(GlobalWindowManager paramGlobalWindowManager, int paramInt1, int paramInt2, NavWindowController paramNavWindowController)
  {
    super(paramGlobalWindowManager, R.dimen.history_apk_width, R.dimen.history_apk_height);
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
    reloadButtons();
    createHistoryViewLayoutPanel();

  }


  private void createHistoryViewLayoutPanel()
  {
    this.historyViewlayout = new FrameLayout(this.base.context);
//    this.historyViewlayout.setBackgroundColor(0);
    this.base.layoutInflater.inflate(R.layout.history_apk, this.historyViewlayout);   
    this.layoutParams = this.base.setWindowLayoutParams(1, 1, this.layoutParams);
    viewgroup = (ViewGroup)this.historyViewlayout.findViewById(R.id.historygrouplayout);
    mScrollView = (ScrollView)this.historyViewlayout.findViewById(R.id.ScrollView1);
    mScrollView.setOnTouchListener(new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			if(arg1.getAction()==MotionEvent.ACTION_MOVE){
				//Toast.makeText(arg0.getContext(), "MOVE", Toast.LENGTH_SHORT).show();
			}
			return false;
		}
	});
    addhistoryButtons();
    layoutParams.type = 2002;  //type是关键，这里的2002表示系统级窗口，你也可以试试2003。
    layoutParams.format = 1;
    /**
     *这里的flags也很关键
     *代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
     *40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
     */
    layoutParams.flags = 40;
    
//		LayoutInflater mInflater = LayoutInflater.from(this);
//		View dialogView = mInflater.inflate(R.layout.choose_dialog, null);
//
//		mDialog.setContentView(dialogView);
//		mDialog.setCanceledOnTouchOutside(true);
//		mDialog.show();
    this.base.windowManager.addView(this.historyViewlayout, this.layoutParams); 
    
  } 
  
  private void addhistoryButtons() {
	    viewgroup.removeAllViews();
	    for(int i = 0; i < appInfos.size(); i++){
	    	groupItem = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.choose_dialog_detail_info, null);
	    	ImageView img = (ImageView)groupItem.findViewById(R.id.imageView1);
	    	TextView text = (TextView)groupItem.findViewById(R.id.textView1);
	    	text.setText(appInfos.get(i).get("title").toString());
	    	img.setBackgroundDrawable((Drawable)appInfos.get(i).get("icon")); 
			Intent singleIntent = (Intent) appInfos.get(i).get("tag");
			groupItem.setTag(singleIntent);
	    	groupItem.setPadding(10, 10, 10, 10);    	
			groupItem.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub					
					Intent intent = (Intent)v.getTag();
		            if (intent != null) {
		                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
		                try {
		                	v.getContext().startActivity(intent);
		                } catch (ActivityNotFoundException e) {
		                    Log.w("Recent", "Unable to launch recent task", e);
		                }
		            }
				}
			});	 
	    	viewgroup.addView(groupItem);

	    }

  }
  
	private void onTouchMove(View v, MotionEvent paramMotionEvent)
    {
      base.setFocus(true);
      if ((this.prevX > -1.0F) && (this.prevY > -1.0F) && (paramMotionEvent.getRawX() > 0F) && (paramMotionEvent.getRawY() > 0F))
      {
        int i = (int)(paramMotionEvent.getRawX() - this.prevX);
        int j = (int)(paramMotionEvent.getRawY() - this.prevY);
        this.deltaXt += Math.abs(i);
        this.deltaYt += Math.abs(j);
        if ((Math.abs(this.deltaXt) > 5.0F) || (Math.abs(this.deltaYt) > 5.0F))
        {
 //     	bmove = true;
          WindowManager.LayoutParams localLayoutParams1 = layoutParams;
          localLayoutParams1.x = (i + localLayoutParams1.x);
          WindowManager.LayoutParams localLayoutParams2 = layoutParams;
          localLayoutParams2.y = (j + localLayoutParams2.y);
          fixLayoutParams(layoutParams, getWidth(), getHeight());
          locX = layoutParams.x;
          locY = layoutParams.y;
          base.windowManager.updateViewLayout(this.historyViewlayout, layoutParams);
        }
        else{
     	  
        }
      	  
      }
      this.prevX = paramMotionEvent.getRawX();
      this.prevY = paramMotionEvent.getRawY();
      base.setFocus(false);
    }  
  
  /**
   * 核心方法，加载最近启动的应用程序
   * 注意：这里我们取出的最近任务为  MAX_RECENT_TASKS + 1个，因为有可能最近任务中包好Launcher2。
   * 这样可以保证我们展示出来的  最近任务 为 MAX_RECENT_TASKS 个
   */
  private void reloadButtons() {
	  appInfos.clear();
  	//得到包管理器和activity管理器
      final Context context = this.base.context;
      final PackageManager pm = context.getPackageManager();
      final ActivityManager am = (ActivityManager)
              context.getSystemService(Context.ACTIVITY_SERVICE);
      //从ActivityManager中取出用户最近launch过的 MAX_RECENT_TASKS + 1 个，以从早到晚的时间排序，
      //注意这个   0x0002,它的值在launcher中是用ActivityManager.RECENT_IGNORE_UNAVAILABLE
      //但是这是一个隐藏域，因此我把它的值直接拷贝到这里
      final List<ActivityManager.RecentTaskInfo> recentTasks =
              am.getRecentTasks(MAX_RECENT_TASKS + 1, 0x0002);

      //这个activity的信息是我们的launcher
      ActivityInfo homeInfo = 
          new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
                  .resolveActivityInfo(pm, 0);
      int numTasks = recentTasks.size();
      for (int i = 0; i < numTasks && (i < MAX_RECENT_TASKS); i++) {
      	HashMap<String, Object> singleAppInfo = new HashMap<String, Object>();//当个启动过的应用程序的信息
          final ActivityManager.RecentTaskInfo info = recentTasks.get(i);

          Intent intent = new Intent(info.baseIntent);
          if (info.origActivity != null) {
              intent.setComponent(info.origActivity);
          }
          //TODO 测试用，无意义代码
          String currentInfo = "PackageName==" + intent.getComponent().getPackageName() + ",ClassName==" + intent.getComponent().getClassName();
          if (homeInfo != null) {
              if (homeInfo.packageName.equals(
                      intent.getComponent().getPackageName())
                      && homeInfo.name.equals(
                              intent.getComponent().getClassName())) {
              	MAX_RECENT_TASKS = MAX_RECENT_TASKS + 1;
                  continue;
              }
          }
          //设置intent的启动方式为 创建新task()【并不一定会创建】
          intent.setFlags((intent.getFlags()&~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                  | Intent.FLAG_ACTIVITY_NEW_TASK);
          //获取指定应用程序activity的信息(按我的理解是：某一个应用程序的最后一个在前台出现过的activity。)
          final ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
          if (resolveInfo != null) {
              final ActivityInfo activityInfo = resolveInfo.activityInfo;
              final String title = activityInfo.loadLabel(pm).toString();
              Drawable icon = activityInfo.loadIcon(pm);

              if (!title.equals("Recent apps.") && !title.equals("Draw Over Apps") && title != null && title.length() > 0 && icon != null) {
              	singleAppInfo.put("title", title);
              	singleAppInfo.put("icon", icon);
              	singleAppInfo.put("tag", intent);
              	appInfos.add(singleAppInfo);
              }
              
          }
          
      }
      MAX_RECENT_TASKS = repeatCount;
  }  

  
  /**
	 * gridview的适配器
	 * @author yanbin
	 *
	 */
	private class MyAppAdapter implements ListAdapter{

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public int getCount() {
			return appInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return appInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		/**
		 * 自定义view
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater mInflater = LayoutInflater.from(historyViewlayout.getContext());
			View infoView = mInflater.inflate(R.layout.choose_dialog_detail_info, null);
			ImageView mImageView = (ImageView) infoView.findViewById(R.id.imageView1);
			TextView mTextView = (TextView) infoView.findViewById(R.id.textView1);
			String title = (String) appInfos.get(position).get("title");
			Drawable icon = (Drawable) appInfos.get(position).get("icon");
			Intent singleIntent = (Intent) appInfos.get(position).get("tag");
			infoView.setTag(singleIntent);
			mImageView.setImageDrawable(icon);
			mTextView.setText(title);
	//		infoView.setOnClickListener(new SingleAppClickListener());
			return infoView;
		}

		@Override
		public int getItemViewType(int position) {
			return 0;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public boolean isEnabled(int position) {
			return false;
		}
	}  
  

  public void checkBrushEditPosition(int paramInt1, int paramInt2)
  {
    if (this.isShowing)
    {
      int i = paramInt2 - this.height / 2 - this.navHeight / 2;
      if (paramInt2 - this.navHeight / 2 + this.base.fullScreenY / 2 < this.height)
        i = paramInt2 + this.height / 2 + this.navHeight / 2;
      this.base.showView(this.historyViewlayout, this.layoutParams, this.width, this.height, paramInt1, i);
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
    this.closedByHistoryApkEditor = true; 

    toggleNav();
  }

  public View getMainView()
  {
    return this.historyViewlayout;
  }

  protected void hide()
  {
    super.hide();
    this.base.hideView(this.historyViewlayout, this.layoutParams);
  }

  protected void show()
  {
    super.show();
    reloadButtons();
    addhistoryButtons();
    this.closedByHistoryApkEditor = false;
    toggleNav();
    if (!(checkHeight()))
    {
      checkBrushEditPosition(this.navLayoutParams.x, this.navLayoutParams.y);
      return;
    }
    this.base.showView(this.historyViewlayout, this.layoutParams, this.width, this.height, 0, -this.base.fullScreenY / 2);
  }

  public void setFocuse_un()
  {
	  this.navWindow.setFocu_un();
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
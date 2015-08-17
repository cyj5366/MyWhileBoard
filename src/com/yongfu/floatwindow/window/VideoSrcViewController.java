package com.yongfu.floatwindow.window;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
//import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.os.ServiceManager;
//import android.os.SystemProperties;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
//import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;



//import com.android.server.tv.DataBaseDeskImpl;

import com.yongfu.floatwindow.window.ChangeInputSource;
//import com.mstar.android.MKeyEvent;
//import com.mstar.android.tv.TvAtscChannelManager;
//import com.mstar.android.tv.TvChannelManager;
//import com.mstar.android.tv.TvCommonManager;
//import com.mstar.android.tv.TvPictureManager;
//import com.mstar.android.tv.TvPipPopManager;
//import com.mstar.android.tvapi.common.PictureManager;
//import com.mstar.android.tvapi.common.TvManager;
//import com.mstar.android.tvapi.common.exception.TvCommonException;
//import com.mstar.android.tvapi.common.vo.Enum3dType;
//import com.mstar.android.tvapi.common.vo.EnumFirstServiceInputType;
//import com.mstar.android.tvapi.common.vo.EnumFirstServiceType;
//import com.mstar.android.tvapi.common.vo.EnumPipModes;
//import com.mstar.android.tvapi.common.vo.EnumScalerWindow;
//import com.mstar.android.tvapi.common.vo.EnumThreeDVideoDisplayFormat;
//import com.mstar.android.tvapi.common.vo.PanelProperty;
//import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
//import com.mstar.android.tvapi.common.vo.VideoWindowType;
import com.yongfu.floatwindow.R;

public class VideoSrcViewController extends BaseController
{
    private static final String TAG = "VideoSrcViewController";

//    private DisplayManager mDisplayManager;
//
//    private static final String STR_STATUS_NONE = "0";
//
//    private static final String STR_STATUS_SUSPENDING = "1";
//
//    private static final int SLEEP_TIME = 50;
//
//    private static final int RETRY_COUNT = 40; // retry for 2s
//
//    private ProgressDialog waitDialog;
//
//    private static boolean isCheckSNOKTheadRunning = false;
//
//
//
//    private static final int SET_WIN_SCALE = 4;
//
//	private static final String ACTION = "com.labwe.service";
//	
//    private boolean isPause = false;
//
//    final float W_1920 = 1920f;
//
//    final float H_1080 = 1080f;
//
//    private List<View> mListView;
//
////    private MstarViewPager mViewPager;
//
//    private List<ResolveInfo> listApps;
//
//    LayoutInflater mInflater;
//
////    LinearLayout mcontent;
//
////    private View view1;
//
//    //private View view2;
//
//    //private View view3;
//
//    //private GridView mGridView1;
//
//    //private GridView mGridView2;
//
//    //private GridView mGridView3;
//
//   // private List<ImageView> idxImageViews;
//
//   // private ImageView pageidx1;
//
//   // private ImageView pageidx2;
//
//   // private ImageView pageidx3;
//
//    public static Context msContext;
//
//    //public GridPageAdapter mGridPageAdapter1;
//
//   // public GridPageAdapter mGridPageAdapter2;
//
//   // public GridPageAdapter mGridPageAdapter3;
//
//    public static final int PAGE_APP_NUM = 15;
//
//    private int itemidxpage2 = -1;
//
//    private int itemidx = -1;
//
//    private int currentpageidx = 1;
//
//    //private static final int PAGE1 = 0;
//    private static final int PAGE2 = 0;
//
//    //private static final int PAGE3 = 2;
//
//    private static final int PAGE_IDLE = 0;
//
//    private static final int PAGE_SLIP = 1;
//
//    private static final int PAGE_SCROLL = 1;
//
//    private static final int PAGE_READYTOSCROLL = 2;
// 
//	byte[] mBuffer;
//
//
//    private static final int ICON_FRAME_NUM = 35;
//
//    private static final int FRAME_DURATION = 80; // ms
//
//    ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
//
//
//
//    private Boolean bLoadIcon = false;
//
//    private Boolean bLoadBreak = false;
//
//    private Boolean bAnimationPause = false;
//
//    private Boolean bExitThread = false;
//
//    private Boolean bSystemShutdown = false;
//
//    // ////////////////for tv///////////////////////////
//    SurfaceView surfaceView = null;
//
//    android.view.SurfaceHolder.Callback callback;
//
//    // private WindowManager wm;
//    // LayoutParams surfaceParams;
//    private boolean PowerOn = true;
//
//    private boolean createsurface = false;
//    
//    private boolean isSourceNeedSwitch = false;
//    
//    private ThreeDModeObserver observer_ThreeDMode = null;
//    
//    private EnumInputSource toChangeInputSource = EnumInputSource.E_INPUT_SOURCE_NONE;
//
//    private InputSourceThread inputSourceThread = new InputSourceThread();
//
//    private Boolean bSync = true;
//
//    private static final int SCALE_NONE = 0;
//
//    private static final int SCALE_FULL = 1;
//
//    private static final int SCALE_SMALL = 2;
//
//    private int fullScale = SCALE_NONE;
//
//    // private MiniDataBase minidatabase;
//    private DataBaseDeskImpl minidatabase;
//
//    private static final String ThirdPartyDtvEnv = "thirdpartydtv"; // colin@20130418
//                                                                    // add for
//                                                                    // ddi
//
//    private int ThirdPartyDtvValue = 0;// colin@20130418 add for ddi
//
//    // ////////////////for tv end///////////////////////////
//    // //////////////////for wallpaper///////////////////////////////
//    private static boolean mWallpaperChecked;
//
//    // ////////////////////////////////////////////////////////////
//    // /////////////////////anim//////////////////////////////////
//    private Animation scale;
//
//    // ///////////////////////////////////////////////////////////
//    private int focusIdx;
//
//    private static final int TOTALICON = 15;
//
//    // ////////////appWidget////////////////
//    private AppWidgetHost mAppWidgetHost;
//
//    private AppWidgetManager mAppWidgetManager;
//
//    private static final int APPWIDGET_HOST_ID = 1024;
//
//    private int appWidgetId1;
//
//    private int appWidgetId2;
//
//    private int appWidgetId3;
//
//    private AppWidgetProviderInfo appWidgetInfo1 = null;
//
//    private AppWidgetProviderInfo appWidgetInfo2 = null;
//
//    private AppWidgetProviderInfo appWidgetInfo3 = null;
//
//    private AbsoluteLayout.LayoutParams ablp1;
//
//    private AbsoluteLayout.LayoutParams ablp2;
//
//    private AbsoluteLayout.LayoutParams ablp3;
//
//    private AbsoluteLayout absoluteLayout;
//
//
//    private int continuousLoadIcon = 0;
//
//    // control status bar hide and show
//    private boolean statusBarIsVisible = false;
//
//    private static final int HIDE_STATUS_BAR = 0;
//
//    public Handler myHandler = new StatusBarHandler();
//
//    private TvCommonManager commonService;
//
//    private TvChannelManager tvChannelManager;
//    
//    private TvAtscChannelManager tvAtscChannelManager;
//    
//    private boolean mixKey = true;
//
//    private boolean hasTVClick = false;
//
//    //private List<Button> dockBtns = null;
//
//    private final String FILE_FLY_LAUNCH = "com.jrm.filefly.action";
//
//    private final int E_PIP_MODE_PIP = 0;
//
//    private final int E_PIP_MODE_POP = 1; 	
	
	
	
	
	
	
  private boolean closedByVideoSrcEditor = false;
  public FrameLayout videosrcviewLayout;
  private ViewGroup viewgroup;
  private View groupItem;
  private final int navHeight;
  private final WindowManager.LayoutParams navLayoutParams;
  private final int navWidth;
  private final NavWindowController navWindow;
  private Dialog mDialog;
  private static final int NUM_BUTTONS = 8;  
  private static int MAX_RECENT_TASKS = 12;    // allow for some discards
  private static int repeatCount = 12;//保证上面两个值相等
  private List<HashMap<String,Object>> appInfos = new ArrayList<HashMap<String, Object>>();
  private ChangeInputSource changeInputSource;

  
  public VideoSrcViewController(GlobalWindowManager paramGlobalWindowManager, int paramInt1, int paramInt2, NavWindowController paramNavWindowController)
  {
    super(paramGlobalWindowManager, R.dimen.history_apk_width, R.dimen.video_src_height);
    this.navLayoutParams = paramNavWindowController.getLayoutParams();

    paramNavWindowController.setOnMoveListener(new BaseController.MoveListener() {
		
		@Override
		public void onMove(int paramInt1, int paramInt2) {
			// TODO Auto-generated method stub
//			Settings.getInstance().setNavX(paramInt1);		
		}
		
		@Override
		public void onDoneMoving(int paramInt1, int paramInt2) {
			// TODO Auto-generated method stub
			
		}
	});
    
    this.navHeight = paramInt2;
    this.navWidth = paramInt1;
    this.navWindow = paramNavWindowController;

    createVideoSrcEditPanel();

//    changeInputSource = new ChangeInputSource(this.base.context);
//    changeInputSource.init1219(this.base.context);    


//    msContext.getContentResolver().registerContentObserver(Uri.parse("content://mstar.tv.usersetting/threedvideomode/inputsrc/"), true, observer_ThreeDMode);
   

    
    
////    mInflater = getLayoutInflater();
////
////    view1 = mInflater.inflate(R.layout.sending01010101, null);
////    mListView.add(view1);
//    //mGridView1.requestFocus(0);

  }

  private void createVideoSrcEditPanel()
  {
    this.videosrcviewLayout = new FrameLayout(this.base.context);///    this.videosrcviewLayout.setBackgroundColor(0);
    this.base.layoutInflater.inflate(R.layout.video_source, this.videosrcviewLayout);   
    this.layoutParams = this.base.setWindowLayoutParams(1, 1, this.layoutParams);
    viewgroup = (ViewGroup)videosrcviewLayout.findViewById(R.id.viewgrouplayout);
    final ArrayList<String> textlist = new ArrayList<String>();
    textlist.add("录播");
    textlist.add("ATV");
    textlist.add("CVBS");
    textlist.add("YPBPR1");
    textlist.add("HDM1");
    textlist.add("数字广播");
    textlist.add("HIDMI3");
    textlist.add("电脑");
    textlist.add("视频会议");
    textlist.add("VGA");   
    ArrayList<Integer> imglst = new ArrayList<Integer>();
    imglst.add(R.drawable.src_1);
    imglst.add(R.drawable.src_2);
    imglst.add(R.drawable.src_3);
    imglst.add(R.drawable.src_4);    
    imglst.add(R.drawable.src_5);
    imglst.add(R.drawable.src_6);
    imglst.add(R.drawable.src_7);
    imglst.add(R.drawable.src_8);
    imglst.add(R.drawable.src_9);    
    imglst.add(R.drawable.src_10);  
    for(int i = 0; i < textlist.size(); i++){
    	groupItem = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.choose_dialog_detail_info, null);
    	ImageView img = (ImageView)groupItem.findViewById(R.id.imageView1);
    	TextView text = (TextView)groupItem.findViewById(R.id.textView1);
    	text.setText(textlist.get(i));
    	img.setImageResource(imglst.get(i));    	
    	groupItem.setPadding(10, 10, 10, 10);
    	final int position = i;
    	groupItem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		        changeInputSource.switchVideoSrc(position); 
			}
		});
    	viewgroup.addView(groupItem);
    }
    layoutParams.type = 2002;  //type是关键，这里的2002表示系统级窗口，你也可以试试2003。
    layoutParams.format = 1;
    /**
     *这里的flags也很关键
     *代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
     *40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
     */
    layoutParams.flags = 40;

    this.base.windowManager.addView(this.videosrcviewLayout, this.layoutParams);
    
    
    
    
  }
  

//  private class ItemClickListener implements OnItemClickListener{
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		// TODO Auto-generated method stub
//		HashMap<String,Object> item = (HashMap<String,Object>)arg0.getItemAtPosition(arg2);
//		//setTitle((String)item.get("ItemText"));
//	}
//	  
//  }
  
  protected Handler handler = new Handler()
  {
      public void handleMessage(Message msg)
      {
          super.handleMessage(msg);
//          if (msg.what == SETIS_START)
//          {
//              isChangingSrc = true;
//              if(sourceViewBroadcastReceiver != null)
//              {
//                  InputSourceListViewActivity.this.unregisterReceiver(sourceViewBroadcastReceiver);
//                  sourceViewBroadcastReceiver = null;
//              }
//             // progressDialog = getProgressDialog();
//              //progressDialog.show();
//          }
//          else if (msg.what == SETIS_END_COMPLETE)
//          {
//             // if (progressDialog != null)
//              //{
//                //  if (progressDialog.isShowing())
//                 // {
//                  //    progressDialog.dismiss();
//                 // }
//              //}
//
//              //intent = new Intent(
//               //   "mstar.tvsetting.ui.intent.action.RootActivity");
//             // intent.putExtra("task_tag", "input_source_changed");
//
//             // startActivity(intent);
//
//              finish();
//              isChangingSrc = false;
//          }
      };
  };	
  

  public void checkVideoSrcPosition(int paramInt1, int paramInt2)
  {
    if (this.isShowing)
    {
      int i = paramInt2 - this.height / 2 - this.navHeight / 2;
      if (paramInt2 - this.navHeight / 2 + this.base.fullScreenY / 2 < this.height)
        i = paramInt2 + this.height / 2 + this.navHeight / 2;
      this.base.showView(this.videosrcviewLayout, this.layoutParams, this.width, this.height, paramInt1, i);
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
    this.closedByVideoSrcEditor = true; 
    toggleNav();
  }

  public View getMainView()
  {
    return this.videosrcviewLayout;
  }

  protected void hide()
  {
    super.hide();
//    if (surfaceView != null) {
//        surfaceView.getHolder().removeCallback((android.view.SurfaceHolder.Callback) callback);
//        surfaceView = null;
//    } 
//    handlertv.removeCallbacks(handlerRuntv);
//    ((LinearLayout)this.videosrcviewLayout.findViewById(R.id.video_layout)).removeAllViews();
    this.base.hideView(this.videosrcviewLayout, this.layoutParams);
  }

  protected void show()
  {
    super.show();
    this.closedByVideoSrcEditor = false;
    toggleNav();
    if (!(checkHeight()))
    {
      checkVideoSrcPosition(this.navLayoutParams.x, this.navLayoutParams.y);
      return;
    }
    this.base.showView(this.videosrcviewLayout, this.layoutParams, this.width, this.height, 0, -this.base.fullScreenY / 2);
//    handlertv.postDelayed(handlerRuntv, 300);
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
  
  /**
   * 核心方法，加载最近启动的应用程序
   * 注意：这里我们取出的最近任务为  MAX_RECENT_TASKS + 1个，因为有可能最近任务中包好Launcher2。
   * 这样可以保证我们展示出来的  最近任务 为 MAX_RECENT_TASKS 个
   */
  private void reloadButtons() {
  	
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
          /**
           * 如果找到是launcher，直接continue，后面的appInfos.add操作就不会发生了
           */
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

              if (title != null && title.length() > 0 && icon != null) {
              	singleAppInfo.put("title", title);
              	singleAppInfo.put("icon", icon);
              	singleAppInfo.put("tag", intent);
              }
          }
          appInfos.add(singleAppInfo);
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
			LayoutInflater mInflater = LayoutInflater.from(videosrcviewLayout.getContext());
			View infoView = mInflater.inflate(R.layout.choose_dialog_detail_info, parent);
			ImageView mImageView = (ImageView) infoView.findViewById(R.id.imageView1);
			TextView mTextView = (TextView) infoView.findViewById(R.id.textView1);
			String title = (String) appInfos.get(position).get("title");
			Drawable icon = (Drawable) appInfos.get(position).get("icon");
			Intent singleIntent = (Intent) appInfos.get(position).get("tag");
			infoView.setTag(singleIntent);
			mImageView.setImageDrawable(icon);
			mTextView.setText(title);
			infoView.setOnClickListener(new SingleAppClickListener());
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
 
	/**
	 * 点击应用图标即可启动程序
	 * @author yanbin
	 *
	 */
	private class SingleAppClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = (Intent)v.getTag();
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                try {
                	base.context.startActivity(intent);
                    if(mDialog != null)
                    	mDialog.dismiss();
                } catch (ActivityNotFoundException e) {
                    Log.w("Recent", "Unable to launch recent task", e);
                }
            }
		}  
	}
	
}
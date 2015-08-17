package com.yongfu.floatwindow.window;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.id;
import android.app.ActivityManager;
//import android.app.ActivityManagerNative;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
//import android.os.IGpioControl;
import android.os.Message;
import android.os.RemoteException;
//import android.os.ServiceManager;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mstar.sourcec.control.InputSourceControl;
import com.mstar.sourcec.control.InputSourceControl.MSTAR_INPUTSOURCE;
import com.yongfu.floatwindow.R;
import com.yongfu.floatwindow.penandpaper.ButtonGroup;
import com.yongfu.floatwindow.penandpaper.Settings;
import com.yongfu.floatwindow.paintview.BrushEditorView;
import com.yongfu.floatwindow.paintview.BrushEditorView.ColorSelected;
import com.yongfu.floatwindow.paintview.ColorDefine;
import com.yongfu.floatwindow.paintview.PaintView;

public class ButtonWireing {
	/*
	 * public static final int airBrushButton = R.id.airBrush; public static
	 * final int brushButton = R.id.brush; public static final int colorSelect =
	 * R.id.colorSelect; public static final int editBrushButton = 2131230736;
	 * public static final int eraseButton = 2131230728; public static final int
	 * hideButton = 2131230738; public static final int infoButton = 2131230737;
	 * public static final int paintButton = 2131230735; public static final int
	 * penButton = 2131230729;
	 */

	private View brushSettingsRoot;
	private int prevColor = -1;
	public final UIController window;
	private Boolean bPaint = false;
//	private IGpioControl m_IGpioControl;
	private GlobalData data;
	private static final String HistoryDialogAction = "com.labwe.showHistoryDialog";
	private static final String strTakeScreenshotAction = "com.labwe.takeScreenShot";
	private int usbstatus;

	public ButtonWireing(UIController paramUIController) {
		this.window = paramUIController;
		data = (GlobalData) this.window.getMainLayout().getContext()
				.getApplicationContext();
		data.setBoolValue(false);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_MEDIA_CHECKING);
		intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_EJECT);
		intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
		intentFilter.addDataScheme("file");
		this.window.getMainLayout().getContext()
				.registerReceiver(bsChangedReceiver, intentFilter);
	}

	private BroadcastReceiver bsChangedReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_CHECKING)) {
				usbstatus = 1;
			} else {
				usbstatus = 0;
			}
		}
	};

	private void revertColor(PaintView paramPaintView) {
		if (paramPaintView.getColor() == 0)
			paramPaintView.changeColor(this.prevColor);
	}

	private void setBackground(View paramView, boolean paramBoolean) {
		if (paramBoolean) {
			// paramView.setBackgroundColor(ColorDefine.SELECTED_COLOR_COLOR);
			return;
		}
		paramView.setBackgroundColor(ColorDefine.BACKGROUND_COLOR);

	}

	private void setSaveFileButton(
			final NavWindowController paramNavWindowController, View paramView) {

		final View localView1 = paramNavWindowController.getMainView()
				.findViewById(R.id.saveFileButton);
		final Button saveButton = (Button) paramView
				.findViewById(R.id.button_save);

		EditText utext = (EditText) paramView
				.findViewById(R.id.editText_save_name);
		utext.setClickable(true);

		// utext.setFocusable(true);

		// utext.setInputType(InputType.TYPE_CLASS_TEXT
		// |InputType.TYPE_TEXT_VARIATION_PASSWORD);

		utext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setFocusable(true);

				((TextView) v).setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				v.setFocusable(true);
				v.requestFocus();

				InputMethodManager inputManager = (InputMethodManager) v
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				// inputManager.showSoftInputFromInputMethod(v.getWindowToken(),
				// InputMethodManager.HIDE_NOT_ALWAYS);
				inputManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);

			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean bool = window.CloseSaveFile();

				// restoreBrushButton(localView1, bool);
				// FrameLayout jj = (FrameLayout)window.getMainLayout();
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		});
		// localBrushEditorView.setOnColorSelected(new
		// BrushEditorView.ColorSelected() {

	}

	private void setSaveButton(NavWindowController paramNavWindowController,
			View paramView, final PaintView paramPaintView) {
		final View localView1 = paramNavWindowController.getMainView()
				.findViewById(R.id.saveFileButton);
		// View localView2 = paramView.findViewById(R.id.saveFileCloseButton);

		paramNavWindowController.saveClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// boolean bool = window.toggleSaveShowing();
				// restoreBrushButton(localView1, bool);
				// window.getNavWindow().restoreTransperancy(true);

				if (paramPaintView.saveBitmap()) {
					Toast toast = Toast.makeText(v.getContext(), "保存标注为文件成功!",
							Toast.LENGTH_SHORT);
					toast.show();
					window.getNavWindow().restoreTransperancy(false);
					window.getNavWindow().setFocu_un();
				} else {
					Toast toast = Toast.makeText(v.getContext(), "请插入U盘!",
							Toast.LENGTH_SHORT);
					toast.show();
					window.getNavWindow().restoreTransperancy(false);
					window.getNavWindow().setFocu_un();
				}

			}
		};

		/*
		 * localView2.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub boolean bool = window.CloseSaveFile();
		 * 
		 * restoreBrushButton(localView1, bool); FrameLayout jj =
		 * (FrameLayout)window.getMainLayout();
		 * window.getNavWindow().restoreTransperancy(false);
		 * 
		 * 
		 * 
		 * } });
		 */
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String file = (String) msg.getData().get("filename");
				mPaintView.LoadBitmap(file);
				break;
			}
			super.handleMessage(msg);
		}
	};

	public List<String> getBiaoZhuFile(Context context) {
		File file = new File(context.getResources().getString(
				R.string.save_path));
		List<String> localFolder = new ArrayList<String>();
		// Only directory to scan
		if (file.isDirectory()) {
			if (file.list() != null && file.list().length > 0) {
				for (File f : file.listFiles()) {
					if (f.getName().startsWith("biaozhu")) {
						localFolder.add(f.getName());
					}
				}
			}
		}
		return localFolder;
		// Under the current directory lists all documents
		// (folder)

	}

	List<String> itmelist;
	ListDataListAdapter adapter;

	private void setOpenButton(
			final NavWindowController paramNavWindowController,
			final View paramView, final PaintView paramPaintView) {
		final View localView1 = paramNavWindowController.getMainView()
				.findViewById(R.id.openFileButton);
		View localView2 = paramView.findViewById(R.id.openFileCloseButton);

		final ListView list = (ListView) paramView
				.findViewById(R.id.videoFileList);

		Button uclose = (Button) paramView.findViewById(R.id.button_open);
		uclose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				boolean bool = window.closeOpenfile();

				// restoreBrushButton(localView1, bool);
				// FrameLayout jj = (FrameLayout)window.getMainLayout();
				
				for (int i = 0; i < adapter.mChecked.size(); i++) {
					if (adapter.mChecked.get(i)) {
						// PaintView.LoadBitmap(itmelist.get(i).toString());
						Bundle bundle = new Bundle();
						bundle.putString("filename", itmelist.get(i).toString());
						Message msg = new Message();
						msg.what = 1;
						msg.setData(bundle);
						myHandler.sendMessage(msg);
						break;
					}
				}
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		});

		paramNavWindowController.openClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itmelist = getBiaoZhuFile(paramView.getContext());
				String listData[] = new String[itmelist.size()];
				for (int i = 0; i < itmelist.size(); i++) {
					listData[i] = itmelist.get(i);
				}
				adapter = new ListDataListAdapter(paramNavWindowController
						.getMainView().getContext(), listData);
				list.setAdapter(adapter);

				// lave add in 12-18
				reseatWindow(3);
				window.toggleOpenFileShowing();
				isOpenFileShowing = true;
				// restoreBrushButton(localView1, bool);
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
				// paramPaintView.LoadBitmap();
			}
		};

		localView2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				window.closeOpenfile();
				// lave add in 12-18
				isOpenFileShowing = false;
				// restoreBrushButton(localView1, bool);
				FrameLayout jj = (FrameLayout) window.getMainLayout();
				window.getNavWindow().restoreTransperancy(false);

				window.getNavWindow().setFocu_un();

			}
		});
	}

	private void setBrushButton(NavWindowController paramNavWindowController,
			View paramView) {
		final View localView1 = paramNavWindowController.getMainView()
				.findViewById(R.id.editBrush);
		View localView2 = paramView.findViewById(R.id.brushEditCloseButton);

		paramNavWindowController.brushClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// lave add in 12-18
				Log.d("jack.chen","ButtonWireing.java brushClickListener onClick()");
				reseatWindow(2);
				boolean bool = window.toggleBrushEditShowing();
				isBrushShowing = true;
				// restoreBrushButton(localView1, bool);
				// window.getNavWindow().restoreTransperancy(true);
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		};

		localView2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				window.closeBrush();
				// lave add in 12-18
				isBrushShowing = false;
				// restoreBrushButton(localView1, bool);
				FrameLayout jj = (FrameLayout) window.getMainLayout();
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();

			}
		});

	}

	private void setBrushColorButton(View paramView) {
		final BrushEditorView localBrushEditorView = (BrushEditorView) paramView
				.findViewById(R.id.colorSelect);
		localBrushEditorView
				.setOnColorSelected(new BrushEditorView.ColorSelected() {

					@Override
					public void onColorSelected(int paramInt) {
						// TODO Auto-generated method stub
						PaintView localPaintView = (PaintView) window
								.getPaintLayout();
						localPaintView.changeColor(paramInt);
						prevColor = paramInt;
						Settings.getInstance().setBrushColor(
								localBrushEditorView.getSelectedColor());
					}
				});

		localBrushEditorView.setColor(Settings.getInstance().getBrushColor());
	}

	private void setBrushSizeSlider(View paramView) {
		SeekBar localSeekBar = (SeekBar) paramView.findViewById(R.id.brushSize);

		localSeekBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub

						PaintView localPaintView = (PaintView) window
								.getPaintLayout();
						localPaintView.changeSize(progress);
						Settings.getInstance().setBrushSize(progress);

					}
				});

		localSeekBar.setProgress(Settings.getInstance().getBrushSize());
	}

	private void setBrushStyleButtons(View paramView) {
		final PaintView localPaintView = (PaintView) this.window
				.getPaintLayout();
		ImageView localImageView1 = (ImageView) paramView
				.findViewById(R.id.eraser);
		ImageView localImageView2 = (ImageView) paramView
				.findViewById(R.id.brush);
		ImageView localImageView3 = (ImageView) paramView
				.findViewById(R.id.airBrush);
		ImageView localImageView4 = (ImageView) paramView
				.findViewById(R.id.pen);
		ImageView[] arrayOfImageView = { localImageView1, localImageView2,
				localImageView3, localImageView4 };
		int[] arrayOfInt = { R.drawable.eraser_gray2,
				R.drawable.brush_shape_gray, R.drawable.soft_shape_gray,
				R.drawable.pen_shape_gray };
		final ButtonGroup localButtonGroup = new ButtonGroup(arrayOfImageView,
				new int[] { R.drawable.eraser, R.drawable.brush_shape,
						R.drawable.soft_shape, R.drawable.pen_shape },
				arrayOfInt);

		localImageView1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ButtonWireing.access$1(this.this$0,
				// this.val$paintView.getColor());
				// ButtonWireing.this.
				localPaintView.changeColor(0);
				localPaintView.changeHardness(0F);

				localButtonGroup.setButton(0);
				Settings.getInstance().setBrushType(0);
			}
		});

		localImageView2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				localPaintView.changeHardness(0.20000000298023224F);

				ButtonWireing.this.revertColor(localPaintView);
				localButtonGroup.setButton(1);
				Settings.getInstance().setBrushType(1);
			}
		});

		localImageView3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				localPaintView.changeHardness(0.5F);
				ButtonWireing.this.revertColor(localPaintView);
				localButtonGroup.setButton(2);
				Settings.getInstance().setBrushType(2);

			}
		});

		localImageView4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				localPaintView.changeHardness(0F);
				ButtonWireing.this.revertColor(localPaintView);
				localButtonGroup.setButton(3);
				Settings.getInstance().setBrushType(3);
			}
		});

		localButtonGroup.setButton(Settings.getInstance().getBrushType());
		((BrushEditorView) paramView.findViewById(R.id.colorSelect))
				.setOnColorSelected(new BrushEditorView.ColorSelected() {

					@Override
					public void onColorSelected(int paramInt) {
						// TODO Auto-generated method stub
						if (localButtonGroup.getCurrentSelected() == 0)
							localButtonGroup.setButton(3);
					}
				});

		/*
		 * localImageView1.setOnClickListener(new View.OnClickListener(this,
		 * localPaintView, localButtonGroup) { public void onClick() {
		 * ButtonWireing.access$1(this.this$0, this.val$paintView.getColor());
		 * this.val$paintView.changeColor(0);
		 * this.val$paintView.changeHardness(0F);
		 * this.val$buttonGroup.setButton(0);
		 * Settings.getInstance().setBrushType(0); } });
		 */
		/*
		 * localImageView2.setOnClickListener(new View.OnClickListener(this,
		 * localPaintView, localButtonGroup) { public void onClick() {
		 * this.val$paintView.changeHardness(0.20000000298023224F);
		 * ButtonWireing.access$2(this.this$0, this.val$paintView);
		 * this.val$buttonGroup.setButton(1);
		 * Settings.getInstance().setBrushType(1); } });
		 * localImageView3.setOnClickListener(new View.OnClickListener(this,
		 * localPaintView, localButtonGroup) { public void onClick() {
		 * this.val$paintView.changeHardness(0.5F);
		 * ButtonWireing.access$2(this.this$0, this.val$paintView);
		 * this.val$buttonGroup.setButton(2);
		 * Settings.getInstance().setBrushType(2); } });
		 * localImageView4.setOnClickListener(new View.OnClickListener(this,
		 * localPaintView, localButtonGroup) { public void onClick() {
		 * this.val$paintView.changeHardness(0F);
		 * ButtonWireing.access$2(this.this$0, this.val$paintView);
		 * this.val$buttonGroup.setButton(3);
		 * Settings.getInstance().setBrushType(3); } });
		 * localButtonGroup.setButton(Settings.getInstance().getBrushType());
		 * ((BrushEditorView
		 * )paramView.findViewById(2131230724)).setOnColorSelected(new
		 * BrushEditorView.ColorSelected(this, localButtonGroup) { public void
		 * onColorSelected() { if (this.val$buttonGroup.getCurrentSelected() ==
		 * 0) this.val$buttonGroup.setButton(3); } });
		 */
	}

	private void setClearScreenButton(View paramView) {
		Button localButton = (Button) paramView.findViewById(R.id.eraseAll);
		localButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				window.clearPaint();
			}
		});
		setOnClickFeedBack(localButton);
	}

	private void setHistoryButton(NavWindowController paramNavWindowController,
			final View historyview, final View paramView) {
		// final View localView1 =
		// paramNavWindowController.getMainView().findViewById(R.id.historyButton);
		//
		// View localView2 =
		// historyview.findViewById(R.id.HistoryApkCloseButton);
		paramNavWindowController.historyClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// window.toggleHistoryShowing();
				Intent RecentHistoryDialog = new Intent();
				RecentHistoryDialog.setAction(HistoryDialogAction);
				v.getContext().sendBroadcast(RecentHistoryDialog);
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		};
		// localView2.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// window.closeHistoryfile();
		// // restoreBrushButton(localView1, bool);
		// FrameLayout jj = (FrameLayout)window.getMainLayout();
		// window.getNavWindow().restoreTransperancy(false);
		// window.getNavWindow().setFocu_un();
		// }
		// });

	}

	private void setVideoSrcNavButton(
			NavWindowController paramNavWindowController,
			final View videosrcview, final View paramView) {
		// final View localView1 =
		// paramNavWindowController.getMainView().findViewById(R.id.videoSrcButton);
		//
		View localView2 = videosrcview.findViewById(R.id.VideoSrcCloseButton);
		paramNavWindowController.videoClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("jack.chen","ButtonWireing.java videoClickListener onClick()  相机");
				// lave add in 12-18
				// reseatWindow(1);
				// window.toggleVideoSrcShowing();
				// isVideoShowing = true;

				if (usbstatus == 1) {
					Intent takeScreenshotIntent = new Intent();
					takeScreenshotIntent.setAction(strTakeScreenshotAction);
					v.getContext().sendBroadcast(takeScreenshotIntent);
					window.getNavWindow().restoreTransperancy(false);
					window.getNavWindow().setFocu_un();
				} else {
					Toast toast = Toast.makeText(v.getContext(), "请插入U盘!",
							Toast.LENGTH_SHORT);
					toast.show();
					window.getNavWindow().restoreTransperancy(false);
					window.getNavWindow().setFocu_un();
					/*Intent intent = new Intent(COMMING_OTHER_DEVICE);
					Bundle bundle = new Bundle();
					i = 4;
					bundle.putInt("key", i);
					Log.i("TAG", ""+i);
					switch (i) {
					case 1:
						bundle.putInt("key", i);
						i = 2;
						break;

					case 2:
						bundle.putInt("key", i);
						i = 3;
						break;
					case 3:
						bundle.putInt("key", i);
						i = 4;
						break;
					case 4:
						bundle.putInt("key", i);
						i = 14;
						break;
					case 11:
						bundle.putInt("key", i);
						i = 1;
						break;
					case 12:
						bundle.putInt("key", i);
						i = 11;
						break;
					case 13:
						bundle.putInt("key", i);
						i = 12;
						break;
					case 14:
						bundle.putInt("key", i);
						i = 13;
						break;
					}
					intent.putExtras(bundle);
					v.getContext().sendBroadcast(intent);*/
					
					/*Intent intent = new Intent();
					intent.setAction(ACTION);
					intent.putExtra("cmd", 11);
					v.getContext().sendBroadcast(intent);*/
				}
			}
		};

	}
	private static final String ACTION = "com.labwe.service";
	int i = 1;
	private static final String COMMING_OTHER_DEVICE = "com.labwe.changeinput";

	private void setCenterButton(NavWindowController paramNavWindowController) {

		final NavWindowController uNavWindowController = paramNavWindowController;
		paramNavWindowController.centerClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		};

	}

	private void setInfoButton(NavWindowController paramNavWindowController) {

		final NavWindowController uNavWindowController = paramNavWindowController;
		paramNavWindowController.helpClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SendMsgToService(11, 11);
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 如果是服务里调用，必须加入new task标识
				intent.addCategory(Intent.CATEGORY_HOME);
				v.getContext().startActivity(intent);
				//uNavWindowController.base.setFocus(false);
				
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		};

	}
	/**
	 * jack.chen add
	 */
	private void setHDMI1Button(NavWindowController paramNavWindowController) {

		//final NavWindowController uNavWindowController = paramNavWindowController;
		paramNavWindowController.HDMI1ClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//SendMsgToService(11, 11); ?
				// TODO Auto-generated method stub

				Log.i("jack.chen","ButtonWireing.java setHDMI1Button onClick()");
				InputSourceControl.getInstance().changeInputSource(MSTAR_INPUTSOURCE.E_INPUT_SOURCE_HDMI1);
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		};

	}
	/**
	 * jack.chen add
	 */
	private void setHDMI2Button(NavWindowController paramNavWindowController) {

		//final NavWindowController uNavWindowController = paramNavWindowController;
		paramNavWindowController.HDMI2ClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//SendMsgToService(11, 11); ?
				// TODO Auto-generated method stub

				Log.i("jack.chen","ButtonWireing.java setHDMI2Button onClick()");
				InputSourceControl.getInstance().changeInputSource(MSTAR_INPUTSOURCE.E_INPUT_SOURCE_HDMI2);
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		};

	}
	/**
	 * jack.chen add
	 */
	private void setHDMI3Button(NavWindowController paramNavWindowController) {

		//final NavWindowController uNavWindowController = paramNavWindowController;
		paramNavWindowController.HDMI3ClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//SendMsgToService(11, 11); ?
				// TODO Auto-generated method stub

				Log.i("jack.chen","ButtonWireing.java setHDMI3Button onClick()");
				InputSourceControl.getInstance().changeInputSource(MSTAR_INPUTSOURCE.E_INPUT_SOURCE_HDMI3);
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		};

	}
	/**
	 * jack.chen add
	 */
	private void setVGAButton(NavWindowController paramNavWindowController) {

		//final NavWindowController uNavWindowController = paramNavWindowController;
		paramNavWindowController.VGAClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//SendMsgToService(11, 11); ?
				// TODO Auto-generated method stub

				Log.i("jack.chen","ButtonWireing.java setVGAButton onClick()");
				InputSourceControl.getInstance().changeInputSource(MSTAR_INPUTSOURCE.E_INPUT_SOURCE_VGA);
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		};

	}

	private void setBackButton(NavWindowController paramNavWindowController) {

		final NavWindowController uNavWindowController = paramNavWindowController;
		paramNavWindowController.backClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					/* Missing read/write permission, trying to chmod the file */
					Process process;
					process = Runtime.getRuntime().exec("adb su");
					process = Runtime.getRuntime().exec(
							"adb shell input keyevent 4");
					window.getNavWindow().restoreTransperancy(false);
					window.getNavWindow().setFocu_un();
				} catch (Exception e) {
					e.printStackTrace();
					throw new SecurityException();

				}
				// boolean bRoot = AExecuteAsRoot.canRunRootCommands();
				// if( bRoot)
				// {
				// try {
				// new InputSomeTest().getCommandsToExecute("");
				// window.getNavWindow().restoreTransperancy(false);
				// window.getNavWindow().setFocu_un();
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// }
			}
		};

	}

	private void setOnClickFeedBack(View paramView) {

		paramView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundColor(ColorDefine.BACKGROUND_COLOR);
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundColor(ColorDefine.BACKGROUND_COLOR);
				}
				return false;
			}

		});

		/*
		 * paramView.setOnTouchListener(new View.OnTouchListener(this,
		 * paramView) { public boolean onTouch(, MotionEvent paramMotionEvent) {
		 * if (paramMotionEvent.getAction() == 0)
		 * this.val$button.setBackgroundColor(ColorDefine.SELECTED_COLOR_COLOR);
		 * while (true) { do { ButtonWireing.access$3(this.this$0).invalidate();
		 * return false; } while (paramMotionEvent.getAction() != 1);
		 * this.val$button.setBackgroundColor(0); } } });
		 */
	}

	public void SendMsgToService(int state, int io) {
		Log.d("jack.chen","buttonwireing,java SendMsgToService state="+state+" io="+io);
//		m_IGpioControl = IGpioControl.Stub.asInterface(ServiceManager
//				.getService("gpio"));
//		try {
//			m_IGpioControl.gpioControl(state, io);
//		} catch (RemoteException e) {
//
//		}
	}

	private void setPaintButton(NavWindowController paramNavWindowController) {

		paramNavWindowController.paintClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean bool = window.togglePaintWindow();
				// ButtonWireing.access$0(this.this$0, v, bool);
				if (data.getBoolValue()) {
					((com.yongfu.floatwindow.circlemenu.CircleImageView) window
							.getMainLayout().findViewById(R.id.mainButton))
							.setImageResource(R.drawable.edit_unsel);
					data.setBoolValue(false);
				} else {
					((com.yongfu.floatwindow.circlemenu.CircleImageView) window
							.getMainLayout().findViewById(R.id.mainButton))
							.setImageResource(R.drawable.edit_new);
					data.setBoolValue(true);
				}
				window.getNavWindow().restoreTransperancy(false);
				window.getNavWindow().setFocu_un();
			}
		};

		/*
		 * paramNavWindowController.paintClickListener = new
		 * View.OnClickListener(this) { public void onClick() { boolean bool =
		 * this.this$0.window.togglePaintWindow();
		 * ButtonWireing.access$0(this.this$0, paramView, bool); } };
		 */
	}

	public void restoreBrushButton(View paramView, boolean paramBoolean) {
		setBackground(paramView, paramBoolean);
	}

	PaintView mPaintView;

	public void setAllButtons(NavWindowController paramNavWindowController,
			View paramView, PaintView paramPaintView, View videosrcview,
			View historyview, View openview, View saveview,
			Runnable paramRunnable) {

		mPaintView = paramPaintView;
		setNavOnClickListeners(paramNavWindowController, paramView,
				paramPaintView, videosrcview, historyview, openview, saveview,
				paramRunnable);
		this.brushSettingsRoot = paramView.findViewById(R.id.brushSettingsRoot);
		setBrushStyleButtons(paramView);
		setBrushColorButton(paramView);
		setBrushSizeSlider(paramView);
		setClearScreenButton(paramView);
		// setSaveFileButton(paramNavWindowController,saveview);
		// setPerAppDrawing(paramView);
	}

	public void setNavOnClickListeners(
			NavWindowController paramNavWindowController, View paramView,
			PaintView paramPaintView, View videosrcview, View historyview,
			View openview, View saveview, Runnable paramRunnable) {
		setPaintButton(paramNavWindowController);
		setVideoSrcNavButton(paramNavWindowController, videosrcview, paramView);
		setBrushButton(paramNavWindowController, paramView);
		setInfoButton(paramNavWindowController);
		setBackButton(paramNavWindowController);
		setOpenButton(paramNavWindowController, openview, paramPaintView);
		setSaveButton(paramNavWindowController, saveview, paramPaintView);
		setCenterButton(paramNavWindowController);
		setHistoryButton(paramNavWindowController, historyview, paramView);
		
		setHDMI1Button(paramNavWindowController);//jack.chen add 20150812n
		setHDMI2Button(paramNavWindowController);//jack.chen add 20150812n
		setHDMI3Button(paramNavWindowController);//jack.chen add 20150812n
		setVGAButton(paramNavWindowController);//jack.chen add 20150812n
		
		
	}

	// lave add in 12-18
	private boolean isOpenFileShowing = false;
	private boolean isBrushShowing = false;
	private boolean isVideoShowing = false;

	private void reseatWindow(int position) {
		switch (position) {
		case 1:
			if (isBrushShowing) {
				window.closeBrush();
			}
			if (isOpenFileShowing) {
				window.closeOpenfile();
			}
			break;
		case 2:
			if (isOpenFileShowing) {
				window.closeOpenfile();
			}
			if (isVideoShowing) {
				window.closeVideoSrcfile();
			}
			break;
		case 3:
			if (isBrushShowing) {
				window.closeBrush();
			}
			if (isVideoShowing) {
				window.closeVideoSrcfile();
			}
			break;
		}
		window.getMainLayout();
		window.getNavWindow().restoreTransperancy(false);
		window.getNavWindow().setFocu_un();
	}

}

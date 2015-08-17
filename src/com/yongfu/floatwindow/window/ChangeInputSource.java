package com.yongfu.floatwindow.window;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
//import android.os.IGpioControl;
import android.os.Message;
import android.os.RemoteException;
//import android.os.ServiceManager;
import android.util.Log;



//import com.mstar.android.tv.TvChannelManager;
//import com.mstar.android.tv.TvCommonManager;
//import com.mstar.android.tv.TvPipPopManager;
//import com.mstar.android.tv.TvS3DManager;
//import com.mstar.android.tvapi.common.PvrManager;
//import com.mstar.android.tvapi.common.TvManager;
//import com.mstar.android.tvapi.common.exception.TvCommonException;
//import com.mstar.android.tvapi.common.vo.Enum3dType;
//import com.mstar.android.tvapi.common.vo.EnumPipModes;
//import com.mstar.android.tvapi.common.vo.EnumThreeDVideoDisplayFormat;
//import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.yongfu.floatwindow.R;
public class ChangeInputSource {
private Context context;

	public ChangeInputSource(Context context) {
		super();
		this.context = context;
//		tvChannelManager = TvChannelManager.getInstance();
//		tvCommonmanager = TvCommonManager.getInstance();
//		tvPipPopManager = TvPipPopManager.getInstance();
//		pvrManager = TvManager.getInstance().getPvrManager();
//		tvS3DManager = TvS3DManager.getInstance();
	}

	private static final String debugTag = "LauncherActivity--";
	private ArrayList<InputSourceItem> data = new ArrayList<InputSourceItem>();
	private int[] SourceListFlag = { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	private boolean[] signalFlag = { false, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true };
	private int[] typeFlag = { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
//	private TvCommonManager tvCommonmanager = null;
//	private TvPipPopManager tvPipPopManager = null;
//	private TvS3DManager tvS3DManager = null;
	/* for prompt user to stop recording */
//	private PvrManager pvrManager = null;
//	private TvChannelManager tvChannelManager;

	private String[] tmpData = null;
//	private EnumInputSource inputSource = EnumInputSource.E_INPUT_SOURCE_ATV;
	final static int SETIS_START = -100;
//	private IGpioControl m_IGpioControl;
	private int vga_type = 0;
	private final static int GOPC = 7;
	private final static int GOMEETING = 8;
	private final static int GOVGA = 9;
	private final static int GOLB = 0;
	private final static String goToBroadcast = "com.labwe.databroadcast";
	private final static int GOBROADCAST = 5;	
	private SharedPreferencesUtil share;
	private Handler handler = new Handler();

	public void SendMsgToService(int cmd, int parameter) {
		Intent intent = new Intent("com.labwe.service");
		Bundle bundle = new Bundle();
		bundle.putInt("cmd", cmd);
		bundle.putInt("parameter", parameter);
		intent.putExtras(bundle);
		context.sendBroadcast(intent);
	}

	public void SetIoState(int state, int io) {
		Log.d("jack.chen","changeinputsource.java SetIoState state="+state+" io="+io);
//		m_IGpioControl = IGpioControl.Stub.asInterface(ServiceManager
//				.getService("gpio"));
//		try {
//			m_IGpioControl.gpioControl(state, io);
//		} catch (RemoteException e) {
//
//		}
	}

	private Handler specialHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what > 6) {
				sendSth(msg.what);
			}
//			switch (msg.what) {
//			case GOLB:
//				vga_type = 0;
////				share.putInt("vga_type", vga_type);
//				Intent i = new Intent(
//						"com.yongfu.intent.action.ACTION_SET_VGA_TYPE2");
//				i.putExtra("vga_type", vga_type);
//				context.sendBroadcast(i);
//				SwitchVideoSrcToRecord();
//				SendMsgToService(6, 1);
//				Log.d(debugTag,
//						"---GOLBcurrSource--"
//								+ tvCommonmanager.getCurrentInputSource());
//				break;
//			case GOMEETING:
//				SwitchVideoSrcToMeeting();
//				SendMsgToService(6, 2);
//				Log.d(debugTag,
//						"---GOMEETINGcurrSource--"
//								+ tvCommonmanager.getCurrentInputSource());
//				break;
//			case GOPC:
//				SwitchVideoSrcToPc();
//				SendMsgToService(6, 3);
//				Log.d(debugTag,
//						"---GOPCcurrSource--"
//								+ tvCommonmanager.getCurrentInputSource());
//				break;
//			case GOVGA:
//				SwitchVideoSrcToVGA();
//				SendMsgToService(6, 4);
//				Log.d(debugTag,
//						"---GOVGAcurrSource--"
//								+ tvCommonmanager.getCurrentInputSource());
//				break;
//			case GOBROADCAST:
//				Log.d(debugTag,
//						"---GOBROADCASTcurrSource--"
//								+ tvCommonmanager.getCurrentInputSource());
//				break;
//			}
//			doSth(msg.what);
//			currentInfoPosition = msg.what;
//			if (isnotSpf) {
//				delay2LaunchAppWhenSupernovaNotOkHandler
//						.sendEmptyMessageDelayed(GO_TO_TVAPK, 500);
//			}
		};
	};




	// pc
	public void SwitchVideoSrcToPc() {
		SetIoState(10, 98);
		SetIoState(11, 152);
	}

	// 录播
	public void SwitchVideoSrcToRecord() {
		SetIoState(11, 98);
		SetIoState(11, 152);
	}

	// 视频会议
	public void SwitchVideoSrcToMeeting() {
		SetIoState(11, 98);
		SetIoState(10, 152);
	}

	// 笔记本
	public void SwitchVideoSrcToVGA() {
		SetIoState(10, 98);
		SetIoState(10, 152);
	}


	// add in 12-10
	/************************************************************/
	private void getInputSourcelist() {
		Log.d("jack.chen","changeinputsource.java getInputSourcelist()");
		int[] sourceList;
//		try {
//			sourceList = TvManager.getInstance().getSourceList();
//			// IntArrayList sourceList = tvCommonmanager.getSourceList();
//			if (sourceList != null) {
//				for (int i = 0; i < SourceListFlag.length; i++) {
//					if (i < sourceList.length) {
//						SourceListFlag[i] = sourceList[i];
//					}
//				}
//			}
//		} catch (TvCommonException e) {
//			e.printStackTrace();
//		}

	}


	
	public void switchVideoSrc(int position){
		specialHandler.sendEmptyMessage(position);
	}
	
	

	public void doSth(int position) {
		Log.d("jack.chen","changeinputsource.java doSth()");
		if (position > 6) {
			position = 0;
		}
//		inputSource = EnumInputSource.values()[data.get(position).getPositon()];
//
//		for (int i = 0; i < data.size(); i++) {
//			Log.d(debugTag,
//					"---data--"
//							+ i
//							+ "is   "
//							+ EnumInputSource.values()[data.get(position)
//									.getPositon()]);
//			Log.d(debugTag, "---data--" + i + "is   "
//					+ data.get(position).getPositon());
//		}
//
//		Log.d(debugTag, "---inputSource--" + inputSource);
//		Log.d(debugTag,
//				"---CurrentInputSourc--"
//						+ tvCommonmanager.getCurrentInputSource());
//
//		handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				context.sendBroadcast(new Intent(
//						"browser.release.player"));
//			}
//		}, 500);
//		if (inputSource != tvCommonmanager.getCurrentInputSource()) {
//			boolean bRecordNow = false;
//			try {
//				bRecordNow = pvrManager.isRecording();
////				nSelectedItemIdx = position;
//				Log.d(debugTag, "---bRecordNow--" + pvrManager.isRecording());
//			} catch (TvCommonException e) {
//				e.printStackTrace();
//			}
//
//			if (bRecordNow
//					&& (tvCommonmanager.getCurrentInputSource() == EnumInputSource.E_INPUT_SOURCE_DTV)) {
//	//			nSelectedItemIdx = position;
//				Log.i(debugTag, "Recording now. display dialog to notify user.");
//				// showStopRecordDialog();
//			} else {
//				changeInputSource(inputSource, position);
//			}
//		}
	}	
	
	
	private void sendSth(int position) {
		vga_type = position - 6;
//		share.putInt("vga_type", vga_type);
		Intent i = new Intent("com.yongfu.intent.action.ACTION_SET_VGA_TYPE2");
		i.putExtra("vga_type", vga_type);
		context.sendBroadcast(i);
	}
	
	
	
	private void getSourceStatus() {
		Log.d("jack.chen","changeinputsource.java getSourceStatus()");
//		boolean[] sourceStatusList = tvCommonmanager.GetInputSourceStatus();
//		if (sourceStatusList != null) {
//			for (int i = 0; i < signalFlag.length; i++) {
//				// if (i < sourceStatusList.size())
//				{
//					signalFlag[i] = sourceStatusList[i];
//					Log.d(debugTag, "sourceStatusList[" + i + "] is :"
//							+ EnumInputSource.values()[i] + ","
//							+ sourceStatusList[i]);
//				}
//			}
//		}
	}

	public void init1219(Context context) {
		getInputSourcelist();
		getSourceStatus();
		tmpData = context.getResources().getStringArray(
				R.array.str_arr_input_source_vals);

		if (data != null) {
			data = new ArrayList<InputSourceItem>();
		}
//
//		for (int i = 0; i < tmpData.length; i++) {
//			// if (i == 10 || i == 15 || i == 19 || i == 22 || i == 27 ||
//			// SourceListFlag[i] == 0)
//			if (SourceListFlag[i] == 0)// for i = 10 ,15, 19, 22,
//										// 27SOURCE_XXXX_MAX aways equals zero
//			{
//				continue;
//			}
//
//			if ((tvPipPopManager.isPipModeEnabled() == true)
//					&& (tvPipPopManager.getPipMode() == EnumPipModes.E_PIP_MODE_PIP)) {
//				if (tvPipPopManager.checkPipSupportOnSubSrc(EnumInputSource
//						.values()[i]) == false) {
//					continue;
//				}
//			} else if ((tvPipPopManager.isPipModeEnabled() == true)
//					&& (tvPipPopManager.getPipMode() == EnumPipModes.E_PIP_MODE_POP)) {
//				Enum3dType formatType = Enum3dType.EN_3D_TYPE_NUM;
//				try {
//					formatType = TvManager.getInstance()
//							.getThreeDimensionManager().getCurrent3dFormat();
//				} catch (TvCommonException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				if (formatType == Enum3dType.EN_3D_DUALVIEW) {
//					// System.out.println("\n continue()xx " + i);
//				} else {
//					// use pip's instead of pop's
//					if (tvPipPopManager.checkPipSupportOnSubSrc(EnumInputSource
//							.values()[i]) == false) {
//
//						continue;
//					}
//				}
//			} else if ((tvPipPopManager.isPipModeEnabled() == true)
//					&& (tvPipPopManager.getPipMode() == EnumPipModes.E_PIP_MODE_TRAVELING)) {
//				EnumInputSource curSubSource = EnumInputSource.E_INPUT_SOURCE_ATV;
//				try {
//					curSubSource = TvManager.getInstance()
//							.getCurrentSubInputSource();
//				} catch (TvCommonException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				if (curSubSource.ordinal() == EnumInputSource.values()[i]
//						.ordinal()) {
//					continue;
//				} else if (tvPipPopManager.checkTravelingModeSupport(
//						EnumInputSource.values()[i], curSubSource) == false) {
//					continue;
//				}
//			}
//			InputSourceItem inputSourceItem = new InputSourceItem();
//			inputSourceItem.setInputSourceName(tmpData[i]);
//			inputSourceItem.setPositon(i);
//			inputSourceItem.setTypeFlag(typeFlag[i]);
//			inputSourceItem.setSignalFlag(signalFlag[i]);
//			data.add(inputSourceItem);
//		}
		String[] data2 = new String[2];

		data2[0] = "视频会议";
		data2[1] = "VGA";
		for (int i = 8; i < 10; i++) {
			InputSourceItem inputSourceItem = new InputSourceItem();
			inputSourceItem.setInputSourceName(data2[i - 8]);
			inputSourceItem.setPositon(i);
			inputSourceItem.setTypeFlag(0);
			inputSourceItem.setSignalFlag(true);
			data.add(inputSourceItem);
		}
		Log.i(debugTag, "---data--" + data.size());
	}


	public void UpdateSourceInputType(int inputSourceTypeIdex) {
		long ret = -1;
		ContentValues vals = new ContentValues();
		vals.put("enInputSourceType", inputSourceTypeIdex);
		try {
			// change in 12-9
			ret = context.getContentResolver().update(
					Uri.parse("content://mstar.tv.usersetting/systemsetting"),
					vals, null, null);
		} catch (SQLException e) {
		}
		if (ret == -1) {
			System.out.println("update tbl_PicMode_Setting ignored");
		}
	}

//	private void changeInputSource(EnumInputSource inpSource, int position) {
//		inputSource = inpSource;
//
//		Log.d(debugTag, "---CurrentInputSource().ordinal()--"
//				+ tvCommonmanager.getCurrentInputSource().ordinal());
//		Log.d(debugTag, "---E_INPUT_SOURCE_STORAGE.ordinal()--"
//				+ EnumInputSource.E_INPUT_SOURCE_STORAGE.ordinal());
//
//		if (tvCommonmanager.getCurrentInputSource().ordinal() >= EnumInputSource.E_INPUT_SOURCE_STORAGE
//				.ordinal()) {
//
//			Intent source_switch_from_storage = new Intent(
//					"source.switch.from.storage");
//			context.sendBroadcast(source_switch_from_storage);
//			Log.d(debugTag, "---executePreviousTask--" + position);
//			executePreviousTask(position);
//		} else {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					Log.i(debugTag, "---inputSource.ordinal()----"
//							+ inputSource.ordinal());
//					UpdateSourceInputType(inputSource.ordinal());
//					tvS3DManager
//							.setDisplayFormatForUI(EnumThreeDVideoDisplayFormat.E_ThreeD_Video_DISPLAYFORMAT_NONE
//									.ordinal());
//				}
//			}).start();
//		}
//	}

	private Intent targetIntent;

	private void executePreviousTask(final int position) {

		targetIntent = new Intent(
				"mstar.tvsetting.ui.intent.action.RootActivity");
		targetIntent.putExtra("task_tag", "input_source_changed");

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

//				tvS3DManager
//						.setDisplayFormatForUI(EnumThreeDVideoDisplayFormat.E_ThreeD_Video_DISPLAYFORMAT_NONE
//								.ordinal());
//
//				EnumInputSource inputSource = EnumInputSource.values()[data
//						.get(position).getPositon()];
//
//				Log.d(debugTag, "---inputSource--" + inputSource);
//
//				if (inputSource == EnumInputSource.E_INPUT_SOURCE_ATV) {
//
//					tvCommonmanager.setInputSource(inputSource);
//					int curChannelNumber = tvChannelManager
//							.getCurrentChannelNumber();
//					if (curChannelNumber > 0xFF) {
//						curChannelNumber = 0;
//					}
//					tvChannelManager.setAtvChannel(curChannelNumber);
//				} else if (inputSource == EnumInputSource.E_INPUT_SOURCE_DTV) {
//					tvCommonmanager.setInputSource(inputSource);
//					tvChannelManager.playDtvCurrentProgram();
//				} else {
//					tvCommonmanager.setInputSource(inputSource);
//				}

				try {
					if (targetIntent != null){
						//context.startActivity(targetIntent);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}

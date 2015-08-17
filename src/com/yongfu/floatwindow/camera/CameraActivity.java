package com.yongfu.floatwindow.camera;

import java.io.IOException;

import com.yongfu.floatwindow.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import android.widget.Toast;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
	private static final String TAG = "xy.huang";
	private WindowManager mWindowManager;
	private View paintView = null;
	private LayoutParams params;
	public Context mContext = null;

	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private boolean previewRunning;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		Log.d(TAG, "----surfaceView---onCreate");
		setContentView(R.layout.surfaceview);
		init();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "----surfaceView---onDestroy");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void init() {
		// paintView = (RelativeLayout) LayoutInflater.from(mContext).inflate(
		// R.layout.surfaceview, null);
		// init();
		surfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// TODO Auto-generated constructor stub
	}

	// void init() {
	// if (mWindowManager == null) {
	// mWindowManager = (WindowManager) mContext
	// .getSystemService(Context.WINDOW_SERVICE);
	// }
	// if (params == null) {
	// params = new WindowManager.LayoutParams();
	// params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
	// params.width = WindowManager.LayoutParams.MATCH_PARENT;
	// params.height = WindowManager.LayoutParams.MATCH_PARENT;
	// params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
	// | LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
	// | LayoutParams.FLAG_SPLIT_TOUCH
	// | LayoutParams.FLAG_HARDWARE_ACCELERATED;
	// params.type = LayoutParams.TYPE_PHONE;
	// params.format = PixelFormat.RGBA_8888;
	// params.gravity = Gravity.CENTER;
	//
	// params.windowAnimations = android.R.style.Animation_Dialog;
	// }
	// Log.d("xy.huang", "fffffffff  openPainView");
	// openPainView();
	// }

	void openPainView() {
		mWindowManager.addView(paintView, params);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Log.d("xy.huang", "surfaceChanged camera:" + camera);
		if (camera == null) {
			Toast toast = Toast.makeText(this, "Camera uninstall ok",
					Toast.LENGTH_LONG);
			toast.show();
			finish();
		} else {
			if (previewRunning) {
				camera.stopPreview();
			}
			Camera.Parameters p = camera.getParameters();
			p.setPreviewSize(width, height);
			p.setPreviewFormat(PixelFormat.JPEG);

			camera.setParameters(p);

			try {
				camera.setPreviewDisplay(holder);
				camera.startPreview();
				previewRunning = true;
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			}
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		camera = Camera.open();
		Log.d("xy.huang", "surfaceCreated camera:" + camera);
		if (camera != null) {
			// Camera.Parameters params = camera.getParameters();
			// camera.setParameters(params);
		} else {
			Toast.makeText(mContext, "Camera not available!", Toast.LENGTH_LONG)
					.show();

		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (camera != null)
			camera.stopPreview();
		previewRunning = false;
		if (camera != null) {
			camera.release();
			Log.d(TAG, "----surfaceView---surfaceDestroyed");
			finish();
			// mWindowManager.removeViewImmediate(paintView);
		}
	}

}

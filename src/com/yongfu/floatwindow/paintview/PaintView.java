package com.yongfu.floatwindow.paintview;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.yongfu.floatwindow.R;
import com.yongfu.floatwindow.window.NavWindowController;
import com.yongfu.floatwindow.window.UIController;

public class PaintView extends View {
	private Bitmap bitmap;
	private RectF brushRect;
	private Canvas canvas;
	private int color = -16777216;
	private float hardness = 0F;
	private boolean isNavHidden = false;
	private float linePrevX;
	private float linePrevY;

	private float linePrevX2;
	private float linePrevY2;

	private float linePrevX3;
	private float linePrevY3;

	private UIController navController;
	private final Paint paint;
	private int size = 16;

	public PaintView(Context paramContext, UIController paramUIController) {
		super(paramContext);
		setFocusable(true);
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		//this.paint.setDither(true);
		this.brushRect = new RectF();
		this.navController = paramUIController;
		linePrevX2 = -1;
		linePrevX = -1;
		linePrevY2 = -1;
		linePrevY = -1;

		paint2 = new Paint();
		linePrevY3 = -1;
		linePrevX3 = -1;
		
	}

	private void onTouchDown(float paramFloat1, float paramFloat2) {
		setRect(paramFloat1, paramFloat2);
		this.canvas.drawPoint(paramFloat1, paramFloat2, this.paint);
		this.linePrevX = paramFloat1;
		this.linePrevY = paramFloat2;
		mX1 = paramFloat1;
		mY1 = paramFloat2;
		System.out.println("down--==--==mX1" + mX1 + "   mY1" + mY1);
		/*if (colorChanged) {
			System.out.println("-=-=before" + path);
			path = new Path();
			System.out.println("-=-=after" + path);
		}
		if (sizeChanged) {
			path = new Path();
		}*/
		path.moveTo(mX1, mY1);
		//path4.moveTo(mX1, mY1);
		colorChanged = false;
		sizeChanged = false;
	}

	private void onTouchDown2(float paramFloat1, float paramFloat2) {
		setRect(paramFloat1, paramFloat2);
		this.canvas.drawPoint(paramFloat1, paramFloat2, this.paint);
		this.linePrevX2 = paramFloat1;
		this.linePrevY2 = paramFloat2;
		mX2 = paramFloat1;
		mY2 = paramFloat2;
		System.out.println("down--==--==mX2" + mX2 + "   mY2" + mY2);
		if (colorChanged1) {
			System.out.println("-=-=before" + path);
			path1 = new Path();
			System.out.println("-=-=after" + path);
		}
		if (sizeChanged1) {
			path1 = new Path();
		}
		path1.moveTo(mX2, mY2);
		//path5.moveTo(mX2, mY2);
		colorChanged1 = false;
		sizeChanged1 = false;
	}

	private void onTouchDown3(float paramFloat1, float paramFloat2) {
		setRect(paramFloat1, paramFloat2);
		this.canvas.drawPoint(paramFloat1, paramFloat2, this.paint);
		this.linePrevX3 = paramFloat1;
		this.linePrevY3 = paramFloat2;
		mX3 = paramFloat1;
		mY3 = paramFloat2;
		if (colorChanged2) {
			System.out.println("-=-=before" + path);
			path2 = new Path();
			System.out.println("-=-=after" + path);
		}
		if (sizeChanged2) {
			path2 = new Path();
		}
		System.out.println("down--==--==mX3" + mX3 + "   mY3" + mY3);
		path2.moveTo(mX3, mY3);
		//path6.moveTo(mX3, mY3);
		colorChanged2 = false;
		sizeChanged2 = false;
	}

	private void onTouchMove(float paramFloat1, float paramFloat2) {
		// this.canvas.drawLine(this.linePrevX, this.linePrevY, paramFloat1,
		// paramFloat2, this.paint);

		float x = paramFloat1;
		float y = paramFloat2;
		float perviousX = mX1;
		float perviousY = mY1;
		float dx = Math.abs(x - perviousX);
		float dy = Math.abs(y - perviousY);
		if ((dx >= 3) || (dy >= 3)) {
			float cX = (x + perviousX) / 2;
			float cY = (y + perviousY) / 2;
			path.quadTo(perviousX, perviousY, cX, cY);
			//path4.quadTo(perviousX, perviousY, cX, cY);
			mX1 = x;
			mY1 = y;
			//this.canvas.drawPath(path, paint);
			this.colorChanged = false;
			this.sizeChanged = false;
		}
		this.linePrevX = paramFloat1;
		this.linePrevY = paramFloat2;

	}

	private void onTouchMove2(float paramFloat1, float paramFloat2) {
		// this.canvas.drawLine(this.linePrevX2, this.linePrevY2, paramFloat1,
		// paramFloat2, this.paint);

		float x = paramFloat1;
		float y = paramFloat2;
		float perviousX = mX2;
		float perviousY = mY2;
		float dx = Math.abs(x - perviousX);
		float dy = Math.abs(y - perviousY);
		if ((dx >= 3) || (dy >= 3)) {
			float cX = (x + perviousX) / 2;
			float cY = (y + perviousY) / 2;
			path1.quadTo(perviousX, perviousY, cX, cY);
			//path5.quadTo(perviousX, perviousY, cX, cY);
			mX2 = x;
			mY2 = y;
			System.out.println("moveLin2-=-=x" + x + "   y" + y + "   cX" + cX
					+ "  cY" + cY);
			this.canvas.drawPath(path1, paint);
			colorChanged1 = false;
			this.sizeChanged1 = false;
		}
		this.linePrevX2 = paramFloat1;
		this.linePrevY2 = paramFloat2;
	}

	private void onTouchMove3(float paramFloat1, float paramFloat2) {
		// this.canvas.drawLine(this.linePrevX3, this.linePrevY3, paramFloat1,
		// paramFloat2, this.paint);

		float x = paramFloat1;
		float y = paramFloat2;
		float perviousX = mX3;
		float perviousY = mY3;
		float dx = Math.abs(x - perviousX);
		float dy = Math.abs(y - perviousY);
		if ((dx >= 3) || (dy >= 3)) {
			float cX = (x + perviousX) / 2;
			float cY = (y + perviousY) / 2;
			path2.quadTo(perviousX, perviousY, cX, cY);
			//path6.quadTo(perviousX, perviousY, cX, cY);
			mX3 = x;
			mY3 = y;
			System.out.println("moveLin3-=-=x" + x + "   y" + y + "   cX" + cX
					+ "  cY" + cY);
			this.canvas.drawPath(path2, paint);
			colorChanged2 = false;
			this.sizeChanged2 = false;
		}
		this.linePrevX3 = paramFloat1;
		this.linePrevY3 = paramFloat2;
	}

	private void onTouchUp(float paramFloat1, float paramFloat2) {
		this.canvas.drawPath(path, paint);
		path.reset();
		this.linePrevX = -1.0F;
		this.linePrevY = -1.0F;
		mX1 = paramFloat1;
		mY1 = paramFloat2;
	}

	private void onTouchUp2(float paramFloat1, float paramFloat2) {
		//this.canvas.drawPath(path1, paint);
		//path1.reset();
		this.linePrevX2 = -1.0F;
		this.linePrevY2 = -1.0F;
		mX2 = paramFloat1;
		mY2 = paramFloat2;
	}

	private void onTouchUp3(float paramFloat1, float paramFloat2) {
		//this.canvas.drawPath(path2, paint);
		//path2.reset();
		this.linePrevX3 = -1.0F;
		this.linePrevY3 = -1.0F;
		mX3 = paramFloat1;
		mY3 = paramFloat2;
	}

	private void setPaint() {
		this.paint.setColor(color);
		this.paint.setAntiAlias(true);
		this.paint.setStrokeWidth(this.size * (1F - this.hardness));
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		if (this.size * this.hardness > 0F) {
			this.paint.setMaskFilter(new BlurMaskFilter(this.size
					* this.hardness, BlurMaskFilter.Blur.NORMAL));
		} else {
			this.paint.setMaskFilter(null);
		}
		// while (true)
		// {
		//PathEffect effect = new CornerPathEffect(10.0f);
		//paint.setPathEffect(effect);
		this.paint.setStyle(Paint.Style.STROKE);
		// this.paint
		// .setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
		if (this.color == 0) {
			//isWriteMode = true;
			this.paint
					.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		}
		else
		{
			//if (isWriteMode) {
			//	this.paint.setColor(tempColor);
			//	isWriteMode = false;
				this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
			//}
		}

		// }

	}

	private void setRect(float paramFloat1, float paramFloat2) {
		int i = this.size / 2;
		this.brushRect.left = (paramFloat1 - i);
		this.brushRect.top = (paramFloat2 - i);
		this.brushRect.right = (paramFloat1 + i);
		this.brushRect.bottom = (paramFloat2 + i);
	}

	public void changeColor(int paramInt) {
		if (paramInt != color) {
			colorChanged = true;
			colorChanged1 = true;
			colorChanged2 = true;
			//path4.reset();
			//path5.reset();
			//path6.reset();
		}
		//if (paramInt == 0) {
		//	tempColor = this.color;
		//}
		this.color = paramInt;

	}

	public void changeHardness(float paramFloat) {
		this.hardness = paramFloat;
		//if (isWriteMode) {
		//	changeColor(tempColor);
		//}
	}

	public void changeOrienation(int paramInt) {
		invalidate();
	}

	public void changeSize(int paramInt) {
		if (this.size < paramInt) {
			sizeChanged = true;
			sizeChanged1 = true;
			sizeChanged2 = true;
		//	path4.reset();
			//path5.reset();
			//path6.reset();
		}
		this.size = paramInt;
	}

	public void clearPaint() {
		if (this.canvas != null) {
			this.bitmap.eraseColor(0);
			path.reset();
			path1.reset();
			path2.reset();
			//path4.reset();
			//path5.reset();
			//path6.reset();
			invalidate();
		}
	}

	public Bitmap getBitmap() {
		return this.bitmap;
	}

	public int getColor() {
		return this.color;
	}

	/*
	 * @Override protected void dispatchDraw(Canvas canvas) {
	 * super.dispatchDraw(canvas); if (this.bitmap != null)
	 * canvas.drawBitmap(this.bitmap, 0F, 0F, null); }
	 */
	Paint paint2;
	
	private Boolean daw = true;
	
	protected void onDraw(Canvas paramCanvas) {
		if (this.bitmap != null)
			//paramCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,
			//		Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		//paramCanvas.drawPath(path4, paint);
		//paramCanvas.drawPath(path5, paint);
		//paramCanvas.drawPath(path6, paint);
		paramCanvas.drawBitmap(bitmap, 0f, 0f, paint2);
		//synchronized (daw) {
			//paramCanvas.drawPath(path1, paint);
			//paramCanvas.drawPath(path2, paint);
				paramCanvas.drawPath(path, paint);
				//paramCanvas.drawPath(path1, paint);
				//paramCanvas.drawPath(path2, paint);
		//}
		// paramCanvas.drawBitmap(this.bitmap, 0f, 0f, paint2);
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		// int i = paramMotionEvent.getAction();
		// int count =
		setPaint();
		/*
		 * if ((this.isNavHidden) &&
		 * (!(this.navController.getNavWindow().isShowing()))) { if (i ==
		 * MotionEvent.ACTION_UP) { if (this.navController != null)
		 * this.navController.getNavWindow().toggleMinimize(this, null);
		 * this.isNavHidden = false; } return true; }
		 */
		switch (paramMotionEvent.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			float f1 = paramMotionEvent.getX(0);// paramMotionEvent.getX();
			float f2 = paramMotionEvent.getY(0);
			onTouchDown(f1, f2);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			if (paramMotionEvent.getActionIndex() == 1) {
				onTouchDown2(paramMotionEvent.getX(1), paramMotionEvent.getY(1));
			} else if (paramMotionEvent.getActionIndex() == 2) {
				onTouchDown3(paramMotionEvent.getX(2), paramMotionEvent.getY(2));
			}
			break;
		case MotionEvent.ACTION_MOVE:
			onTouchMove(paramMotionEvent.getX(0), paramMotionEvent.getY(0));
			if (paramMotionEvent.getPointerCount() >= 3) {
				onTouchMove2(paramMotionEvent.getX(1), paramMotionEvent.getY(1));
				onTouchMove3(paramMotionEvent.getX(2), paramMotionEvent.getY(2));
			} else if (paramMotionEvent.getPointerCount() >= 2) {
				onTouchMove2(paramMotionEvent.getX(1), paramMotionEvent.getY(1));
			}

			break;
		case MotionEvent.ACTION_POINTER_UP:
			if (paramMotionEvent.getActionIndex() == 1) {
				onTouchUp2(paramMotionEvent.getX(1), paramMotionEvent.getY(1));
			} else if (paramMotionEvent.getActionIndex() == 2) {
				onTouchUp3(paramMotionEvent.getX(2), paramMotionEvent.getY(2));
			}
			break;
		case MotionEvent.ACTION_UP:
			onTouchUp(paramMotionEvent.getX(0), paramMotionEvent.getY(0));
			break;
		}

		invalidate();
		return true;

		/*
		 * while (true) { do { invalidate(); i = paramMotionEvent.getAction();
		 * // if (i != 0) // break; //onTouchDown(f1, f2); } while (i !=
		 * MotionEvent.ACTION_UP);
		 * 
		 * onTouchUp(f1, f2); }
		 */
	}

	public void setTouchToggleNav() {
		this.isNavHidden = true;
	}

	@SuppressLint("NewApi")
	public void LoadBitmap(String filename) {
		// FileInputStream fis = new
		// FileInputStream("/storage/sdcard1/dddx.png");
		clearPaint();
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inMutable = true;
		Bitmap bitmap = BitmapFactory.decodeFile(
				this.getResources().getString(R.string.save_path) + filename,
				localOptions); // BitmapFactory.decodeFile("/storage/sdcard1/dddx.png",localOptions);
		this.invalidate();
		// Bitmap bitmap =Bitmap.createBitmap(800, 900,
		// Bitmap.Config.ARGB_8888);// BitmapFactory.decodeStream(fis);
		// Bitmap.createBitmap(800, 199, Bitmap.Config.ARGB_8888);
		useNewBitmap(bitmap);
	}

	public boolean saveBitmap() {
		if (bitmap != null) {
			String jj = this.getResources().getString(R.string.save_path);// "/storage/sdcard1/";
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyyMMddHHmmss");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String str = formatter.format(curDate);
				ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100,
						localByteArrayOutputStream);
				File localFile = new File(jj, "biaozhu" + str + ".png");
				localFile.createNewFile();
				FileOutputStream localFileOutputStream = new FileOutputStream(
						localFile);
				localFileOutputStream.write(localByteArrayOutputStream
						.toByteArray());
				localFileOutputStream.close();
				// FileOutputStream out = new FileOutputStream(f);
				// this.bitmap.compress(Bitmap.CompressFormat.PNG, 100,
				// out);
				// out.flush();
				// out.close();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void useNewBitmap(Bitmap paramBitmap) {
		this.bitmap = paramBitmap;
		canvas = null;
		this.canvas = new Canvas();
		this.canvas.setDrawFilter(new PaintFlagsDrawFilter(0,
				Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		this.canvas.setBitmap(this.bitmap);

	}

	/***********************************************/
	private Path path = new Path();
	private Path path1 = new Path();
	private Path path2 = new Path();
	//private Path path4 = new Path();
	//private Path path5 = new Path();
	//private Path path6 = new Path();
	private float mX1;
	private float mY1;
	private float mX2;
	private float mY2;
	private float mX3;
	private float mY3;
	private boolean colorChanged = false;
	private boolean colorChanged1 = false;
	private boolean colorChanged2 = false;
	private boolean sizeChanged = false;
	private boolean sizeChanged1 = false;
	private boolean sizeChanged2 = false;
	//add in 12 - 20
	//private int tempColor ;
	//private boolean isWriteMode = false;
}
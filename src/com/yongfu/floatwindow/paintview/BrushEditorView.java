package com.yongfu.floatwindow.paintview;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yongfu.floatwindow.R;

public class BrushEditorView extends View
{
  private static final float COLUMN_COUNT = 6.0F;
  private static final float PADDING = 5.0F;
  private static final float ROW_COUNT = 2.0F;
  private int[] Colors;
  private float columnWidth;
  private int height;
  private List<ColorSelected> onColorSelect;
  private Paint paint;
  private float rowHeight;
  private int selectedColor = 11;
  private int width;

  public BrushEditorView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }

  public BrushEditorView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext);
  }

  private void drawColorGrid(Canvas paramCanvas)
  {
    int i = 0;
    while (true)
    {
      if (i >= this.Colors.length)
        return;
      int j = (int)(i % 6.0F);
      int k = (int)(i / 6.0F);
      drawColor(paramCanvas, this.Colors[i], j, k);
      ++i;
    }
  }

  private void init(Context paramContext)
  {
    this.paint = new Paint();
    this.columnWidth = ((paramContext.getResources().getDimension(R.dimen.brush_settings_total_width) - 35.0F) / 6.0F);
    this.rowHeight = ((paramContext.getResources().getDimension(R.dimen.brush_settings_edit_height) - 15.0F) / 2F);
    this.Colors = ColorDefine.getColors(paramContext);
    this.onColorSelect = new ArrayList();
  }

  private void notifyColorSelected(int paramInt)
  {
    Iterator localIterator = this.onColorSelect.iterator();
    while (true)
    {
      if (!(localIterator.hasNext()))
        return;
      ((ColorSelected)localIterator.next()).onColorSelected(paramInt);
    }
  }

  public void drawColor(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3)
  {
    float f1 = 5.0F + paramInt2 * (5.0F + this.columnWidth);
    float f2 = 5.0F + paramInt3 * (5.0F + this.rowHeight);
    this.paint.setColor(paramInt1);
    paramCanvas.drawRect(f1, f2, f1 + this.columnWidth, f2 + this.rowHeight, this.paint);
  }

  public void drawSelection(Canvas paramCanvas)
  {
    int i = (int)(this.selectedColor % 6.0F);
    int j = (int)(this.selectedColor / 6.0F);
    float f1 = 5.0F + i * (5.0F + this.columnWidth) - 2.5F;
    float f2 = 5.0F + j * (5.0F + this.rowHeight) - 2.5F;
    float f3 = 5.0F + f1 + this.columnWidth;
    float f4 = 5.0F + f2 + this.rowHeight;
//    this.paint.setColor(ColorDefine.SELECTED_COLOR_COLOR);
    this.paint.setStrokeWidth(2.5F);
    paramCanvas.drawLines(new float[] { f1, f2, f3, f2, f3, f2, f3, f4, f1, f4, f3, f4, f1, f2, f1, f4 }, this.paint);
  }

  public int getSelectedColor()
  {
    return this.selectedColor;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawSelection(paramCanvas);
    drawColorGrid(paramCanvas);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    this.width = View.MeasureSpec.getSize(paramInt1);
    this.height = View.MeasureSpec.getSize(paramInt2);
    setMeasuredDimension(this.width, this.height);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      float f = paramMotionEvent.getY();
      int i = (int)(paramMotionEvent.getX() / (5.0F + this.columnWidth));
      int j = (int)(6.0F * (int)(f / (5.0F + this.rowHeight)) + i);
      if (j >= this.Colors.length)
        j = -1 + this.Colors.length;
      this.selectedColor = j;
      //Log.d("COLORS", this.Colors[j]);
      notifyColorSelected(this.Colors[j]);
      playSoundEffect(0);
      invalidate();
    }
    return super.onTouchEvent(paramMotionEvent);
  }

  public void setColor(int paramInt)
  {
    this.selectedColor = paramInt;
    notifyColorSelected(this.Colors[paramInt]);
    invalidate();
  }

  public void setOnColorSelected(ColorSelected paramColorSelected)
  {
    this.onColorSelect.add(paramColorSelected);
  }

  public static abstract interface ColorSelected
  {
    public abstract void onColorSelected(int paramInt);
  }
}

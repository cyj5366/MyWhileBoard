package com.yongfu.floatwindow.penandpaper;



import android.widget.ImageView;
import com.yongfu.floatwindow.paintview.ColorDefine;

public class ButtonGroup
{
  private int currentSelected;
  private final int[] defaultState;
  private final ImageView[] group;
  private final int[] selectedState;

  public ButtonGroup(ImageView[] paramArrayOfImageView, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    this.group = paramArrayOfImageView;
    this.defaultState = paramArrayOfInt1;
    this.selectedState = paramArrayOfInt2;
  }

  public int getCurrentSelected()
  {
    return this.currentSelected;
  }

  public void setButton(int paramInt)
  {
    setButtonBackground(paramInt);
    setButtonImage(paramInt);
    this.currentSelected = paramInt;
  }

  public void setButtonBackground(int paramInt)
  {
    int i = 0;
    while (true)
    {
      if (i >= this.group.length)
      {
//        this.group[paramInt].setBackgroundColor(ColorDefine.SELECTED_COLOR_COLOR);
        return;
      }
      if (i != paramInt)
        this.group[i].setBackgroundColor(0);
      ++i;
    }
  }

  public void setButtonImage(int paramInt)
  {
    int i=0;
    if ((this.defaultState != null) && (this.selectedState != null))
      i = 0;
    while (true)
    {
      if (i >= this.group.length)
      {
        this.group[paramInt].setImageResource(this.selectedState[paramInt]);
        return;
      }
      if (i != paramInt)
        this.group[i].setImageResource(this.defaultState[i]);
      ++i;
    }
  }
}
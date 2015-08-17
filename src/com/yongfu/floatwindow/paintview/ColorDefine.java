package com.yongfu.floatwindow.paintview;




import com.yongfu.floatwindow.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

public class ColorDefine
{
  public static final int BACKGROUND_COLOR = Color.parseColor("#25000000");//("#BB000000");
  public static final int SELECTED_COLOR_COLOR = Color.parseColor("#33b5e5");

  //static
 // {
    //BACKGROUND_COLOR = 
  //}

  public static int[] getColors(Context paramContext)
  {
    int i = paramContext.getResources().getColor(R.color.highlighter_yellow);
    int j = paramContext.getResources().getColor(R.color.highlighter_green);
    int k = paramContext.getResources().getColor(R.color.highlighter_orange);
    int l = paramContext.getResources().getColor(R.color.highlighter_red);
    int i1 = paramContext.getResources().getColor(R.color.highlighter_blue);
    int i2 = paramContext.getResources().getColor(R.color.highlighter_purple);
    int i3 = paramContext.getResources().getColor(R.color.red);
    int i4 = paramContext.getResources().getColor(R.color.blue);
    int i5 = paramContext.getResources().getColor(R.color.green);
    return new int[] { l, k, i, j, i1, i2, i3, 
    	paramContext.getResources().getColor(R.color.orange), 
    	paramContext.getResources().getColor(R.color.yellow), 
    	i5, 
    	i4, 
    	paramContext.getResources().getColor(R.color.purple),
    	};
  }
}
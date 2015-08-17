package com.yongfu.floatwindow.penandpaper;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Settings
{
  public static final String BRUSH_COLOR = "BRUSH_COLOR";
  public static final String BRUSH_SIZE = "BRUSH_SIZE";
  public static final String BRUSH_TYPE = "BRUSH_TYPE";
  public static final int DEFAULT_BRUSH_COLOR = 11;
  public static final int DEFAULT_BRUSH_SIZE = 16;
  public static final int DEFAULT_BRUSH_TYPE = 3;
  public static final int DEFAULT_NAV_X = 0;
  public static final int DEFAULT_NAV_Y = 0;
  public static final int DEFAULT_SCREENSHOT_COUNT = 0;
  public static final String GET_SCREENSHOT_COUNT = "GET_SCREENSHOT_COUNT";
  public static final String NAV_X = "NAV_X";
  public static final String NAV_Y = "NAV_Y";
  public static final String PER_APP_DRAWING = "PER_APP_DRAWING";
  //public static final boolean PER_APP_DRAWING_DEFAULT;
  private static Settings instance;
  private boolean perAppDrawing;
  private final SharedPreferences prefs;

  public Settings(SharedPreferences paramSharedPreferences)
  {
    this.perAppDrawing = paramSharedPreferences.getBoolean("PER_APP_DRAWING", false);
    this.prefs = paramSharedPreferences;
    instance = this;
  }

  public static Settings getInstance()
  {
    if (instance != null)
      return instance;
    StartServiceActivity.app.createSettings();
    return instance;
  }

  public int getBrushColor()
  {
    return this.prefs.getInt("BRUSH_COLOR", 11);
  }

  public int getBrushSize()
  {
    return this.prefs.getInt("BRUSH_SIZE", 6);
  }

  public int getBrushType()
  {
    return this.prefs.getInt("BRUSH_TYPE", 3);
  }

  public int getNavX()
  {
    return this.prefs.getInt("NAV_X", 0);
  }

  public int getNavY()
  {
    return this.prefs.getInt("NAV_Y", 0);
  }

  public int getScreenShotCount()
  {
    return this.prefs.getInt("GET_SCREENSHOT_COUNT", 0);
  }

  public void incrScreenShotCount()
  {
    int i = 1 + getScreenShotCount();
    this.prefs.edit().putInt("GET_SCREENSHOT_COUNT", i).commit();
  }

  public boolean isPerAppDrawing()
  {
    if (instance != null)
      return instance.perAppDrawing;
    return false;
  }

  public void setBrushColor(int paramInt)
  {
    this.prefs.edit().putInt("BRUSH_COLOR", paramInt).commit();
  }

  public void setBrushSize(int paramInt)
  {
    this.prefs.edit().putInt("BRUSH_SIZE", paramInt).commit();
  }

  public void setBrushType(int paramInt)
  {
    this.prefs.edit().putInt("BRUSH_TYPE", paramInt).commit();
  }

  public void setNavX(int paramInt)
  {
    this.prefs.edit().putInt("NAV_X", paramInt).commit();
  }

  public void setNavY(int paramInt)
  {
    this.prefs.edit().putInt("NAV_Y", paramInt).commit();
  }

  public void setPerAppDrawing(boolean paramBoolean)
  {
    this.prefs.edit().putBoolean("PER_APP_DRAWING", paramBoolean).commit();
    this.perAppDrawing = paramBoolean;
  }
}
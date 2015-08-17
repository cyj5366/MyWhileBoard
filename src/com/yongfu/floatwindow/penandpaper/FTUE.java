package com.yongfu.floatwindow.penandpaper;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FTUE
{
  private static final String FTUE = "ftue";
  private static final String LAST_VERSION = "LAST_VERSION";
  private SharedPreferences prefs;

  public FTUE(SharedPreferences paramSharedPreferences)
  {
    this.prefs = paramSharedPreferences;
  }

  private boolean checkVersionTrue(int paramInt)
  {
    this.prefs.getInt("LAST_VERSION", 0);
    this.prefs.edit().putInt("LAST_VERSION", paramInt).apply();
    return true;
  }

  public boolean checkFTUE()
  {
    if (this.prefs.contains("ftue"))
      return false;
    this.prefs.edit().putBoolean("ftue", false).apply();
    return true;
  }

  public boolean checkVersionFTUE(int paramInt)
  {
    if (this.prefs.contains("LAST_VERSION"))
    {
      int i = this.prefs.getInt("LAST_VERSION", 0);
      boolean bool = false;
      if (i < paramInt)
        bool = checkVersionTrue(paramInt);
      return bool;
    }
    return checkVersionTrue(paramInt);
  }
}

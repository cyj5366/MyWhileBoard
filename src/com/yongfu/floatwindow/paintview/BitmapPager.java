package com.yongfu.floatwindow.paintview;


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.yongfu.floatwindow.penandpaper.Settings;
import java.util.HashMap;

public class BitmapPager
{
  private HashMap<String, BitmapPointer> bitmaps = new HashMap();
  private String currentApp;
  private Bitmap currentBitmap;
  int height;
  boolean isSaved = false;
  int width;

  public BitmapPager(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    this.currentBitmap = createNewBitmap();
  }

  private Bitmap clearBitmap()
  {
    this.currentBitmap.eraseColor(0);
    return this.currentBitmap;
  }

  public Bitmap createNewBitmap()
  {
   // int i=0;
   // if (this.width > this.height)
     // i = this.width;
   // while (true)
   // {
    	//i = this.height;
      return Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
      
   // }
  }

  private Bitmap loadBitmap(String paramString)
  {
    Bitmap localBitmap = null;
    if ((paramString.equals(this.currentApp)) || (!(Settings.getInstance().isPerAppDrawing())))
      localBitmap = this.currentBitmap;
    //do
    //{
      //return localBitmap;
      //if (!(this.bitmaps.containsKey(paramString)))
       // break label62;
      
      //localBitmap = ((BitmapPointer)this.bitmaps.get(paramString)).readFromDisk();
    //}
    //while (localBitmap != null);
      if(localBitmap== null)
      {
    	  localBitmap = createNewBitmap();
      }
    	  
    //return clearBitmap();
    //label62: return clearBitmap();
    return localBitmap;
  }

  public Bitmap getBitmapForApp(String paramString)
  {
	  this.currentApp = paramString;
    if (!(this.isSaved))
      saveBitmap(this.currentApp, this.currentBitmap);
    this.currentBitmap = loadBitmap(paramString);
    this.currentApp = paramString;
    this.isSaved = false;
    return this.currentBitmap;
  }
  BitmapPointer localBitmapPointer;
  public void saveBitmap(String paramString, Bitmap paramBitmap)
  {
	  
	
    if ((paramString != null) && (paramBitmap != null))
    {
      if ((this.bitmaps.containsKey(paramString)))
      {
       
               ((BitmapPointer)this.bitmaps.get(paramString)).pageToDisk(paramBitmap);
               this.isSaved = true;
      }
      else
      {
      	localBitmapPointer = new BitmapPointer(paramString);
          this.bitmaps.put(paramString, localBitmapPointer);
          localBitmapPointer.pageToDisk(paramBitmap);
          this.isSaved = true;
      }
    }
    
	  
  
    
  }

  public void saveCurrentBitmap()
  {
    saveBitmap(this.currentApp, this.currentBitmap);
  }

  public void setWidthAndHeight(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
  }
}
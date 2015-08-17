package com.yongfu.floatwindow.paintview;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.yongfu.floatwindow.penandpaper.StartServiceActivity;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BitmapPointer
{
  private String appName;
  private Bitmap bitmapStorage;
  private AsyncBitmapWrite bitmapWrite;

  public BitmapPointer(String paramString)
  {
    this.appName = paramString;
  }

  private String getFileName()
  {
	  
	  return "/storage/sdcard1/"+this.appName+".png";

    //return StartServiceActivity.CACHE_PATH + "/" + this.appName + ".png";
  }

  public void pageToDisk(Bitmap paramBitmap)
  {
    this.bitmapStorage = paramBitmap.copy(paramBitmap.getConfig(), true);
    this.bitmapWrite = new AsyncBitmapWrite();
    AsyncBitmapWrite localAsyncBitmapWrite = 
    		this.bitmapWrite.addOnPostExecute(new AsyncBitmapWrite.PostExecutable() {
				
				@Override
				public void onPostExecute(boolean paramBoolean) {
					// TODO Auto-generated method stub
					if (paramBoolean)
					{
						
					}
			         // BitmapPointer.this.appName = null;
					
				}
			});
    				
    				/*new AsyncBitmapWrite.PostExecutable(this)
    {
      public void onPostExecute()
      {
        if (paramBoolean)
          BitmapPointer.access$0(this.this$0, null);
      }
    });*/
    BitmapEntry[] arrayOfBitmapEntry = new BitmapEntry[1];
    arrayOfBitmapEntry[0] = new BitmapEntry(getFileName(), paramBitmap);
    localAsyncBitmapWrite.execute(arrayOfBitmapEntry);
  }

  public Bitmap readFromDisk()
  {
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    //localOptions.inMutable = true;
    if (this.bitmapWrite != null)
    {
    try
    {
      this.bitmapWrite.get(5L, TimeUnit.SECONDS);
      if (this.bitmapStorage != null)
       // break label72;
      return this.bitmapStorage;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
    catch (ExecutionException localExecutionException)
    {
      localExecutionException.printStackTrace();
    }
    catch (TimeoutException localTimeoutException)
    {
      localTimeoutException.printStackTrace();
    }
    
    }
    label72: return BitmapFactory.decodeFile(getFileName(), localOptions);
    
  }

  public static class BitmapEntry
  {
    public Bitmap bitmap;
    public String fileName;

    public BitmapEntry(String paramString, Bitmap paramBitmap)
    {
      this.fileName = paramString;
      this.bitmap = paramBitmap;
    }
  }
}
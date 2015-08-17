package com.yongfu.floatwindow.paintview;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class AsyncBitmapWrite extends AsyncTask<BitmapPointer.BitmapEntry, Integer, Boolean>
{
  private ArrayList<PostExecutable> executables = new ArrayList();

  public AsyncBitmapWrite addOnPostExecute(PostExecutable paramPostExecutable)
  {
    this.executables.add(paramPostExecutable);
    return this;
  }

  protected Boolean doInBackground(BitmapPointer.BitmapEntry[] paramArrayOfBitmapEntry)
  {
    int i = 0;
    if (i >= paramArrayOfBitmapEntry.length)
      return Boolean.valueOf(true);
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      paramArrayOfBitmapEntry[i].bitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
      File localFile = new File(paramArrayOfBitmapEntry[i].fileName);
      localFile.createNewFile();
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
      localFileOutputStream.write(localByteArrayOutputStream.toByteArray());
      localFileOutputStream.close();
      ++i;
    }
    catch (Exception localException)
    {
    }
    return Boolean.valueOf(false);
  }

  protected void onPostExecute(Boolean paramBoolean)
  {
    super.onPostExecute(paramBoolean);
    Iterator localIterator = this.executables.iterator();
    while (true)
    {
      if (!(localIterator.hasNext()))
        return;
      ((PostExecutable)localIterator.next()).onPostExecute(paramBoolean.booleanValue());
    }
  }

  public static abstract interface PostExecutable
  {
    public abstract void onPostExecute(boolean paramBoolean);
  }
}
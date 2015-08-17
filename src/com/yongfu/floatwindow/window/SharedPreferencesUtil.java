package com.yongfu.floatwindow.window;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 
 * 类名: SharedPreferencesUtil</br> 
 * 包名：com.yonger.util </br> 
 * 描述: 保存共享的数据工具类</br>
 * 发布版本号：</br>
 * 开发人员： Liu Yongfu</br>
 * 创建时间： 2013-6-11
 */
public class SharedPreferencesUtil {
	private SharedPreferences mSharedPreferences;

	public SharedPreferencesUtil(Context context) 
	{
		// public static final int MODE_WORLD_WRITEABLE = 2;
		//public static final int MODE_WORLD_READABLE = 1;
		mSharedPreferences = context.getSharedPreferences("YONGER_CLIENT_SAVE01",3);
	}

	public SharedPreferencesUtil(Context context,String Name) 
	{
		//Context.MODE_PRIVATE
	  mSharedPreferences = context.getSharedPreferences(Name,3);
    }
	
	
	/**
	 * 
	 * 方法名: putString</br>
	 * 详述: 保存字符串数据</br>
	 * 开发人员：Liu Yongfu</br>
	 * 创建时间：2013-6-11</br>
	 * @param key  钥匙
	 * @param value
	 */
	public void putString(String key, String value) {
		mSharedPreferences.edit().putString(key, value).commit();
	}
	
	public void putString(String key,String value,int len)
	{
		while(value.length()<len)
		{
			value = "0"+value;
		}
		mSharedPreferences.edit().putString(key, value).commit();
	}


	/**
	 * 
	 * 方法名: getString</br>
	 * 详述: 获取字符串数据</br>
	 * 开发人员：Liu Yongfu</br>
	 * 创建时间：2013-6-11</br>
	 * @param key 钥匙
	 * @param defValue  默认值
	 * @return
	 */
	public String getString(String key, String... defValue) {
		if (defValue.length > 0){
			return mSharedPreferences.getString(key, defValue[0]);
		}else{
			return mSharedPreferences.getString(key, "");
		}
	}
	
	/**
	 * 
	 * 方法名: putInt</br>
	 * 详述: 保存整型数据</br>
	 * 开发人员：Liu Yongfu</br>
	 * 创建时间：2013-6-11</br>
	 * @param key  钥匙
	 * @param value 值
	 */
	public void putInt(String key, int value) {
		mSharedPreferences.edit().putInt(key, value).commit();
	}

	/**
	 * 
	 * 方法名: getInt</br>
	 * 详述: 获取整型数据</br>
	 * 开发人员：Liu Yongfu</br>
	 * 创建时间：2013-6-11</br>
	 * @param key 钥匙
	 * @param defValue 默认值
	 * @return
	 */
	public int getInt(String key, int...  defValue) {
    int jj=0;
		if (defValue.length > 0){
			jj = mSharedPreferences.getInt(key, defValue[0]);
      return jj;
    }else{
      return mSharedPreferences.getInt(key, 0);
    }
	}

	/**
	 * 
	 * 方法名: putBoolean</br>
	 * 详述: 保存布尔值数据</br>
	 * 开发人员：Liu Yongfu</br>
	 * 创建时间：2013-6-11</br>
	 * @param key 钥匙
	 * @param value  值
	 */
	public void putBoolean(String key, Boolean value) {
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	/**
	 * 
	 * 方法名: getBoolean</br>
	 * 详述: 获取布尔值数据</br>
	 * 开发人员：Liu Yongfu</br>
	 * 创建时间：2013-6-11</br>
	 * @param key  钥匙
	 * @param defValue 默认值
	 * @return
	 */
	public Boolean getBoolean(String key, Boolean... defValue) {
		if (defValue.length > 0){
			return mSharedPreferences.getBoolean(key, defValue[0]);
		}else{
			return mSharedPreferences.getBoolean(key, false);
		}
	}
	
}

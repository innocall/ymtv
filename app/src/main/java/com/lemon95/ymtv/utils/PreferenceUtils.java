package com.lemon95.ymtv.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils
{

	private static SharedPreferences mPreferences;

	private static SharedPreferences getSp(Context context)
	{
		if (mPreferences == null)
		{
			mPreferences = context.getSharedPreferences("configSP",
														Context.MODE_PRIVATE);
		}
		return mPreferences;
	}

	/**
	 * 获得boolean类型的值，没有时默认值为false
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key)
	{
		return getBoolean(context, key, false);
	}

	/**
	 * 获得boolean类型的数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
										boolean defValue)
	{
		SharedPreferences sp = getSp(context);
		return sp.getBoolean(key, defValue);
	}

	/**
	 * 设置boolean类型的值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putBoolean(Context context, String key, boolean value)
	{
		SharedPreferences sp = getSp(context);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 获得String类型的值，没有时默认值为null
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context, String key)
	{
		return getString(context, key, null);
	}

	/**
	 * 获得String类型的数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue)
	{
		SharedPreferences sp = getSp(context);
		return sp.getString(key, defValue);
	}

	/**
	 * 设置String类型的值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putString(Context context, String key, String value)
	{
		SharedPreferences sp = getSp(context);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 获得int类型的值，没有时默认值为-1
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getInt(Context context, String key)
	{
		return getInt(context, key, -1);
	}

	/**
	 * 获得String类型的数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(Context context, String key, int defValue)
	{
		SharedPreferences sp = getSp(context);
		return sp.getInt(key, defValue);
	}

	/**
	 * 设置int类型的值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putInt(Context context, String key, int value)
	{
		SharedPreferences sp = getSp(context);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 获得long类型的值，没有时默认值为-1
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static long getLong(Context context, String key)
	{
		return getLong(context, key, -1);
	}

	/**
	 * 获得String类型的数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLong(Context context, String key, long defValue)
	{
		SharedPreferences sp = getSp(context);
		return sp.getLong(key, defValue);
	}

	/**
	 * 设置int类型的值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putLong(Context context, String key, long value)
	{
		SharedPreferences sp = getSp(context);
		Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static void clearAll(Context context) {
		SharedPreferences sp = getSp(context);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
}

package com.lemon95.ymtv.utils;

import java.util.Random;
import java.util.Hashtable;

/**
 * 随机数处理
 */
public class RandomSecquenceCreator {
    static Hashtable hash = new Hashtable();
    static Random rand = new Random(System.currentTimeMillis());
    static long lastRandTime = System.currentTimeMillis();

    public RandomSecquenceCreator() {
    }

    /**
     * 生成长度不限的随机数字序列号
     * @return String
     */
    public static String getId() {
        // 根据时间值，重置hash，否则hash会无限增
        if (System.currentTimeMillis()-lastRandTime>20000)
            hash.clear();
        Integer id = new Integer(0);
        synchronized (hash) {
            id = new Integer(rand.nextInt());
            while (hash.containsKey(id)) {
                id = new Integer(rand.nextInt());
            }
            // 为当前用户保留该ID
            String data = "";
            hash.put(id, data);
        }
        lastRandTime = System.currentTimeMillis();
        return System.currentTimeMillis() + "" + Math.abs(id.intValue());
    }

    /**
     * 生成长度在length之内的随机数字序列号
     * @param length int
     * @return String
     */
    public static String getId(int length) {
        if (System.currentTimeMillis()-lastRandTime>20000)
            hash.clear();
        Integer id = new Integer(0);
        String strId = "";
        synchronized (hash) {
            id = new Integer(rand.nextInt());
            if (length > 15)
                strId = System.currentTimeMillis() + "" + Math.abs(id.intValue());
            else
                strId = "" + Math.abs(id.intValue());
            if (strId.length() > length)
                strId = strId.substring(0, length);
            while (hash.containsKey(strId)) {
                id = new Integer(rand.nextInt());
                if (length > 15)
                    strId = System.currentTimeMillis() + "" + Math.abs(id.intValue());
                else
                    strId = "" + Math.abs(id.intValue());
                if (strId.length() > length)
                    strId = strId.substring(0, length);
            }
            // 为当前用户保留该ID
            String data = "";
            hash.put(strId, data);
        }
        lastRandTime = System.currentTimeMillis();
        return strId;
    }
    
    
    public synchronized static String getRandomCode(int length){
    	String sRand = ""; 
		for (int i = 0;i < length; i++) { 
			Random random = new Random();
			String rand = String.valueOf(random.nextInt(10)); 
			sRand += rand;
		}
		return sRand;
    }
    
    public static void main(String[] args) {
		String str = getRandomCode(4);
//		MD5Encrypt md5 = new MD5Encrypt();
//		str = md5.getMD5ofStr(str);
		System.out.println(str);
	}

    public static int getRandom(int le) {
        Random random = new Random();
        int result=random.nextInt(le); // 返回[0,10)集合中的整数，注意不包括10
        return result;              // +1后，[0,10)集合变为[1,11)集合，满足要求
    }
    
    public static int getHeight() {
    	int []height = {150,160,170,180,200,210,220,230,240,250};
    	Random random = new Random();
    	int i = random.nextInt(10);
    	return height[i];
    }
}

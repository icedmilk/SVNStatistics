package com.hp.log.access;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Description: A class which is designed to resolved the access log
 * 
 * @author: Lu, Cheng
 *
 */
public class AccessLogResolver
{
	// Acceptable time gap between two operations
	private final static short timeGap = 30;

	/**
	 * Description: Judge whether two date is almost equivalent
	 * 
	 * @param d1
	 *            : date1
	 * @param d2
	 *            : date2
	 * @return: comparison result
	 */
	public static boolean isTimeNearlyEquals(Date d1, Date d2)
	{
		long diff = d1.getTime() - d2.getTime();
		long gap = diff / (1000);
		if (Math.abs(gap) < timeGap)
			return true;
		return false;
	}

	/**
	 * Description: Judge whether two keys is almost equivalent
	 * 
	 * @param key
	 *            : Old key
	 * @param newKey
	 *            : newKey
	 * @return: comparison result
	 * @throws: ParseException
	 */
	public static boolean isStringArrayEquals(String[] key, String[] newKey)
			throws ParseException
	{
		if (key.length != newKey.length)
			return false;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = df.parse(key[3]);
		Date d2 = df.parse(newKey[3]);
		if (key[0].equals(newKey[0]) && key[1].equals(newKey[1])
				&& key[2].equals(newKey[2]) && isTimeNearlyEquals(d1, d2))
			return true;
		return false;
	}

	/**
	 * Description: Resolve log process
	 * 
	 * @param str
	 *            : String to resolve
	 * @param hm
	 *            : Hash map that save the result sets
	 * @throws: ParseException
	 */
	public static void resolveLog(String str,
			HashMap<String[], ArrayList<String[]>> hm) throws ParseException
	{
		String[] splitedStr = str.split("\\|");
		// for (String s : splitedStr)
		// System.out.print(s + " ");
		// System.out.println();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// keys to match
		String[] newKey = new String[] { splitedStr[0], splitedStr[1],
				splitedStr[3], splitedStr[6], df.format(new Date()) };
		Set<String[]> sets = hm.keySet();
		Iterator<String[]> iterator = sets.iterator();
		while (iterator.hasNext())
		{
			String[] key = iterator.next();
			if (isStringArrayEquals(key, newKey))
			{
				hm.get(key).add(splitedStr);
				ArrayList<String[]> tempList = hm.get(key);
				hm.remove(key);
				hm.put(newKey, tempList);
				return;
			}
		}

		ArrayList<String[]> temp = new ArrayList<String[]>();
		temp.add(splitedStr);
		hm.put(newKey, temp);

	}

}

package com.hp.log.svn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Description: A class which is designed to resolved the subversion log
 * 
 * @author: Lu, Cheng
 *
 */
public class SubversionLogResolver
{
	/**
	 * Description: Resolve log process
	 * 
	 * @param str
	 *            : String to resolve
	 * @param hm
	 *            : Array list that save the result sets
	 */
	public static void resolveLog(String str, ArrayList<String[]> svnLog)
	{
		String[] splitedStr = str.split("\\|");
		// for (String s : splitedStr)
		// System.out.print(s + " ");
		// System.out.println();

		ArrayList<String> temp = new ArrayList<String>();

		for (int i = 0; i < splitedStr.length; i++)
			temp.add(splitedStr[i]);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		temp.add(df.format(new Date()));

		final int size = temp.size();
		String[] tempStr = new String[size];
		for (int i = 0; i < size; i++)
			tempStr[i] = new String(temp.get(i));

		svnLog.add(tempStr);

	}

}

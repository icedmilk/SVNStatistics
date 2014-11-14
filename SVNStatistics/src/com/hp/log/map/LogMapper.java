package com.hp.log.map;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.hp.main.StartStatistics;

/**
 * Description: A class which is designed to map the logs
 * 
 * @author: Lu, Cheng
 *
 */
public class LogMapper
{
	private static final int timeGapToResolve = 3;
	private static final int timeGapToExpire = 10;

	/**
	 * Description: Judge whether the two operations are the same
	 * 
	 * @param hm
	 *            : Access logs which is saved in the hash map
	 * @param svnLog
	 *            : Subversion logs which is saved in the array list
	 * @return: Are those the same?
	 */
	public static boolean isTheSameOperation(String[] hm, String[] svnLog)
	{
		if (hm[0].equals(svnLog[0]) && hm[1].equals(svnLog[1])
				&& hm[2].equals(svnLog[3]) && hm[3].equals(svnLog[5]))
			return true;
		return false;
	}

	/**
	 * Description: Judge whether two date is almost equivalent
	 * 
	 * @param d1
	 *            : date1
	 * @param d2
	 *            : date2
	 * @return: comparison result
	 * @throws ParseException
	 */
	public static boolean isTimeGapEnough(String date, int seconds)
			throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date2 = sdf.format(new Date());

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = df.parse(date);
		Date d2 = df.parse(date2);

		long diff = d1.getTime() - d2.getTime();
		long gap = diff / (1000);
		if (Math.abs(gap) > seconds)
			return true;
		return false;
	}

	/**
	 * Description: Map the access log and subversion log
	 * 
	 * @param hm
	 *            : access log
	 * @param svnLog
	 *            : subversion log
	 * @throws ParseException
	 */
	public static void LogMap(HashMap<String[], ArrayList<String[]>> hm,
			ArrayList<String[]> svnLog) throws ParseException
	{
		for (int i = 0; i < svnLog.size(); i++)
		{
			Set<String[]> sets = hm.keySet();
			Iterator<String[]> it = sets.iterator();

			while (it.hasNext())
			{
				String[] key = it.next();
				if (isTheSameOperation(key, svnLog.get(i))
						&& isTimeGapEnough(svnLog.get(i)[9], timeGapToResolve))
				{
					System.out.println(svnLog.get(i)[5]);
					System.out.println(hm.get(key).size());

					StartStatistics.hm.remove(key);

					StartStatistics.svnLog.remove(svnLog.get(i));
					break;
				}
				else if (!it.hasNext())
				{
					System.out.println(svnLog.get(i)[5]);
					System.out.println("Not Matched");
				}
			}
		}

		/**
		 * You can remove the unused logs here
		 */
		System.out.println("¡¾");
		System.out.println("HashMap remains: ");
		Set<String[]> sets = hm.keySet();
		Iterator<String[]> it = sets.iterator();

		while (it.hasNext())
		{
			String[] key = it.next();
			if (isTimeGapEnough(key[4], timeGapToExpire))
				StartStatistics.hm.remove(key);
			System.out.println(key[3] + " " + hm.get(key).size());
		}
		System.out.println("¡¿");

	}
}

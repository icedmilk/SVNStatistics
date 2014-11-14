package com.hp.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import com.hp.log.access.AccessLogReader;
import com.hp.log.map.LogMapper;
import com.hp.log.svn.SubversionLogReader;


/* * * * * * * * * * * * * * * 
 * Hey, start from here!!!	 *
 * * * * * * * * * * * * * * * 
 * @JDK version: 1.7
 * @author Lu, Cheng
 *
 */
public class StartStatistics
{

	public static ArrayList<String[]> svnLog = new ArrayList<String[]>();
	public static HashMap<String[], ArrayList<String[]>> hm = new HashMap<String[], ArrayList<String[]>>();

	public void func1() throws InterruptedException
	{
		synchronized (this)
		{
//			System.out.println("func1--readsvn");
			SubversionLogReader.readFileByLines(
					"C:\\Users\\clu2\\Desktop\\subversion.log", svnLog, 0);
//			Thread.sleep(2000);
		}
	}

	public void func2() throws InterruptedException
	{
		synchronized (this)
		{
//			System.out.println("func2--readaccess");
			try
			{
				AccessLogReader.readFileByLines(
						"C:\\Users\\clu2\\Desktop\\access.log", hm, 0);
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
//			Thread.sleep(2000);
		}
	}

	@SuppressWarnings("unchecked")
	public void func3() throws InterruptedException, ParseException
	{
		synchronized (this)
		{
//			System.out.println("3");
			ArrayList<String[]> svn = (ArrayList<String[]>) svnLog.clone();
			HashMap<String[], ArrayList<String[]>> access = (HashMap<String[], ArrayList<String[]>>) hm
					.clone();
			LogMapper.LogMap(access, svn);
			// Thread.sleep(2000);
		}
	}

	// @SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		final StartStatistics rs = new StartStatistics();
		new Thread()
		{
			public void run()
			{
				try
				{
					rs.func1();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
		new Thread()
		{
			public void run()
			{
				try
				{
					rs.func2();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
		while (true)
		{
			new Thread("test")
			{
				public void run()
				{
					try
					{
						rs.func3();
					}
					catch (InterruptedException | ParseException e)
					{
						e.printStackTrace();
					}
				}
			}.start();
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}

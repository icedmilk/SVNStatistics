package com.hp.log.access;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Set;

/**
 * Description: A class which is designed to read the access log by line
 * @author:		Lu, Cheng
 *
 */
public class AccessLogReader
{
	
	
	/**
	 * Description: 			Read the access file by lines
	 * @param fileName:			FileName to read
	 * @param recorderCount:	the count of recorder to read
	 * @throws:					ParseException
	 */
	public static void readFileByLines(String fileName, HashMap<String[], ArrayList<String[]>> hm, int recorderCount) throws ParseException
	{
		
		File file = new File(fileName);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			
			recorderCount = recorderCount == 0 ? 0x7fffffff : recorderCount;
			
			int i = 0;

			while (i++ < recorderCount
					&& ((tempString = reader.readLine()) != null))
			{
				AccessLogResolver.resolveLog(tempString, hm);
			}

			reader.close();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) throws ParseException
//	{
//		readFileByLines("C:\\Users\\clu2\\Desktop\\access.log",
//				 "false", 10000);
//		Set<String[]> set2 = hm.keySet();
//		Iterator<String[]> it = set2.iterator();
//		while(it.hasNext())
//			System.out.println(it.next()[2]);
//	}

}

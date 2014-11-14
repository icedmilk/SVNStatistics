package com.hp.log.svn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Description: A class which is designed to read the subversion log by line
 * @author:		Lu, Cheng
 *
 */
public class SubversionLogReader
{
	
	/**
	 * Description: 			Read the access file by lines
	 * @param fileName:			FileName to read
	 * @param recorderCount:	the count of recorder to read
	 * @throws:					ParseException
	 */
	public static void readFileByLines(String fileName, ArrayList<String[]> svnLog, int recorderCount)
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
				SubversionLogResolver.resolveLog(tempString, svnLog);
			}

			reader.close();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

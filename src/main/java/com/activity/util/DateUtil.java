package com.activity.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public DateUtil() {
		ft.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
	}

	public static String getDateFromTimestamp(Timestamp timestamp)
	{
		if(timestamp!=null)
		{
			Date date = new Date(timestamp.getTime());
		
			String dateString = ft.format(date);
		
			return dateString;
		}
		
		
		return null;
	}

}

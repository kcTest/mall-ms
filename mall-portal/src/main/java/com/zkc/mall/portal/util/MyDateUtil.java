package com.zkc.mall.portal.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class MyDateUtil {
	
	/**
	 * 从Date类型的时间中提起日期部分
	 */
	public static Date getDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
}

package com.brian.springboot.ecommerceproject.utils;

import java.util.Calendar;

public class ServiceUtils {
	/**
	 * 取得三天前跟今天時間
	 * @return
	 */
	public static long[] getBefore3DaysAndNow() {
		Calendar cal = Calendar.getInstance();
		long now = cal.getTimeInMillis();
		cal.add(Calendar.DATE, -3);
		long before3Days = cal.getTimeInMillis();
		return new long[]{before3Days, now};
	}
}

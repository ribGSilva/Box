package com.gabriel.box.application.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
	
	private static final String TIME_ZONE = "America/Sao_Paulo";
	private static final TimeZone CURRENT_TIME_ZONE = TimeZone.getTimeZone(TIME_ZONE);
	private static final SimpleDateFormat SDF_HHMM = new SimpleDateFormat("HHmm");
	private static final SimpleDateFormat SDF_HHMM_DDMMYYYY = new SimpleDateFormat("HH:mm dd/MM/YYYY");
	private static final SimpleDateFormat SDF_DDMMYYYY = new SimpleDateFormat("dd/MM/YYYY");
	
	public static Integer getCurrentIntTime() {
		Date currentDate = getCurrentDate();
		
		return Integer.parseInt(SDF_HHMM.format(currentDate));
	}

	public static Date getCurrentDate() {
		return getCurrentCalendar().getTime();
	}

	public static Calendar getCurrentCalendar() {
		return Calendar.getInstance(CURRENT_TIME_ZONE);
	}
	
	public static String intTimeToStringTime(Integer intTime) {
		if (intTime == null)
			return null;

		return String.format("%d:%02d", intTime/100, intTime%100);
	}
	
	public static String formatDate(Date date) {
		if (date == null) return "";
		return SDF_HHMM_DDMMYYYY.format(date);
	}
	
	public static String formatOnlyDate(Date date) {
		if (date == null) return "";
		return SDF_DDMMYYYY.format(date);
	}
	
	public static Date addDaysToCurrentDate(int days) {
		Calendar today = getCurrentCalendar();
		
		today.add(Calendar.DAY_OF_MONTH, days);
		
		return today.getTime();
	}
}

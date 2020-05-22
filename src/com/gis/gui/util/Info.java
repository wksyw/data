package com.gis.gui.util;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Info {

	private Calendar cal;
	private DecimalFormat df = new DecimalFormat("00");

	private static Info info = new Info();

	private Info() {
	}

	public static Info getInstance() {
		return info;
	}

	public void warn(String massage) {
		System.out.println(getPrefix("warn") + massage);
	}
	
	public void info(String massage) {
		System.out.println(getPrefix("info") + massage);
	}
	
	public void err(String massage) {
		System.out.println(getPrefix("error") + massage);
	}

	public String getPrefix(String type) {
		cal = Calendar.getInstance();

		int h = cal.get(Calendar.HOUR_OF_DAY);
		int m = cal.get(Calendar.MINUTE);
		int s = cal.get(Calendar.SECOND);

		StringBuffer prefix = new StringBuffer();
		prefix.append("[");
		prefix.append(df.format(h));
		prefix.append(":");
		prefix.append(df.format(m));
		prefix.append(":");
		prefix.append(df.format(s));
		prefix.append(" ");
		prefix.append(type);
		prefix.append("] ");

		return prefix.toString();
	}

}

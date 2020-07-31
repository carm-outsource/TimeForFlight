package com.carmwork.plugin.timeforflight.utils;

public class TimeFormat {


	public static String getTimeString(int time) {
		int temp;
		StringBuilder sb = new StringBuilder();

		temp = time / 60 / 60 % 60;
		sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");

		temp = time % 3600 / 60;
		sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");

		temp = time % 3600 % 60;
		sb.append((temp < 10) ? "0" + temp : "" + temp);

		return sb.toString();
	}


}

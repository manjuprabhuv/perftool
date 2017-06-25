package com.hawkeye.utils;

public class HawkEyeUtils {

	public static boolean isLong(String val) {

		try {
			Long.parseLong(val);
			return true;
		} catch (Exception e) {
			System.out.println("Numbers only");
			return false;
		}
	}

}

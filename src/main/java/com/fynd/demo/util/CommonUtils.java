package com.fynd.demo.util;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class CommonUtils {

	public static boolean isNumeric(String str) {
		if(str != null && !str.isEmpty()) {
			NumberFormat formatter = NumberFormat.getInstance();
			ParsePosition pos = new ParsePosition(0);
			formatter.parse(str, pos);
			return str.length() == pos.getIndex();
		}		
		return false;
	}
}

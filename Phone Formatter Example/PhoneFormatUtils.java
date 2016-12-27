package edu.ucla.dt.studentweb.mvc.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import org.springframework.util.StringUtils;

public final class PhoneFormatUtils {

	static String exStr;

	public static String phoneFormat(String str) {

		if (!StringUtils.hasText(str)) {
			return str;
		}

		if (str.length() < 11) {
			MaskFormatter mf;

			try {
				str = str.replaceAll("[^0-9]", "");
				mf = new MaskFormatter("###-###-####");
				mf.setValueContainsLiteralCharacters(false);
				exStr = mf.valueToString(str);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			exStr = str;
		}

		return exStr;

	}
}

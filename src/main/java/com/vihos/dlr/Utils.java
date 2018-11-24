package com.vihos.dlr;

import android.content.Context;

public class Utils {

	public static String processString(Context context, CharSequence text) {
		return processString(context, text.toString());
	}

	public static String processString(Context context, String text) {
		if (text.startsWith("#")) {
			int slashIndex = text.indexOf("/");

			if (slashIndex == -1 || slashIndex == 1 || slashIndex == text.length() -1)
				return text;

			String type = text.substring(1, slashIndex);
			String resourceName = text.substring(slashIndex + 1);

			int id = context.getResources().getIdentifier(resourceName, type, context.getPackageName());

			if (id != 0)
				return context.getResources().getString(id);
		}

		return text;
	}

	public static boolean isLocalizable(String string) {
		if (!string.startsWith("#"))
			return false;

		int slashIndex = string.indexOf("/");
		return slashIndex != -1 && slashIndex != 1 && slashIndex != string.length() - 1;
	}
}

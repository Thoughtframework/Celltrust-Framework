package com.ProjecName.base;

public class StringFunctions 
{
	public static String capitalizeFully(String str, char... delimiters) 
	{
		int delimLen = delimiters == null ? -1 : delimiters.length;
		if (isEmpty(str) || (delimLen == 0)) 
		{
			return str;
		}
		str = str.toLowerCase();
		return capitalize(str, delimiters);
	}

	public static boolean isBlank(CharSequence cs) 
	{
		int strLen;
		if ((cs == null) || ((strLen = cs.length()) == 0)) 
		{
			return true;
		}
		for (int i = 0; i < strLen; i++) 
		{
			if (!Character.isWhitespace(cs.charAt(i))) 
			{
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(CharSequence cs) 
	{
		return (cs == null) || (cs.length() == 0);
	}

	public static boolean isNotEmpty(CharSequence cs) 
	{
		return !isEmpty(cs);
	}

	public static String capitalize(String str, char... delimiters) 
	{
		int delimLen = delimiters == null ? -1 : delimiters.length;
		if (isEmpty(str) || (delimLen == 0)) 
		{
			return str;
		}
		char[] buffer = str.toCharArray();
		boolean capitalizeNext = true;
		for (int i = 0; i < buffer.length; i++) 
		{
			char ch = buffer[i];
			if (isDelimiter(ch, delimiters)) 
			{
				capitalizeNext = true;
			} 
			else if (capitalizeNext) 
			{
				buffer[i] = Character.toTitleCase(ch);
				capitalizeNext = false;
			}
		}
		return new String(buffer);
	}

	private static boolean isDelimiter(char ch, char[] delimiters) 
	{
		if (delimiters == null) 
		{
			return Character.isWhitespace(ch);
		}
		for (char delimiter : delimiters) 
		{
			if (ch == delimiter) 
			{
				return true;
			}
		}
		return false;
	}

	public static int countMatches(CharSequence str, char ch) 
	{
		if (isEmpty(str)) 
		{
			return 0;
		}
		int count = 0;
		for (int i = 0; i < str.length(); i++) 
		{
			if (ch == str.charAt(i)) 
			{
				count++;
			}
		}
		return count;
	}

	public static int countMatches(CharSequence str, CharSequence sub) 
	{
		if ((isEmpty(str)) || (isEmpty(sub))) 
		{
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = indexOf(str, sub, idx)) != -1) 
		{
			count++;
			idx += sub.length();
		}
		return count;
	}

	static int indexOf(CharSequence cs, CharSequence searchChar, int start) 
	{
		return cs.toString().indexOf(searchChar.toString(), start);
	}
}

package org.devil.jtools.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	// 首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 将基础对象转换为String <功能详细描述>
	 * 
	 * @param obj
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws
	 *                [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String transformObjToString(Object obj) {
		String result = null;
		if (null == obj) {
			result = "";
		} else if (Date.class == obj.getClass()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = sdf.format(obj);
		} else {
			result = String.valueOf(obj);
		}
		return result;
	}

	/**
	 * 将String转成对象 <功能详细描述>
	 * 
	 * @param data
	 * @param clazz
	 * @return [参数说明]
	 * 
	 * @return Object [返回类型说明]
	 * @throws ParseException
	 * @exception throws
	 *                [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> Object transformStringToObj(String data, Class<T> clazz) throws ParseException {
		Object result = null;
		if (null == data || data.equals("")) {
			result = null;
		} else if (Integer.class == clazz) {
			result = Integer.valueOf(data);
		} else if (Long.class == clazz) {
			result = Long.valueOf(data);
		} else if (String.class == clazz) {
			result = data;
		} else if (Date.class == clazz) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = sdf.parse(data);
		}
		return result;
	}
}

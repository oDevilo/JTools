package org.devil.jtools.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 字符串工具类，对字符串进行常规的处理
 * 
 * @Author:chenssy
 * @date:2014年8月5日
 */
public class StringUtils {
	
	/**
	 * 将半角的符号转换成全角符号.(即英文字符转中文字符)
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param str
	 * 			要转换的字符
	 * @return
	 */
	public static String changeToFull(String str) {
        String source = "1234567890!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=+\\|[];:'\",<.>/?";
        String[] decode = { "１", "２", "３", "４", "５", "６", "７", "８", "９", "０",
                "！", "＠", "＃", "＄", "％", "︿", "＆", "＊", "（", "）", "ａ", "ｂ",
                "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ",
                "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ",
                "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ",
                "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ",
                "Ｙ", "Ｚ", "－", "＿", "＝", "＋", "＼", "｜", "【", "】", "；", "：",
                "'", "\"", "，", "〈", "。", "〉", "／", "？" };
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int pos = source.indexOf(str.charAt(i));
            if (pos != -1) {
                result += decode[pos];
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }
	
	/**
	 *  将字符转换为编码为Unicode，格式 为'\u0020'<br>
	 * 		  unicodeEscaped(' ') = "\u0020"<br>
	 * 		  unicodeEscaped('A') = "\u0041"
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param ch
	 * 			待转换的char 字符
	 * @return
	 */
	public static String unicodeEscaped(char ch) {
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		} else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		} else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}
	
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
	
	/**
	 * 进行toString操作，若为空，返回默认值
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param object
	 * 				要进行toString操作的对象
	 * @param nullStr
	 * 				返回的默认值
	 * @return
	 */
	public static String toString(Object object,String nullStr){
		return object == null ? nullStr : object.toString();
	}
	
	/**
	 * 将字符串重复N次，null、""不在循环次数里面 <br>
	 * 		 当value == null || value == "" return value;<br>
	 * 		 当count <= 1 返回  value
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param value
	 * 				需要循环的字符串
	 * @param count
	 * 				循环的次数
	 * @return
	 */
	public static String repeatString(String value,int count){
		if(value == null || "".equals(value) || count <= 1){
			return value;
		}
		
		int length = value.length();
		if(length == 1){          //长度为1，存在字符
			return repeatChar(value.charAt(0), count);
		}
		
		int outputLength = length * count;
		switch (length) {
		case 1:
			return repeatChar(value.charAt(0), count);
		case 2:
			char ch0 = value.charAt(0);
			char ch1 = value.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = count * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default: 
			StringBuilder buf = new StringBuilder(outputLength);
			for (int i = 0; i < count; i++) {
				buf.append(value);
			}
			return buf.toString();
		}

	}
	
	/**
	 * 将某个字符重复N次
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param ch
	 * 			需要循环的字符
	 * @param count
	 * 			循环的次数
	 * @return
	 */
	public static String repeatChar(char ch, int count) {
		char[] buf = new char[count];
		for (int i = count - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}

	/**
	 * 判断字符串是否全部都为小写
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param value
	 * 				待判断的字符串
	 * @return
	 */
	public static boolean isAllLowerCase(String value){
		if(value == null || "".equals(value)){
			return false;
		}
		for (int i = 0; i < value.length(); i++) {
			if (Character.isLowerCase(value.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断字符串是否全部大写
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param value
	 * 				待判断的字符串
	 * @return
	 */
	public static boolean isAllUpperCase(String value){
		if(value == null || "".equals(value)){
			return false;
		}
		for (int i = 0; i < value.length(); i++) {
			if (Character.isUpperCase(value.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 反转字符串
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param value
	 * 				待反转的字符串
	 * @return
	 */
	public static String reverse(String value){
		if(value == null){
			return null;
		}
		return new StringBuffer(value).reverse().toString();
	}
	
	/**
	 * @desc:截取字符串，支持中英文混乱，其中中文当做两位处理
	 * @autor:chenssy
	 * @date:2014年8月10日
	 *
	 * @param resourceString
	 * @param length
	 * @return
	 */
	public static String subString(String resourceString,int length){
		String resultString = "";
		if (resourceString == null || "".equals(resourceString) || length < 1) {
			return resourceString;
		}

		if (resourceString.length() < length) {
			return resourceString;
		}

		char[] chr = resourceString.toCharArray();
		int strNum = 0;
		int strGBKNum = 0;
		boolean isHaveDot = false;

		for (int i = 0; i < resourceString.length(); i++) {
			if (chr[i] >= 0xa1){// 0xa1汉字最小位开始
				strNum = strNum + 2;
				strGBKNum++;
			} else {
				strNum++;
			}

			if (strNum == length || strNum == length + 1) {
				if (i + 1 < resourceString.length()) {
					isHaveDot = true;
				}
				break;
			}
		}
		resultString = resourceString.substring(0, strNum - strGBKNum);
		if (isHaveDot) {
			resultString = resultString + "...";
		}

		return resultString;
	}
	
	/**
	 * 
	 * @autor:chenssy
	 * @date:2014年8月10日
	 *
	 * @param htmlString
	 * @param length
	 * @return
	 */
	public static String subHTMLString(String htmlString,int length){
		return subString(delHTMLTag(htmlString), length);
	}
	
	/**
	 * 过滤html标签，包括script、style、html、空格、回车标签
	 * @autor:chenssy
	 * @date:2014年8月10日
	 *
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr){
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
	    String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
	    String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
	    String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符 
	    
	    Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
        Matcher m_script = p_script.matcher(htmlStr);  
        htmlStr = m_script.replaceAll(""); // 过滤script标签  
  
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
        Matcher m_style = p_style.matcher(htmlStr);  
        htmlStr = m_style.replaceAll(""); // 过滤style标签  
  
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
        Matcher m_html = p_html.matcher(htmlStr);  
        htmlStr = m_html.replaceAll(""); // 过滤html标签  
  
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
        Matcher m_space = p_space.matcher(htmlStr);  
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
        
        return htmlStr.trim(); // 返回文本字符串
	}
	
}

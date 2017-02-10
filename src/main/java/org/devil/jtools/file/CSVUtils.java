package org.devil.jtools.file;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.devil.jtools.base.StringUtils;

/**
 * CSV导入导出工具
 * 
 * 如果字段中有逗号（,），该字段使用双引号（”）括起来； 如果该字段中有双引号，该双引号前要再加一个双引号，然后把该字段使用双引号括起来。
 * 
 * 字段处理前 字段处理后 abc,d2 "abc,d2" ab"c,d2 "ab""c,d2" "abc """abc" "" """"""
 * 
 * 如果字段中有两个挨着的双引号，比如：aaa" "ccc。这种情况不用特殊处理。 如果你把它处理成："aaa"" ""ccc" Excel也能正常打开
 * 
 * @author Devil
 *
 */
public class CSVUtils {

	/**
	 * 分割符
	 */
	public static final String SEPARATOR = ",";

	/**
	 * 默认编码格式
	 */
	public static final String DEFAULT_ENCODEING = "UTF-8";

	/**
	 * 文件后缀
	 */
	public static final String FILE_SUFFIX = ".csv";

	/**
	 * 导出
	 * 
	 * @param file
	 *            csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList
	 *            数据
	 * @return
	 * @throws IOException
	 */
	public static boolean exportCsvByList(File file, List<List<String>> dataList, String encoding) throws IOException {
		if (null == encoding || encoding.equals("")) {
			encoding = DEFAULT_ENCODEING;
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		if (dataList != null && dataList.size() > 0) {
			for (Integer i = 0; i < dataList.size(); i++) {
				List<String> element = dataList.get(i); // 获取一行
				if (null != element && element.size() > 0) {
					for (Integer j = 0; j < element.size(); j++) {
						String value = element.get(i);
						if (value.contains("\"")) { // 若发现有双引号,需要将字符串中的一个双引号替换为两个,并且需前后加双引号
							value = value.replaceAll("\"", "\"\"");
							value = "\"" + value + "\"";
						} else if (value.contains(",")) { // 若发现有逗号,需前后加引号
							value = "\"" + value + "\"";
						}

						if (j == element.size() - 1) {
							bw.write(value + "\n");
						} else {
							bw.write(value + SEPARATOR);
						}
					}
				}
			}
		}
		bw.close();
		return true;
	}

	/**
	 * 导出
	 * 
	 * @param file
	 *            csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList
	 *            数据
	 * @return
	 * @throws IOException
	 */
	public static boolean exportCsvByArr(File file, List<String[]> dataList, String encoding) throws IOException {
		if (null == encoding || encoding.equals("")) {
			encoding = DEFAULT_ENCODEING;
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		if (dataList != null && dataList.size() > 0) {
			for (Integer i = 0; i < dataList.size(); i++) {
				String[] element = dataList.get(i);// 一行元素
				if (element != null && element.length > 0) {
					for (Integer j = 0; j < element.length; j++) {
						String value = element[j];
						if (value.contains("\"")) { // 若发现有双引号,需要将字符串中的一个双引号替换为两个,并且需前后加双引号
							value = value.replaceAll("\"", "\"\"");
							value = "\"" + value + "\"";
						} else if (value.contains(",")) { // 若发现有逗号,需前后加引号
							value = "\"" + value + "\"";
						}

						if (j == element.length - 1) {
							bw.write(value + "\n");
						} else {
							bw.write(value + SEPARATOR);
						}
					}
				}
			}
		}
		bw.close();
		return true;
	}

	/**
	 * 导出
	 * 
	 * @param file
	 *            csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList
	 *            数据
	 * @return
	 * @throws IOException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static <T> boolean exportCsvByObj(File file, List<T> dataList, Class<T> clazz, String encoding)
			throws Exception {
		if (null == encoding || encoding.equals("")) {
			encoding = DEFAULT_ENCODEING;
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		if (dataList != null && dataList.size() > 0) {
			for (Integer i = 0; i < dataList.size(); i++) {
				T element = dataList.get(i);// 一行元素
				Field[] fields = clazz.getDeclaredFields();
				for (int j = 0; j < fields.length; j++) {
					Method method = null;
					try {
						PropertyDescriptor prop = new PropertyDescriptor(fields[j].getName(), clazz);
						method = prop.getReadMethod();
					} catch (Exception e) {
						// TODO: handle exception
					}
					if (null != method) { // 如果不为空，则执行get方法
						String value = StringUtils.transformObjToString(method.invoke(element));
						if (value.contains("\"")) { // 若发现有双引号,需要将字符串中的一个双引号替换为两个,并且需前后加双引号
							value = value.replaceAll("\"", "\"\"");
							value = "\"" + value + "\"";
						} else if (value.contains(",")) { // 若发现有逗号,需前后加引号
							value = "\"" + value + "\"";
						}

						if (j == fields.length - 1) {
							bw.write(value + "\n");
						} else {
							bw.write(value + SEPARATOR);
						}
					}

				}
			}
		}
		bw.close();
		return true;
	}

	/**
	 * 读取
	 * 
	 * @param file
	 *            csv文件(路径+文件)
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> importCsv(File file, String encoding) throws IOException {
		if (encoding == null) {
			encoding = DEFAULT_ENCODEING;
		}
		List<String[]> result = new ArrayList<String[]>();
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			if (!lineTxt.trim().equals("")) {
				String[] strArr = lineTxt.trim().split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1); // 双引号内的逗号不分割,双引号外的逗号进行分割
				for (int i = 0; i < strArr.length; i++) {
					strArr[i] = clearStartAndEndQuote(strArr[i]);
				}
				result.add(strArr);
			}

		}
		read.close();
		return result;
	}

	public static <T> List<T> importCsvByObj(File file, Class<T> clazz, String encoding) throws Exception {
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try{
			if (encoding == null) {
				encoding = DEFAULT_ENCODEING;
			}
			read = new InputStreamReader(new FileInputStream(file), encoding);
			bufferedReader = new BufferedReader(read);
			String lineTxt;

			Field[] fields = clazz.getDeclaredFields();
			List<Method> methods = new ArrayList<Method>();
			for (Field field : fields) {
				Method method = null;
				PropertyDescriptor prop = new PropertyDescriptor(field.getName(), clazz);
				method = prop.getWriteMethod();
				if (method != null)
					methods.add(method);
			}

			List<T> result = new ArrayList<T>();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				if (!lineTxt.trim().equals("")) {
					String[] strArr = lineTxt.trim().split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1); // 双引号内的逗号不分割,双引号外的逗号进行分割
					if (strArr.length != methods.size())
						throw new RuntimeException("methods length is not match csv size");

					T obj = clazz.newInstance();
					for (int i = 0; i < strArr.length; i++) {
						// strArr[i] = clearStartAndEndQuote(strArr[i]);
						String data = clearStartAndEndQuote(strArr[i]);
						Class<?>[] types = methods.get(i).getParameterTypes();
						methods.get(i).invoke(obj, StringUtils.transformStringToObj(data, types[0]));
					}
					result.add(obj);
				}
			}
			
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			bufferedReader.close();
			read.close();
		}
	}

	/**
	 * 去掉字符串前后字符串 <功能详细描述>
	 * 
	 * @param str
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws
	 *                [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String clearStartAndEndQuote(String str) {
		if (str != null && str.length() >= 2) {
			if (str.indexOf("\"") == 0)
				str = str.substring(1, str.length()); // 去掉第一个 "
			if (str.lastIndexOf("\"") == (str.length() - 1))
				str = str.substring(0, str.length() - 1); // 去掉最后一个 "

			str = str.replaceAll("\"\"", "\"");// 把两个双引号换成一个双引号
		}
		return str;
	}

}
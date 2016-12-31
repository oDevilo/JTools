package org.devil.jtools.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.devil.jtools.file.CSVUtils;
import org.junit.Test;

public class CSVTest {
	/**
	 * CSV导出
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void exportCsv() throws IOException {
		List<List<String>> dataList = new ArrayList<List<String>>();
		List<String> data = new ArrayList<>();
		data.add("abc,d2");
		data.add("ab\"c,d2");
		data.add("\"abc");
		data.add("\"\"");
		data.add("aaa\" \"ccc");
		data.add("\"aaa\"\" \"\"ccc\"");
		dataList.add(data);
		boolean isSuccess = CSVUtils.exportCsvByList(new File("D:/ljq.csv"), dataList,null);
		System.out.println(isSuccess);
	}
	
	@Test
	public void exportCsvObj() throws IOException {
		List<List<String>> dataList = new ArrayList<List<String>>();
		List<String> data = new ArrayList<>();
		data.add("abc,d2");
		data.add("ab\"c,d2");
		data.add("\"abc");
		data.add("\"\"");
		data.add("aaa\" \"ccc");
		data.add("\"aaa\"\" \"\"ccc\"");
		dataList.add(data);
		boolean isSuccess = CSVUtils.exportCsvByList(new File("D:/ljq.csv"), dataList,null);
		System.out.println(isSuccess);
	}

	/**
	 * CSV导出
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void importCsv() throws IOException {
		List<String[]> dataList = CSVUtils.importCsv(new File("D:/ljq.csv"), null);
		if (dataList != null && !dataList.isEmpty()) {
			for (String[] data : dataList) {
				for (String string : data) {
					System.out.println(string);
				}
			}
		}
	}

}

package com.fynd.demo;

import java.util.List;
import java.util.Properties;

import com.fynd.demo.modal.ItemInfo;
import com.fynd.demo.parser.IParser;
import com.fynd.demo.parser.impl.CSVParser;
import com.fynd.demo.util.LogUtil;

public class Main {
	private final String TAG = Main.class.getSimpleName();
	private Properties properties;
	
	public Main() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("fynd.properties"));
		} catch (Exception e) {
			LogUtil.logError(TAG, e.getMessage());
		}
	}
	
	public void init() {
		IParser<String, List<ItemInfo>> parser = new CSVParser();
		parser.setData(properties.getProperty("filename"));
		parser.parse();
		List<ItemInfo> list = parser.getData();
		System.out.println(list.toString());
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.init();
	}
}

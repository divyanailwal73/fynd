package com.fynd.demo.parser.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fynd.demo.modal.ItemInfo;
import com.fynd.demo.modal.SizeQuantity;
import com.fynd.demo.parser.IParser;
import com.fynd.demo.util.CommonUtils;
import com.fynd.demo.util.LogUtil;

public class CSVParser implements IParser<String,List<ItemInfo>> {

	private final String TAG = CSVParser.class.getSimpleName();
	private String filePath;
	private List<ItemInfo> infoList;
	public static ArrayList<String> sizeArrayList;
	private int quantityArraySize ;
	private final String delimiter;
	
	public CSVParser() {
		infoList = new ArrayList<ItemInfo>();
		quantityArraySize = ItemInfo.SIZE_INDEX_END - ItemInfo.SIZE_INDEX_START + 1;
		sizeArrayList = new ArrayList<>(quantityArraySize);
		delimiter = ",";
	}
	public void parse() {
		BufferedReader br = null;
		String line = "";
		
		boolean isFirstLine = true;
		if(filePath != null && !filePath.isEmpty()) {
			try {
				br = new BufferedReader(new FileReader(filePath));
				while ((line = br.readLine()) != null) {
					if(isFirstLine) {
						parseFirstLine(line);
						isFirstLine = false;
					} else {
						parse(line);
					}
				}
	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void setData(String input) {
		this.filePath = input;
	}

	public List<ItemInfo> getData() {		
		return infoList;
	}
	
	private void parseFirstLine(String line) {
		if(line != null) {
			String[] itemArray = line.split(delimiter);		
			if(itemArray != null && ItemInfo.ITEM_NAME_INDEX >=0 && ItemInfo.MRP_INDEX < itemArray.length) {
				for(int j = ItemInfo.SIZE_INDEX_START; (j <= ItemInfo.SIZE_INDEX_END ); j++) {
					sizeArrayList.add(itemArray[j]);
				}
			}
		}		
	}
	
	private void parse(String line) {
		if(line != null) {
			String[] itemArray = line.split(delimiter);		
			if(itemArray != null && ItemInfo.ITEM_NAME_INDEX >=0 && ItemInfo.MRP_INDEX < itemArray.length) {
				ItemInfo info = new ItemInfo();
				info.setItemName(itemArray[ItemInfo.ITEM_NAME_INDEX]);
				info.setShadeName(itemArray[ItemInfo.SHADE_NAME_INDEX]);
				if(CommonUtils.isNumeric(itemArray[ItemInfo.MRP_INDEX])) {
					info.setMrp(Integer.parseInt(itemArray[ItemInfo.MRP_INDEX]));
				}				
				
				if(quantityArraySize > 0) {
					SizeQuantity[] quantityArray = new SizeQuantity[quantityArraySize];						
					for(int sIndex = 0, qIndex = ItemInfo.SIZE_INDEX_START; sIndex < sizeArrayList.size() && qIndex <= ItemInfo.SIZE_INDEX_END; sIndex++,qIndex++) {
						SizeQuantity quantitiy = new SizeQuantity();
						try {
							if(CommonUtils.isNumeric(itemArray[qIndex])) {							
								quantitiy.setQuantity(Long.parseLong(itemArray[qIndex]));
							} else {
								quantitiy.setQuantity(0);
							}
							
							if(CommonUtils.isNumeric(sizeArrayList.get(sIndex))) {
								quantitiy.setSize(Long.parseLong(sizeArrayList.get(sIndex)));
							}
						} catch(Exception e) {
							LogUtil.logError(TAG,e.getMessage());
						}
						quantityArray[sIndex] = quantitiy;
					}
					info.setSizeQuantity(quantityArray);
				}	
				infoList.add(info);
			}
		}		
	}
}

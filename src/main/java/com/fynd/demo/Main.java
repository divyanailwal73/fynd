package com.fynd.demo;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fynd.demo.modal.ItemInfo;
import com.fynd.demo.modal.Items;
import com.fynd.demo.modal.SizeQuantity;
import com.fynd.demo.modal.Store;
import com.fynd.demo.parser.IParser;
import com.fynd.demo.parser.impl.CSVParser;
import com.fynd.demo.service.ItemService;
import com.fynd.demo.service.StoreService;
import com.fynd.demo.util.LogUtil;

public class Main {
	private final String TAG = Main.class.getSimpleName();
	private Properties properties;
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private StoreService storeService;
	
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
		for(ItemInfo itemInfo : list) {
			Items item = new Items();
			item.setItemName(itemInfo.getItemName());
			item.setShadeName(itemInfo.getShade_name());
			item.setMrp(itemInfo.getMrp());
			int id = itemService.save(item);
			if(id != -1) {
				for(SizeQuantity quantity: itemInfo.getSizeQuantity()) {
					if(quantity.getQuantity() > 0 ) {
						Store store = new Store();
						store.setItemId(id);
						store.setQuantity((int)quantity.getQuantity());
						store.setSize((int)quantity.getSize());
						int storeId = storeService.save(store);
						if(storeId != -1) {
							System.out.println("Item Info successfully inserted.");
						}
					}
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext appContext = 
		    	  new ClassPathXmlApplicationContext("WEB_INF/spring/servlet-context.xml");
		Main main = new Main();
		main.init();
	}
}

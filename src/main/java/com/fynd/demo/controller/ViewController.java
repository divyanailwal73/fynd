package com.fynd.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fynd.demo.modal.ItemInfo;
import com.fynd.demo.modal.Items;
import com.fynd.demo.modal.SizeQuantity;
import com.fynd.demo.modal.Store;
import com.fynd.demo.parser.IParser;
import com.fynd.demo.parser.impl.CSVParser;
import com.fynd.demo.service.ItemService;
import com.fynd.demo.service.StoreService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/fyndViews")
public class ViewController {

	private final String failedJsonResponse = "{\"result\":\"Failed to insert\"}";
	private final String successJsonResponse = "{\"result\":\"Successfully Inserted\"}";
	
	@Value("${filename}")
	private String filename;
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private StoreService storeService;
	
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	@ResponseBody
	public String insertFromCsv(HttpServletRequest request,
			HttpServletResponse response) {		
		String result = failedJsonResponse;
		if(filename != null && !filename.isEmpty()) {
			IParser<String, List<ItemInfo>> parser = new CSVParser();		
			parser.setData(filename);
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
						Store store = new Store();
						store.setItemId(id);
						store.setQuantity((int)quantity.getQuantity());
						store.setSize((int)quantity.getSize());
						int storeId = storeService.save(store);
						if(storeId != -1) {
							result = successJsonResponse;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/getData", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	@ResponseBody
	public String getStoreData(HttpServletRequest request,
			HttpServletResponse response) {		
		String result = failedJsonResponse;
		List<Items> itemList = itemService.getAllData();		
		List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>();
		for(Items item : itemList) {
			List<Store> storeList = storeService.getByItemId(item.getId());
			ItemInfo itemInfo = new ItemInfo();
			itemInfo.setItemName(item.getItemName());
			itemInfo.setShadeName(item.getShadeName());
			itemInfo.setMrp(item.getMrp());
			SizeQuantity[] quantityList = new SizeQuantity[storeList.size()];
			int index = 0;
			for(Store store : storeList) {
				SizeQuantity quantity = new SizeQuantity();
				quantity.setQuantity(store.getQuantity());
				quantity.setSize(store.getSize());
				quantityList[index++] = quantity;
			}
			itemInfo.setSizeQuantity(quantityList);
			itemInfoList.add(itemInfo);
		}
		Gson gson = new Gson();
		return gson.toJson(itemInfoList);
	}
}

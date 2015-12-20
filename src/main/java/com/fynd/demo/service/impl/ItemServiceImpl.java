package com.fynd.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fynd.demo.dao.ItemDao;
import com.fynd.demo.modal.Items;
import com.fynd.demo.service.ItemService;

public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;
	
	@Override
	public int save(Items item) {	
		return itemDao.save(item);
	}

	@Override
	public List<Items> getAllData() {
		return itemDao.getAllData();
	}

}

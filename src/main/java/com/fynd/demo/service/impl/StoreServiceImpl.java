package com.fynd.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fynd.demo.dao.StoreDao;
import com.fynd.demo.modal.Store;
import com.fynd.demo.service.StoreService;

public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreDao storeDao;
	
	@Override
	public int save(Store item) {		
		return storeDao.save(item);
	}

	@Override
	public List<Store> getAllData() {
		return storeDao.getAllData();
	}

	@Override
	public List<Store> getByItemId(int id) {
		return storeDao.getByItemId(id);
	}

}

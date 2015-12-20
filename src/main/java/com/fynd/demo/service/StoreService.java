package com.fynd.demo.service;

import java.util.List;

import com.fynd.demo.modal.Store;

public interface StoreService {
	int save(Store item);
	List<Store> getAllData();
	List<Store> getByItemId(int id);
}

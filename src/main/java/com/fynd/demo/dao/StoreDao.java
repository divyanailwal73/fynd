package com.fynd.demo.dao;

import java.util.List;

import com.fynd.demo.modal.Store;

public interface StoreDao {
	int save(Store item);
	List<Store> getAllData();
	List<Store> getByItemId(int id);
}

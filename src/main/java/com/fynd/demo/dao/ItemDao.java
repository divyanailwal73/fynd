package com.fynd.demo.dao;

import java.util.List;

import com.fynd.demo.modal.Items;

public interface ItemDao {
	int save(Items item);
	List<Items> getAllData();
}

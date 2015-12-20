package com.fynd.demo.service;

import java.util.List;

import com.fynd.demo.modal.Items;

public interface ItemService {
	int save(Items item);
	List<Items> getAllData();
}

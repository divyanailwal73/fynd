package com.fynd.demo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.fynd.demo.dao.ItemDao;
import com.fynd.demo.modal.Items;
import com.fynd.demo.util.LogUtil;


public class ItemDaoImpl implements ItemDao {

	private final String TAG = ItemDaoImpl.class.getSimpleName();
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public int save(Items item) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			int id = (int) session.save(item);
			tx.commit();
			session.flush();
			session.close();
			return id;
		} catch(Exception e) {
			LogUtil.logError(TAG, e.getCause().toString());
		}		
		return -1;
	}

	@Override
	public List<Items> getAllData() {
		List<Items> itemList= new ArrayList<>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Items.class);		
		itemList = (List<Items>) criteria.list();
		
		session.flush();
		session.close();
		return itemList;
	}

}

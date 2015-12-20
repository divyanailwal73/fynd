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

import com.fynd.demo.dao.StoreDao;
import com.fynd.demo.modal.Items;
import com.fynd.demo.modal.Store;
import com.fynd.demo.util.LogUtil;

@Repository("storeDaoImpl")
public class StoreDaoImpl implements StoreDao {

	private final String TAG = StoreDaoImpl.class.getSimpleName();
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public int save(Store item) {
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
	public List<Store> getAllData() {
		List<Store> storeList= new ArrayList<>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Store.class);		
		storeList = (List<Store>) criteria.list();
		
		session.flush();
		session.close();
		return storeList;
	}

	@Override
	public List<Store> getByItemId(int id) {
		List<Store> storeList= new ArrayList<>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Store.class);	
		criteria.add(Restrictions.eq("itemId",id));
		storeList = (List<Store>) criteria.list();
		
		session.flush();
		session.close();
		return storeList;
	}

}

package com.scrumiverse.persistence.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.scrumFeatures.HistoryEntry;
import com.scrumiverse.persistence.DAO.HistoryDAO;

/**
 * DAO for saving single history entries
 * @author Kevin Jolitz
 * @version 17.03.2016
 *
 */
public class HistoryDAOImpl implements HistoryDAO {
	
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactorySprint) {
		this.hibernateTemplate = 
               new HibernateTemplate(sessionFactorySprint); }

	@Override
	public void saveHistoryEntry(HistoryEntry entry) {
		hibernateTemplate.save(entry);
	}
}
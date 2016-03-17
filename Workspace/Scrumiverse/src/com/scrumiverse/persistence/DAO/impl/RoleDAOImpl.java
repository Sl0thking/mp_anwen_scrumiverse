package com.scrumiverse.persistence.DAO.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.account.Role;
import com.scrumiverse.persistence.DAO.RoleDAO;

public class RoleDAOImpl implements RoleDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sessionFactoryProject) {
		this.hibernateTemplate = 
					new HibernateTemplate(sessionFactoryProject); }
	
	@Override
	public void saveRole(Role r) {
		hibernateTemplate.save(r);
		
	}

	@Override
	public Role getRole(int id) {
		return (Role) hibernateTemplate.find("from Role where roleID='" + id + "'").get(0);
	}

	@Override
	public SortedSet<Role> getRoles() {
		SortedSet<Role> roleSet = new TreeSet<Role>();
		List<Role> roles = hibernateTemplate.find("from Role");
		for(Role role : roles) {
			roleSet.add(role);
		}
		return roleSet;
	}

	@Override
	public void updateRole(Role role) {
		hibernateTemplate.update(role);
	}
}
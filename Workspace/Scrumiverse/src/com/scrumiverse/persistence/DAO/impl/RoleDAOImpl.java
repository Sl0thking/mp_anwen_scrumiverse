package com.scrumiverse.persistence.DAO.impl;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.RolePersistenceException;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.persistence.DAO.RoleDAO;

/**
 * Role persistence implementation
 * 
 * @author Kevin Jolitz
 * @version 12.04.2016
 *
 */
public class RoleDAOImpl implements RoleDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sessionFactoryProject) {
		this.hibernateTemplate = 
					new HibernateTemplate(sessionFactoryProject); }
	
	@Override
	public void saveRole(Role r) {
		hibernateTemplate.save(r);
	}
	/**
	 * Returns role by id
	 * @param int
	 * @return Role
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Role getRole(int id) throws RolePersistenceException {
		List<Role> possibleRoles = hibernateTemplate.find("from Role where roleID='" + id + "'");
		if(possibleRoles.size() == 1) {
			return possibleRoles.get(0);
		} 
		throw new RolePersistenceException();
	}
	/**
	 * Returns all roles
	 * @return SortedSet<Role>
	 */
	@SuppressWarnings("unchecked")
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
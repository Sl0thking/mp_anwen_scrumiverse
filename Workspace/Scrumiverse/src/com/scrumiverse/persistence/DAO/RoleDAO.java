package com.scrumiverse.persistence.DAO;

import java.util.Set;

import com.scrumiverse.exception.RolePersistenceException;
import com.scrumiverse.model.account.Role;

public interface RoleDAO {
	public void saveRole(Role r);
	public Role getRole(int id) throws RolePersistenceException;
	public Set<Role> getRoles();
	public void updateRole(Role role);
}

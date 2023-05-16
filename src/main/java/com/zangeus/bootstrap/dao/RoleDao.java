package com.zangeus.bootstrap.dao;

import com.zangeus.bootstrap.model.Role;

import java.util.List;
import java.util.NoSuchElementException;

public interface RoleDao {

    List<Role> findAll();

    Role findRoleByAuthority(String authority) throws NoSuchElementException;

}

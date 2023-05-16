package com.zangeus.bootstrap.dao;

import com.zangeus.bootstrap.model.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    User showById(Long id);

    void updateUserById(Long id, User user);

    void removeUserById(Long id);

    List<User> getAllUsers();

    User findByUsername(String username);

}

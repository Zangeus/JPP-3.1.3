package com.zangeus.bootstrap.service;


import com.zangeus.bootstrap.model.Role;
import com.zangeus.bootstrap.model.User;
import org.springframework.ui.Model;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void removeUserById(Long id);

    void updateUserById(Long id, User user, Model model);

    User showById(Long id);

    List<User> getAllUsers();

    User findByUsername(String username);

    List<Role> findAllRoles();
}

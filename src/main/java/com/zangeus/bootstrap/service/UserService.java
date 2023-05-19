package com.zangeus.bootstrap.service;

import com.zangeus.bootstrap.entities.Role;
import com.zangeus.bootstrap.entities.User;
import org.springframework.ui.Model;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    List<User> getAllUsers();
    User getUser(int id);
    void saveUser(User user);
    void deleteUserAndHisTokensById(int id);
}

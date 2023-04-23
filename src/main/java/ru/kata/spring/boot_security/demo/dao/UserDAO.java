package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    void createUser(User user);
    User getUser(int id);
    void deleteUser(int id);
    void updateUser(User user);
}

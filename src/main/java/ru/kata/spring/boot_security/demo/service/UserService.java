package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    List<User> getAllUsers();
    User getUser(int id);
    void createUser(User user);
    void deleteUser(int id);
    void updateUser(User user);
}

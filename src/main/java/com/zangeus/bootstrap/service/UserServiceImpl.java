package com.zangeus.bootstrap.service;

import com.zangeus.bootstrap.dao.RoleDao;
import com.zangeus.bootstrap.dao.UserDao;
import com.zangeus.bootstrap.model.Role;
import com.zangeus.bootstrap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        for (Role role : user.getRoles()) {
            role.setId(roleDao.findRoleByAuthority(role.getAuthority()).getId());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Transactional
    @Override
    public void removeUserById(Long id) {
        userDao.removeUserById(id);
    }

    @Transactional
    @Override
    public void updateUserById(Long id, User user, Model model) {
        for (Role role : user.getRoles()) {
            role.setId(roleDao.findRoleByAuthority(role.getAuthority()).getId());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.updateUserById(id, user);
    }

    @Transactional
    @Override
    public User showById(Long id) {
        return userDao.showById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }

}

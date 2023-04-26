package com.zangeus.bootstrap.service;

import com.zangeus.bootstrap.entities.Role;
import com.zangeus.bootstrap.entities.User;
import com.zangeus.bootstrap.repositories.RoleRepository;
import com.zangeus.bootstrap.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl
        implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository
            , RoleRepository roleRepository
            , PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with current email: '%s' - not found", email));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    //CRUD OPERATIONS
    @Transactional
    public Collection<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void addRoles(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteUserAndHisTokensById(int userId) {
        User userToBeDelete = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        userToBeDelete.getRoles()
               .forEach(role -> {
                   Collection<User> updatedUsers = role.getUsers()
                           .stream()
                           .filter(account -> !account.equals(userToBeDelete))
                           .collect(Collectors.toSet());
                   role.setUsers(updatedUsers);
                   roleRepository.save(role);
               });
        userToBeDelete.setRoles(null);
        userRepository.deleteById(userId);
    }
}
package ru.kata.sping.boot_security_bootstrap.services;

import ru.kata.sping.boot_security_bootstrap.model.Role;
import ru.kata.sping.boot_security_bootstrap.model.User;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User getUserById(Long id);

    void save(User user);

    void update(Long id, User updateUser);

    void deleteUser(Long id);

    List<Role> getAllRoles();

}
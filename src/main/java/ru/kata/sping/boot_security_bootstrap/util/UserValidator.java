package ru.kata.sping.boot_security_bootstrap.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.sping.boot_security_bootstrap.model.User;
import ru.kata.sping.boot_security_bootstrap.security.UserDetailsServiceImpl;


@Component
public class UserValidator implements Validator {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public UserValidator(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            userDetailsServiceImpl.loadUserByUsername(user.getEmail());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("email", "", "Пользовательс таким электронным адресом уже существует");
    }
}

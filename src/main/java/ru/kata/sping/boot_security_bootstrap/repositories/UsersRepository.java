package ru.kata.sping.boot_security_bootstrap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.sping.boot_security_bootstrap.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}

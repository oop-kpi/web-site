package com.oopwebsite.repository;

import com.oopwebsite.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByLogin(String login);
    Optional<User> findById(int id);
    boolean existsByLogin(String login);
}

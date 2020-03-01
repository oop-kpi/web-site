package com.oopwebsite.repository;

import com.oopwebsite.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByLogin(String login);
    Optional<User> findById(String id);
    boolean existsByLogin(String login);
}

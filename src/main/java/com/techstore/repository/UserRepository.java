package com.techstore.repository;

import com.techstore.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT * FROM users WHERE username = :username")
    Optional<User> findByUsername(String username);

    @Query("SELECT * FROM users WHERE email = :email")
    Optional<User> findByEmail(String email);
}
package com.lzy3me.theater.repositories;

import com.lzy3me.theater.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    User findByUsername(String username);

    User findByEmail(String email);
}
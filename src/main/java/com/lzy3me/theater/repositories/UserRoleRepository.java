package com.lzy3me.theater.repositories;

import com.lzy3me.theater.entities.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRoleRepository extends CrudRepository<UserRole, UUID> {
}

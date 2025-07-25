package com.nisum.UserService.dao;

import com.nisum.UserService.entity.UserRole;
import com.nisum.UserService.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}


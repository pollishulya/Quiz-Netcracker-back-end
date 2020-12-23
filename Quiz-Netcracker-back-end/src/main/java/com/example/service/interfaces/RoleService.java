package com.example.service.interfaces;

import com.example.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {
    List<Role> findAll();
    Role findById(UUID id);
    Role save(Role role);
    Role update(UUID id, Role role);
    void delete(UUID id);
}

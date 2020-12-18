package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Role;
import com.example.repository.RoleRepository;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(UUID id, Role roleRequest) {
        return roleRepository.findById(id).map(role -> {
            role.setTitle(roleRequest.getTitle());
            role.setUserSet(roleRequest.getUserSet());
            return roleRepository.save(role);
        }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
    }

    @Override
    public void delete(UUID id) {
        roleRepository.deleteById(id);
    }
}

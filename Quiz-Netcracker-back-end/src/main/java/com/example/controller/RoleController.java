package com.example.controller;

import com.example.model.Role;
import com.example.service.RoleService;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public CollectionWrapper<Role> getRoles() {
        return new CollectionWrapper<>(roleService.findAll());
    }

    @GetMapping("/roles/{roleId}")
    public Optional<Role> getRole(@PathVariable UUID roleId) {
        return roleService.findById(roleId);
    }

    @PostMapping("/roles")
    public Role createRole(@Valid @RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/roles/{roleId}")
    public Role updateRole(@PathVariable UUID roleId,
                           @Valid @RequestBody Role role) {
        return roleService.update(roleId, role);
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID roleId) {
        roleService.delete(roleId);
        return ResponseEntity.ok().build();
    }
}

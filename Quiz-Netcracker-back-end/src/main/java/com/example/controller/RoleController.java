package com.example.controller;

import com.example.dto.RoleDto;
import com.example.model.Role;
import com.example.service.interfaces.RoleService;
import com.example.service.mapper.RoleMapper;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class RoleController {
    private final RoleService roleService;
    private  final RoleMapper roleMapper;
    @Autowired
    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping("/roles")
    public List<RoleDto>  getRoles() {
        return roleService.findAll().stream().map(roleMapper :: toShortRoleDto ).collect(Collectors.toList());
    }

    @GetMapping("/roles/{roleId}")
    public RoleDto getRole(@PathVariable UUID roleId) {
        return roleMapper.toShortRoleDto(roleService.findById(roleId));
    }

    @PostMapping("/roles")
    public RoleDto createRole(@Valid @RequestBody RoleDto roleDto) {
        Role role=roleMapper.fromRoleDto(roleDto);
        return roleMapper.toRoleDto(roleService.save(role));
    }

    @PutMapping("/roles/{roleId}")
    public RoleDto updateRole(@PathVariable UUID roleId,
                           @Valid @RequestBody RoleDto roleDto) {
        Role role=roleMapper.fromRoleDto(roleDto);
        return roleMapper.toRoleDto(roleService.update(roleId,role));
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID roleId) {
        roleService.delete(roleId);
        return ResponseEntity.ok().build();
    }
}

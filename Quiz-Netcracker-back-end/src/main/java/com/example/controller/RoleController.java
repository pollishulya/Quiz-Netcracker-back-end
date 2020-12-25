package com.example.controller;

import com.example.dto.RoleDto;
import com.example.model.Role;
import com.example.service.interfaces.RoleService;
import com.example.service.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
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
    @GetMapping("/all")
    public CollectionWrapper<RoleDto> getRoles() {
        return new CollectionWrapper<>(roleService.findAll()
                                                  .stream()
                                                  .map(RoleConverter::fromRoleToRoleDto)
                                                  .collect(Collectors.toList()));
    }
    @GetMapping("/{roleId}")
    public RoleDto getRole(@PathVariable UUID roleId) {
        return roleMapper.toShortRoleDto(roleService.findById(roleId));
    }

    @PostMapping("/save")
    public RoleDto createRole(@Valid @RequestBody RoleDto roleDto) {
        Role role=roleMapper.fromRoleDto(roleDto);
        return roleMapper.toRoleDto(roleService.save(role));
    }

    @PutMapping("/{roleId}")
    public RoleDto updateRole(@PathVariable UUID roleId,
                           @Valid @RequestBody RoleDto roleDto) {
        Role role=roleMapper.fromRoleDto(roleDto);
        return roleMapper.toRoleDto(roleService.update(roleId,role));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID roleId) {
        roleService.delete(roleId);
        return ResponseEntity.ok().build();
    }
}

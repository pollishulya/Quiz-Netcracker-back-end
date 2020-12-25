package com.example.controller;

import com.example.model.Role;
import com.example.service.RoleService;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public CollectionWrapper<RoleDto> getRoles() {
        return new CollectionWrapper<>(roleService.findAll()
                                                  .stream()
                                                  .map(RoleConverter::fromRoleToRoleDto)
                                                  .collect(Collectors.toList()));
    }

    @GetMapping("/{roleId}")
    public RoleDto getRole(@PathVariable UUID roleId) {
        return RoleConverter.fromRoleToRoleDto(roleService.findById(roleId));
    }

    @PostMapping("/save")
    public RoleDto createRole(@Valid @RequestBody RoleDto roleDto) {
        Role role = RoleConverter.fromRoleDtoToRole(roleDto);
        return RoleConverter.fromRoleToRoleDto(roleService.save(role));
    }

    @PutMapping("/{roleId}")
    public RoleDto updateRole(@PathVariable UUID roleId,
                           @Valid @RequestBody RoleDto roleDto) {
        Role role = RoleConverter.fromRoleDtoToRole(roleDto);
        return RoleConverter.fromRoleToRoleDto(roleService.update(roleId, role));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID roleId) {
        roleService.delete(roleId);
        return ResponseEntity.ok().build();
    }
}

package com.example.controller;

import com.example.dto.RoleDto;
import com.example.model.Role;
import com.example.service.interfaces.RoleService;
import com.example.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//import com.example.dto.conerters.RoleConverter;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RoleController {
    private final RoleService roleService;
    private  final Mapper mapper;
    @Autowired
    public RoleController(RoleService roleService, Mapper mapper) {
        this.roleService = roleService;
        this.mapper = mapper;
    }
    @GetMapping("/roles")
    public List<RoleDto>  getRoles() {
        return roleService.findAll().stream().map(mapper:: toShortRoleDto ).collect(Collectors.toList());
    //@GetMapping("/all")
    //public CollectionWrapper<RoleDto> getRoles() {
        //return new CollectionWrapper<>(roleService.findAll()
       //                                           .stream()
         //                                         .map(RoleConverter::fromRoleToRoleDto)
     //                                             .collect(Collectors.toList()));
    }
    @GetMapping("/{roleId}")
    public RoleDto getRole(@PathVariable UUID roleId) {
        return mapper.toShortRoleDto(roleService.findById(roleId));
    }

    @PostMapping("/save")
    public RoleDto createRole(@Valid @RequestBody RoleDto roleDto) {
        Role role= mapper.fromRoleDto(roleDto);
        return mapper.toRoleDto(roleService.save(role));
    }

    @PutMapping("/{roleId}")
    public RoleDto updateRole(@PathVariable UUID roleId,
                           @Valid @RequestBody RoleDto roleDto) {
        Role role= mapper.fromRoleDto(roleDto);
        return mapper.toRoleDto(roleService.update(roleId,role));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID roleId) {
        roleService.delete(roleId);
        return (ResponseEntity<?>) ResponseEntity.ok().build();
    }
}

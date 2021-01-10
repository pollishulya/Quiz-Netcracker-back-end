package com.example.service.mapper;

import com.example.dto.RoleDto;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper implements Mapper<Role, RoleDto> {
    private final UserService userService;

    @Autowired
    public RoleMapper(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RoleDto toDto(Role entity) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(entity.getId());
        roleDto.setTitle(entity.getTitle());
        roleDto.setUsers(entity.getUserSet()
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet()));
        return roleDto;
    }

    @Override
    public Role toEntity(RoleDto dto) {
        Set<User> users = dto.getUsers()
                .stream()
                .map(userService::getUserById)
                .collect(Collectors.toSet());
        Role role = new Role();
        role.setId(dto.getId());
        role.setTitle(dto.getTitle());
        role.setUserSet(users);
        return role;
    }

    @Override
    public RoleDto toShortDto(Role entity) {
        RoleDto roleDto = new RoleDto();
        roleDto.setTitle(entity.getTitle());
        return roleDto;
    }
}

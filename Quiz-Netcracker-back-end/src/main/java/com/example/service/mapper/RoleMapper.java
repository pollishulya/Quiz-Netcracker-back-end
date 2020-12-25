package com.example.service.mapper;

import com.example.dto.RoleDto;
import com.example.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleDto toRoleDto(final Role role){
        return RoleDto.builder()
                .id(role.getId())
                .title(role.getTitle())
                .users(role.getUserSet())
                .build();
    }

    public Role fromRoleDto(final RoleDto roleDto){
        return Role.builder()
                .id(roleDto.getId())
                .title(roleDto.getTitle())
                .userSet(roleDto.getUsers())
                .build();
    }

    public  RoleDto toShortRoleDto(final Role role ){
        return RoleDto.builder()
                .title(role.getTitle())
                .build();
    }

}

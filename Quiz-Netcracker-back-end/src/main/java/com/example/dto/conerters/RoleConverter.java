package com.example.dto.conerters;

import com.example.dto.RoleDto;
import com.example.model.Role;

public class RoleConverter {
    public static RoleDto fromRoleToRoleDto(Role role) {
        if (role == null) {
            return null;
        }
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setTitle(role.getTitle());
//        roleDto.setUserSet(role.getUserSet());
        return roleDto;
    }

    public static Role fromRoleDtoToRole(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setTitle(roleDto.getTitle());
//        role.setUserSet(roleDto.getUserSet());
        return role;
    }
}

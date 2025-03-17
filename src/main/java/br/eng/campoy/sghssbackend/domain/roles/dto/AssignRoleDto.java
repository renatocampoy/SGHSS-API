package br.eng.campoy.sghssbackend.domain.roles.dto;

import lombok.Data;

@Data
public class AssignRoleDto {
    private Long userId;
    private Long roleId;
}
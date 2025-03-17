package br.eng.campoy.sghssbackend.domain.roles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleResponseDto {
    private Long id;
    private String name;
}
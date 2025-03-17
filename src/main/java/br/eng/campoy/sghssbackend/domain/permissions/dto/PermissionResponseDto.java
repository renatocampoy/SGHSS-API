package br.eng.campoy.sghssbackend.domain.permissions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionResponseDto {
    private Long id;
    private String name;
}
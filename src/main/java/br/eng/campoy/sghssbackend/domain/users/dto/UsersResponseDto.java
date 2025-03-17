package br.eng.campoy.sghssbackend.domain.users.dto;

import br.eng.campoy.sghssbackend.domain.users.ValueObject.Role;
import br.eng.campoy.sghssbackend.domain.users.ValueObject.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UsersResponseDto {
    private Long id;
    private String email;
    private Role role;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
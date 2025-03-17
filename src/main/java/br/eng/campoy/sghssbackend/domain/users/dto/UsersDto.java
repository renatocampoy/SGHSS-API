package br.eng.campoy.sghssbackend.domain.users.dto;

import br.eng.campoy.sghssbackend.domain.users.ValueObject.Role;
import br.eng.campoy.sghssbackend.domain.users.ValueObject.Status;
import lombok.Data;

@Data
public class UsersDto {
    private String email;
    private String password;
    private Role role;
    private Status status;
}
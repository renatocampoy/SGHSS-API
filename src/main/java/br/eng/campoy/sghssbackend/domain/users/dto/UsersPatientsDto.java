package br.eng.campoy.sghssbackend.domain.users.dto;

import br.eng.campoy.sghssbackend.domain.patients.ValueObject.Status;
import br.eng.campoy.sghssbackend.types.Age;
import br.eng.campoy.sghssbackend.types.Cpf;
import br.eng.campoy.sghssbackend.types.Email;

public record UsersPatientsDto(
        Long user_id,
        Email email,
        String name,
        Age birthDate,
        Cpf cpf,
        String address,
        String number,
        String city,
        String state,
        String country,
        String phone,
        String phoneContact,
        String mobile,
        Status status
) {
}

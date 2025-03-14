package br.eng.campoy.sghssbackend.domain.users.dto;

import br.eng.campoy.sghssbackend.domain.patients.ValueObject.Status;

public record UsersPatientsDto(
        Long user_id,
        String email,
        String name,
        String birthDate,
        String cpf,
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

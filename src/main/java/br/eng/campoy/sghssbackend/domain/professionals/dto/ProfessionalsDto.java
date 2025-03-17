package br.eng.campoy.sghssbackend.domain.professionals.dto;

import br.eng.campoy.sghssbackend.domain.professionals.ValueObject.ProfessionalStatus;
import lombok.Data;

@Data
public class ProfessionalsDto {
    private Long userId;
    private Long serviceUnitId;
    private String name;
    private String cpf;
    private String classRegistration;
    private String address;
    private String number;
    private String city;
    private String state;
    private String country;
    private String email;
    private String phone;
    private String phoneContact;
    private String mobile;
    private ProfessionalStatus status;
}
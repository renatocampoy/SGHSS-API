package br.eng.campoy.sghssbackend.domain.patients.dto;

import lombok.Data;

@Data
public class PatientsDto {
    private Long userId;
    private String name;
    private String birthDate;
    private String cpf;
    private String address;
    private String number;
    private String city;
    private String state;
    private String country;
    private String email;
    private String phone;
    private String phoneContact;
    private String mobile;
    private String status;

}
package br.eng.campoy.sghssbackend.domain.serviceunit.dto;

import br.eng.campoy.sghssbackend.domain.serviceunit.ValueObject.ServiceUnitStatus;
import lombok.Data;

@Data
public class ServiceUnitDto {
    private String name;
    private String address;
    private String number;
    private String city;
    private String state;
    private String country;
    private String email;
    private String phone;
    private String mobile;
    private Integer capacity;
    private ServiceUnitStatus status;
}
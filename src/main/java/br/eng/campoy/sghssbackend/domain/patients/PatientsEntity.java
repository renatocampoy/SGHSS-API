package br.eng.campoy.sghssbackend.domain.patients;

import br.eng.campoy.sghssbackend.domain.patients.ValueObject.Status;
import br.eng.campoy.sghssbackend.domain.users.UsersEntity;
import br.eng.campoy.sghssbackend.types.Age;
import br.eng.campoy.sghssbackend.types.Cpf;
import br.eng.campoy.sghssbackend.types.Email;
import br.eng.campoy.sghssbackend.types.converter.EmailAttributeConverter;
import br.eng.campoy.sghssbackend.types.converter.AgeAttributeConverter;
import  br.eng.campoy.sghssbackend.types.converter.CpfAttributeConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Patients")
@Getter
@Setter
public class PatientsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private UsersEntity user;

    private String name;

    @Convert(converter = AgeAttributeConverter.class)
    private Age birthDate;

    @Convert(converter = CpfAttributeConverter.class)
    private Cpf cpf;

    private String address;
    private String number;
    private String city;
    private String state;
    private String country;

    @Convert(converter = EmailAttributeConverter.class)
    private Email email;

    private String phone;
    private String phoneContact;
    private String mobile;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String createdAt;
    private String updatedAt;

}
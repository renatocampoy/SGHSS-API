package br.eng.campoy.sghssbackend.domain.patients;

import br.eng.campoy.sghssbackend.domain.patients.ValueObject.Status;
import br.eng.campoy.sghssbackend.domain.users.UsersEntity;
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

    @Enumerated(EnumType.STRING)
    private Status status;

    private String createdAt;
    private String updatedAt;

}
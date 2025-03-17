package br.eng.campoy.sghssbackend.domain.professionals;

import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitEntity;
import br.eng.campoy.sghssbackend.domain.users.UsersEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ValueObject.ProfessionalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Professionals")
public class ProfessionalsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "service_unit_id", nullable = false)
    private ServiceUnitEntity serviceUnit;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String classRegistration; // Ex: CRM, CRO, COREN

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
    @Column(nullable = false)
    private ProfessionalStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
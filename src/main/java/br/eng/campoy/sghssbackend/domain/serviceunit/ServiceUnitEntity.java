package br.eng.campoy.sghssbackend.domain.serviceunit;

import br.eng.campoy.sghssbackend.domain.serviceunit.ValueObject.ServiceUnitStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ServiceUnit")
public class ServiceUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    private String mobile;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceUnitStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
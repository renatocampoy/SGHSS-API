package br.eng.campoy.sghssbackend.domain.hospitalizationreport;

import br.eng.campoy.sghssbackend.domain.hospitalization.HospitalizationEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HospitalizationReport")
public class HospitalizationReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospitalization_id", nullable = false)
    private HospitalizationEntity hospitalization;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private ProfessionalsEntity professional;

    @Column(nullable = false)
    private String reportText;

    @Column(nullable = false)
    private LocalDateTime reportDate;
}
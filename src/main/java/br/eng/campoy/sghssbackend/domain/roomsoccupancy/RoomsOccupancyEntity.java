package br.eng.campoy.sghssbackend.domain.roomsoccupancy;

import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import br.eng.campoy.sghssbackend.domain.rooms.RoomsEntity;
import br.eng.campoy.sghssbackend.domain.roomsoccupancy.ValueObject.RoomsOccupancyStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RoomsOccupancy")
public class RoomsOccupancyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientsEntity patient;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomsEntity room;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomsOccupancyStatus status;

    @Column(nullable = false)
    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
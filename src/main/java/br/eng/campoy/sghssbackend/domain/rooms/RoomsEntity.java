package br.eng.campoy.sghssbackend.domain.rooms;

import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitEntity;
import br.eng.campoy.sghssbackend.domain.rooms.ValueObject.RoomStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Rooms")
public class RoomsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_unit_id", nullable = false)
    private ServiceUnitEntity serviceUnit;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status;
}
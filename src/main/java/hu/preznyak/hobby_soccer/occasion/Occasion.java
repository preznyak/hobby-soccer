package hu.preznyak.hobby_soccer.occasion;

import hu.preznyak.hobby_soccer.participant.Participant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "Occasions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Occasion {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false, name = "\"end\"")
    private LocalDateTime end;

    @ManyToMany
    @JoinTable(
            name = "Participations",
            joinColumns = @JoinColumn(name = "occasionId"),
            inverseJoinColumns = @JoinColumn(name = "participantId")
    )
    private Set<Participant> participants;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}

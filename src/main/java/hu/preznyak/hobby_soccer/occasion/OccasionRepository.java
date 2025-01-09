package hu.preznyak.hobby_soccer.occasion;

import hu.preznyak.hobby_soccer.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OccasionRepository extends JpaRepository<Occasion, Long> {

    Occasion findFirstByParticipants(Participant participant);

    List<Occasion> findAllByParticipants(Participant participant);
}

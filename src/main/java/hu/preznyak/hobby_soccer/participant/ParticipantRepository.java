package hu.preznyak.hobby_soccer.participant;

import hu.preznyak.hobby_soccer.occasion.Occasion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Participant findFirstByOccasions(Occasion occasion);

    List<Participant> findAllByOccasions(Occasion occasion);

}

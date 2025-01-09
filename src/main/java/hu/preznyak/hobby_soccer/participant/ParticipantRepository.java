package hu.preznyak.hobby_soccer.participant;

import hu.preznyak.hobby_soccer.occasion.Occasion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Participant findFirstByOccasions(Occasion occasion);

    List<Participant> findAllByOccasions(Occasion occasion);

}

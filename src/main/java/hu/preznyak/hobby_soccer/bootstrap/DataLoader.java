package hu.preznyak.hobby_soccer.bootstrap;

import hu.preznyak.hobby_soccer.occasion.Occasion;
import hu.preznyak.hobby_soccer.occasion.OccasionRepository;
import hu.preznyak.hobby_soccer.participant.Participant;
import hu.preznyak.hobby_soccer.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ParticipantRepository participantRepository;
    private final OccasionRepository occasionRepository;

    @Override
    public void run(String... args) throws Exception {
        fillDatabase();
    }

    private void fillDatabase() {

        var occasion = occasionRepository.save(Occasion.builder()
                .title("All Stars")
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(2))
                .build());
        participantRepository.save(Participant.builder()
                .name("David Beckham")
                .age(42)
                .build());
        participantRepository.save(Participant.builder()
                .name("Roberto Carlos")
                .age(45)
                .build());
        participantRepository.save(Participant.builder()
                .name("Thierry Henry")
                .age(45)
                .build());
        participantRepository.save(Participant.builder()
                .name("Ronaldinho")
                .age(45)
                .build());
        var participants = participantRepository.findAll();

        occasion.setParticipants(new HashSet<>(participants));
        occasionRepository.save(occasion);
    }
}

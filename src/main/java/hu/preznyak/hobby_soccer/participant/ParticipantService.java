package hu.preznyak.hobby_soccer.participant;

import hu.preznyak.hobby_soccer.occasion.Occasion;
import hu.preznyak.hobby_soccer.occasion.OccasionRepository;
import hu.preznyak.hobby_soccer.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final OccasionRepository occasionRepository;

    public ParticipantService(final ParticipantRepository participantRepository,
            final OccasionRepository occasionRepository) {
        this.participantRepository = participantRepository;
        this.occasionRepository = occasionRepository;
    }

    public List<ParticipantDTO> findAll() {
        final List<Participant> participants = participantRepository.findAll(Sort.by("id"));
        return participants.stream()
                .map(participant -> mapToDTO(participant, new ParticipantDTO()))
                .toList();
    }

    public ParticipantDTO get(final Long id) {
        return participantRepository.findById(id)
                .map(participant -> mapToDTO(participant, new ParticipantDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ParticipantDTO participantDTO) {
        final Participant participant = new Participant();
        mapToEntity(participantDTO, participant);
        return participantRepository.save(participant).getId();
    }

    public void update(final Long id, final ParticipantDTO participantDTO) {
        final Participant participant = participantRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(participantDTO, participant);
        participantRepository.save(participant);
    }

    public void delete(final Long id) {
        participantRepository.deleteById(id);
    }

    private ParticipantDTO mapToDTO(final Participant participant,
            final ParticipantDTO participantDTO) {
        participantDTO.setId(participant.getId());
        participantDTO.setName(participant.getName());
        participantDTO.setAge(participant.getAge());
        participantDTO.setOccasions(participant.getOccasions().stream()
                .map(occasion -> occasion.getId())
                .toList());
        return participantDTO;
    }

    private Participant mapToEntity(final ParticipantDTO participantDTO,
            final Participant participant) {
        participant.setName(participantDTO.getName());
        participant.setAge(participantDTO.getAge());
        final List<Occasion> occasions = occasionRepository.findAllById(
                participantDTO.getOccasions() == null ? Collections.emptyList() : participantDTO.getOccasions());
        if (occasions.size() != (participantDTO.getOccasions() == null ? 0 : participantDTO.getOccasions().size())) {
            throw new NotFoundException("one of occasions not found");
        }
        participant.setOccasions(new HashSet<>(occasions));
        return participant;
    }

}
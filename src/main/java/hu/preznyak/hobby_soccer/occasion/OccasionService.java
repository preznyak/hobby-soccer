package hu.preznyak.hobby_soccer.occasion;

import hu.preznyak.hobby_soccer.participant.*;
import hu.preznyak.hobby_soccer.util.NotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class OccasionService {

    private final OccasionRepository occasionRepository;
    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    public OccasionService(final OccasionRepository occasionRepository,
                           final ParticipantRepository participantRepository, ParticipantMapper participantMapper) {
        this.occasionRepository = occasionRepository;
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
    }

    public List<OccasionDTO> findAll() {
        final List<Occasion> occasions = occasionRepository.findAll(Sort.by("id"));
        return occasions.stream()
                .map(occasion -> mapToDTO(occasion, new OccasionDTO()))
                .toList();
    }

    public OccasionDTO get(final Long id) {
        return occasionRepository.findById(id)
                .map(occasion -> mapToDTO(occasion, new OccasionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OccasionDTO occasionDTO) {
        final Occasion occasion = new Occasion();
        mapToEntity(occasionDTO, occasion);
        return occasionRepository.save(occasion).getId();
    }

    public void update(final Long id, final OccasionDTO occasionDTO) {
        final Occasion occasion = occasionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(occasionDTO, occasion);
        occasionRepository.save(occasion);
    }

    public void delete(final Long id) {
        final Occasion occasion = occasionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        participantRepository.findAllByOccasions(occasion)
                .forEach(participant -> participant.getOccasions().remove(occasion));
        occasionRepository.delete(occasion);
    }

    private OccasionDTO mapToDTO(final Occasion occasion, final OccasionDTO occasionDTO) {
        occasionDTO.setId(occasion.getId());
        occasionDTO.setTitle(occasion.getTitle());
        occasionDTO.setStart(occasion.getStart());
        occasionDTO.setEnd(occasion.getEnd());
        occasionDTO.setParticipants(occasion.getParticipants().stream()
                .map(Participant::getName)
                .collect(Collectors.toList()));
        return occasionDTO;
    }
    //TODO introduce mapstruct
    private Occasion mapToEntity(final OccasionDTO occasionDTO, final Occasion occasion) {
        occasion.setTitle(occasionDTO.getTitle());
        occasion.setStart(occasionDTO.getStart());
        occasion.setEnd(occasionDTO.getEnd());
        // TODO set participants in a mapstruct decorator
        return occasion;
    }

    public void addParticipant(Long id, @Valid ParticipantDTO participantDTO) {
        final Occasion occasion = occasionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        var participant = participantMapper.toEntity(participantDTO);
        occasion.addParticipant(participant);
        occasionRepository.save(occasion);

    }
}

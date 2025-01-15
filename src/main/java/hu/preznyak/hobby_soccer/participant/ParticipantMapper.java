package hu.preznyak.hobby_soccer.participant;

import org.mapstruct.Mapper;

@Mapper
public interface ParticipantMapper {
    Participant toEntity(ParticipantDTO dto);

    ParticipantDTO toDTO(Participant entity);
}

package hu.preznyak.hobby_soccer.participant;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/participants", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParticipantResource {

    private final ParticipantService participantService;

    public ParticipantResource(final ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants() {
        return ResponseEntity.ok(participantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipant(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(participantService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createParticipant(
            @RequestBody @Valid final ParticipantDTO participantDTO) {
        final Long createdId = participantService.create(participantDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateParticipant(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ParticipantDTO participantDTO) {
        participantService.update(id, participantDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteParticipant(@PathVariable(name = "id") final Long id) {
        participantService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

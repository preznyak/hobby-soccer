package hu.preznyak.hobby_soccer.occasion;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/occasions", produces = MediaType.APPLICATION_JSON_VALUE)
public class OccasionResource {

    private final OccasionService occasionService;

    public OccasionResource(final OccasionService occasionService) {
        this.occasionService = occasionService;
    }

    @GetMapping
    public ResponseEntity<List<OccasionDTO>> getAllOccasions() {
        return ResponseEntity.ok(occasionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccasionDTO> getOccasion(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(occasionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOccasion(@RequestBody @Valid final OccasionDTO occasionDTO) {
        final Long createdId = occasionService.create(occasionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOccasion(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final OccasionDTO occasionDTO) {
        occasionService.update(id, occasionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOccasion(@PathVariable(name = "id") final Long id) {
        occasionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

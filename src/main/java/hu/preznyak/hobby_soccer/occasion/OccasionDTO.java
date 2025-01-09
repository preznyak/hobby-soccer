package hu.preznyak.hobby_soccer.occasion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class OccasionDTO {

    private Long id;

    @Size(max = 255)
    private String title;

    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

    private List<String> participants;

}

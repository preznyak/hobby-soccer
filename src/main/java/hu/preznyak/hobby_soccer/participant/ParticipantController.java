package hu.preznyak.hobby_soccer.participant;

import hu.preznyak.hobby_soccer.occasion.Occasion;
import hu.preznyak.hobby_soccer.occasion.OccasionRepository;
import hu.preznyak.hobby_soccer.util.CustomCollectors;
import hu.preznyak.hobby_soccer.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;
    private final OccasionRepository occasionRepository;

    public ParticipantController(final ParticipantService participantService,
            final OccasionRepository occasionRepository) {
        this.participantService = participantService;
        this.occasionRepository = occasionRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("occasionsValues", occasionRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Occasion::getId, Occasion::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("participants", participantService.findAll());
        return "participant/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("participant") final ParticipantDTO participantDTO) {
        return "participant/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("participant") @Valid final ParticipantDTO participantDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "participant/add";
        }
        participantService.create(participantDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("participant.create.success"));
        return "redirect:/participants";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("participant", participantService.get(id));
        return "participant/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("participant") @Valid final ParticipantDTO participantDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "participant/edit";
        }
        participantService.update(id, participantDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("participant.update.success"));
        return "redirect:/participants";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        participantService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("participant.delete.success"));
        return "redirect:/participants";
    }

}

package hu.preznyak.hobby_soccer.occasion;

import hu.preznyak.hobby_soccer.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/occasions")
public class OccasionController {

    private final OccasionService occasionService;

    public OccasionController(final OccasionService occasionService) {
        this.occasionService = occasionService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("occasions", occasionService.findAll());
        return "occasion/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("occasion") final OccasionDTO occasionDTO) {
        return "occasion/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("occasion") @Valid final OccasionDTO occasionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "occasion/add";
        }
        occasionService.create(occasionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("occasion.create.success"));
        return "redirect:/occasions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("occasion", occasionService.get(id));
        return "occasion/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("occasion") @Valid final OccasionDTO occasionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "occasion/edit";
        }
        occasionService.update(id, occasionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("occasion.update.success"));
        return "redirect:/occasions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        occasionService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("occasion.delete.success"));
        return "redirect:/occasions";
    }

}

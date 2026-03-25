package fr.eni.enchere.retrait.controller;

import fr.eni.enchere.retrait.bll.RetraitService;
import fr.eni.enchere.retrait.bo.Retrait;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("retraits")
public class RetraitController {

    private final RetraitService retraitService;

    public RetraitController(RetraitService retraitService) {
        this.retraitService = retraitService;
    }

    @PostMapping("/supprimer/{id}")
    public String deleteRetrait(@PathVariable Long id){
        retraitService.deleteRetrait(id);
        return "redirect:/profile#retraits";
    }

    @PostMapping("/modifier/{id}")
    public String updateRetrait(@PathVariable Long id,
                                @Valid Retrait retrait,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            Map<String, String> erreurs = new HashMap<>();
            bindingResult.getFieldErrors()
                    .forEach(e -> erreurs.put(e.getField(), e.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            return "redirect:/profile#retraits";
        }

        retrait.setId(id);
        retraitService.updateRetrait(retrait);

        return "redirect:/profile#retraits";
    }
}

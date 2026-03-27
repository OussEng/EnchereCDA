package fr.eni.enchere.retrait.controller;

import fr.eni.enchere.retrait.bll.RetraitService;
import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.security.AuthenticatedUser;
import fr.eni.enchere.user.bll.UserService;
import fr.eni.enchere.user.bo.User;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final UserService userService;
    private final AuthenticatedUser authenticatedUser;

    public RetraitController(RetraitService retraitService, UserService userService, AuthenticatedUser authenticatedUser) {
        this.retraitService = retraitService;
        this.userService = userService;
        this.authenticatedUser = authenticatedUser;
    }

    @PostMapping("/supprimer/{id}")
    public String deleteRetrait(@PathVariable Long id, RedirectAttributes redirectAttributes){
        retraitService.deleteRetrait(id);
        redirectAttributes.addFlashAttribute("success", "Adresse supprimé avec succès !");
        return "redirect:/profile#retraits";
    }

    @PostMapping("/modifier/{id}")
    public String updateRetrait(@PathVariable Long id,
                                @Valid @ModelAttribute Retrait retrait,
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
        redirectAttributes.addFlashAttribute("success", "Adresse modifié avec succès !");

        return "redirect:/profile#retraits";
    }

    @PostMapping("/ajouter")
    public String createRetrait(@Valid @ModelAttribute Retrait retrait,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            Map<String, String> erreurs = new HashMap<>();
            bindingResult.getFieldErrors()
                    .forEach(e -> erreurs.put(e.getField(), e.getDefaultMessage()));
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            redirectAttributes.addFlashAttribute("ouvrirModal", true);
            return "redirect:/profile#retraits";
        }

        User user = userService.getByPseudo(userDetails.getUsername());
        retraitService.createRetrait(retrait, user);
        redirectAttributes.addFlashAttribute("success", "Adresse ajoutée avec succès !");
        return "redirect:/profile#retraits";
    }
}

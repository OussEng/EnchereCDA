package fr.eni.enchere.user.controller;

import fr.eni.enchere.security.UserPrincipal;
import fr.eni.enchere.user.bll.UserService;
import fr.eni.enchere.user.bo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping
    public String getUser(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        model.addAttribute("user", userPrincipal.getUser());
        return "/userProfil/profile";
    }

    @GetMapping("utilisateurs")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAll());
        return "utilisateur";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model){
        model.addAttribute("message", "Utilisateur récupéré avec succes");
        model.addAttribute("userById", userService.getById(id));
        return "/userProfil/profile";
    }

    @PostMapping
    public String createUser(@Valid @RequestBody User user, Model model){
        model.addAttribute("newUser", "Utilisateur créer avec succes");
        return "/userProfil/profile";
    }

    @PostMapping("/modif")
    public String updateUser(@ModelAttribute User user, BindingResult bindingResult,
                             @RequestParam String motDePasseActuel, RedirectAttributes redirectAttributes,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            Map<String, String> erreurs = new HashMap<>();
            bindingResult.getFieldErrors()
                    .forEach(e -> erreurs.put(e.getField(), e.getDefaultMessage()));
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            return "redirect:/profile";
        }

        // Vérifie le mot de passe actuel via authenticate()
        try {
            String pseudoActuel = SecurityContextHolder.getContext().getAuthentication().getName();

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(pseudoActuel, motDePasseActuel);
            authenticationManager.authenticate(token); // lève une exception si incorrect

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Mot de passe actuel incorrect.");
            return "redirect:/profile";
        }

        userService.update(user.getId(), user); // gère le nouveau mot de passe en interne

        redirectAttributes.addFlashAttribute("succes", "Profil mis à jour avec succès !");
        return "redirect:/profile";
    }

    @DeleteMapping("/supprimer/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request) {
        userService.deleteById(id);
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/recherche/email")
    public String getUserByEmail(@Valid @RequestParam String email, Model model){
        model.addAttribute("userEmail", userService.getByEmail(email));
        return "/userProfil/profile";
    }

    @GetMapping("/recherche/pseudo")
    public String getUserByPseudo(@Valid @RequestParam String pseudo , Model model){
        model.addAttribute("userPseudo", userService.getByPseudo(pseudo));
        return "/userProfil/profile";
    }
}

package fr.eni.enchere.user.controller;

import fr.eni.enchere.security.UserPrincipal;
import fr.eni.enchere.user.bll.UserService;
import fr.eni.enchere.user.bo.User;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
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

    @PatchMapping("/modifier")
    public String updateUser(@Valid @RequestBody User user, Model model){
        model.addAttribute("updateUser", "Utilisateur modifié avec succes");
        return "/userProfil/profile";
    }

    @DeleteMapping("supprimer/{id}")
    public String deleteUser(@PathVariable Long id ,Model model){
        model.addAttribute("deleteUser", "Utilisateur supprimé avec succes");
        return "/userProfil/profile";
    }

    @GetMapping("recherche/email")
    public String getUserByEmail(@Valid @RequestParam String email, Model model){
        model.addAttribute("userEmail", userService.getByEmail(email));
        return "/userProfil/profile";
    }

    @GetMapping("recherche/pseudo")
    public String getUserByPseudo(@Valid @RequestParam String pseudo , Model model){
        model.addAttribute("userPseudo", userService.getByPseudo(pseudo));
        return "/userProfil/profile";
    }
}

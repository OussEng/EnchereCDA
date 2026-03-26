package fr.eni.enchere.security;

import fr.eni.enchere.user.bll.UserService;
import fr.eni.enchere.user.bo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@Profile("mysql")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public String auth(){
        return "Auth/pages/auth";
    }

    @GetMapping("/mot-de-passe-oublie")
    public String mdpOublier(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("success", "Un mail vous à été envoyer, vous pouvez modifier votre mot de passe.");
        return "Auth/pages/mdpOublier";
    }

    @PostMapping("/inscription")
    public String inscription(@Valid @ModelAttribute User user,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            Map<String, String> erreurs = new HashMap<>();
            bindingResult.getFieldErrors()
                    .forEach(e -> erreurs.put(e.getField(), e.getDefaultMessage()));
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            return "redirect:/auth";
        }

        String motDePasseClair = user.getMotDePasse();
        userService.save(user);

        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(user.getPseudo(), motDePasseClair);

            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            redirectAttributes.addFlashAttribute("success", "Inscription réussie.");

        } catch (Exception e) {
            System.out.println("Erreur login : " + e.getMessage());
            redirectAttributes.addFlashAttribute("erreur", "Inscription réussie mais connexion échouée.");
            return "redirect:/auth";
        }
        return "redirect:/";
    }
}

package fr.eni.enchere.user.controller;

import fr.eni.enchere.article.bll.ArticleService;
import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.enchere.bll.EnchereService;
import fr.eni.enchere.retrait.bll.RetraitService;
import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.security.AuthenticatedUser;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthenticatedUser authenticatedUser;
    private final ArticleService articleService;
    private final EnchereService enchereService;
    private final RetraitService retraitService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, AuthenticatedUser authenticatedUser, ArticleService articleService, EnchereService enchereService, RetraitService retraitService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authenticatedUser = authenticatedUser;
        this.articleService = articleService;
        this.enchereService = enchereService;
        this.retraitService = retraitService;
    }

    @GetMapping
    public String getUser( Model model) {
        User user = userService.getById(authenticatedUser.get().getId());
        model.addAttribute("user", user);
        //model.addAttribute("enchere", enchereService.)

        List<Retrait> retraits = retraitService.getRetraitsByUserId(authenticatedUser.get().getId());
        model.addAttribute("retraits", retraits);

        List<Article> articles = articleService.findByVendeurId(authenticatedUser.get().getId());
        model.addAttribute("articles", articles);

        return "/Profile/pages/profile";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model){
        model.addAttribute("message", "Utilisateur récupéré avec succes");
        model.addAttribute("userById", userService.getById(id));
        return "/Profile/pages/profile";
    }

    @PostMapping("/modif")
    public String updateUser(@Valid @ModelAttribute User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes
                             ) {

        if (bindingResult.hasErrors()) {
            Map<String, String> erreurs = new HashMap<>();
            bindingResult.getFieldErrors()
                    .forEach(e -> erreurs.put(e.getField(), e.getDefaultMessage()));
            redirectAttributes.addFlashAttribute("erreurs", erreurs);
            return "redirect:/profile";
        }

        userService.update(user.getId(), user);

        redirectAttributes.addFlashAttribute("success", "Profil mis à jour avec succès !");

        return "redirect:/profile";
    }

    @DeleteMapping("/supprimer-compte/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        request.getSession().invalidate();
        redirectAttributes.addFlashAttribute("success", "Profil supprimé avec succès !");
        return "redirect:/";
    }

    @PostMapping("/supprimer/{id}")
    public String desactivateAccount(@PathVariable Long id, HttpServletRequest request) {
        userService.desactivateAccount(id);
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/recherche/email")
    public String getUserByEmail(@Valid @RequestParam String email, Model model, RedirectAttributes redirectAttributes){
        model.addAttribute("userEmail", userService.getByEmail(email));
        redirectAttributes.addFlashAttribute("success", "Profil supprimé avec succès !");
        return "/Profile/pages/profile";
    }

    @GetMapping("/recherche/pseudo")
    public String getUserByPseudo(@Valid @RequestParam String pseudo , Model model){
        model.addAttribute("userPseudo", userService.getByPseudo(pseudo));
        return "/Profile/pages/profile";
    }
}

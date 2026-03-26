package fr.eni.enchere.user.controller;

import fr.eni.enchere.article.bll.ArticleService;
import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.enchere.bll.EnchereService;
import fr.eni.enchere.retrait.bll.RetraitService;
import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.security.AuthenticatedUser;
import fr.eni.enchere.user.bll.UserService;
import fr.eni.enchere.user.bo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final AuthenticatedUser auth;

    public UserController(AuthenticationManager authenticationManager, UserService userService, AuthenticatedUser authenticatedUser, ArticleService articleService, EnchereService enchereService, RetraitService retraitService, AuthenticatedUser auth) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authenticatedUser = authenticatedUser;
        this.articleService = articleService;
        this.enchereService = enchereService;
        this.retraitService = retraitService;
        this.auth = auth;
    }

    @GetMapping
    public String getUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getByPseudo(userDetails.getUsername());
        model.addAttribute("user", user);

        List<Retrait> retraits = retraitService.getRetraitsByUserId(user.getId());
        model.addAttribute("retraits", retraits);

        List<Article> articles = articleService.getByUserId(user.getId());
        model.addAttribute("articles", articles);

        model.addAttribute("mesArticles", articleService.getBidsByUser(auth.get().getId()));
        model.addAttribute("currentUser", userService.getById(auth.get().getId()));

        return "/Profile/pages/profile";
    }

    @GetMapping("/credits")
    public String showCredits(@AuthenticationPrincipal UserDetails userDetails,Model model){
        User user = userService.getByPseudo(userDetails.getUsername());
        model.addAttribute("user", user);
        return "/Credits/pages/credit";
    }

    @PostMapping("/ajouter-credits")
    public String addCredit(@AuthenticationPrincipal UserDetails userDetails,@RequestParam int montant, RedirectAttributes redirectAttributes){
        User user = userService.getByPseudo(userDetails.getUsername());
        user.setCredit(user.getCredit() + montant);
        userService.updateCredit(user);

        redirectAttributes.addFlashAttribute("success", "Achat éffectué avec succes");
        return "redirect:/profile/credits";
    }


    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model){
        model.addAttribute("message", "Utilisateur récupéré avec succes");

        model.addAttribute("user", userService.getById(id));
        return "userProfil/profile";
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


    @GetMapping("/user/{pseudo}")
    public String getUserByPseudo(@Valid @PathVariable String pseudo , Model model){
        model.addAttribute("articles", articleService.getByUserId(userService.getByPseudo(pseudo).getId()));
        model.addAttribute("user", userService.getByPseudo(pseudo));
        return "userProfil/userProfile";

    }


}

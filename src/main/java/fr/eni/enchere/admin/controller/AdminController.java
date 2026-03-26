package fr.eni.enchere.admin.controller;

import fr.eni.enchere.article.bll.ArticleService;
import fr.eni.enchere.user.bll.UserService;
import fr.eni.enchere.user.bo.Roles;
import fr.eni.enchere.user.bo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ArticleService articleService;

    public AdminController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping
    public String showAdmin(@AuthenticationPrincipal UserDetails userDetails, Model model){
        model.addAttribute("users", userService.getAll());
        model.addAttribute("userSuperAdmin", userDetails.getUsername());
        model.addAttribute("articles", articleService.getAll());
        return "admin/pages/admin";
    }

    @DeleteMapping("/supprimer-compte/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        request.getSession().invalidate();
        redirectAttributes.addFlashAttribute("success", "Profil supprimé avec succès !");
        return "redirect:/";
    }

    @DeleteMapping("/supprimer-article/{id}")
    public String delete(@PathVariable Long id){
        articleService.deleteById(id);
        return "home";
    }

    @PostMapping("/modifier-role/{id}")
    public String modifierRole(@PathVariable Long id,
                               @RequestParam String role,
                               RedirectAttributes redirectAttributes) {
        userService.updateRole(id, role);
        redirectAttributes.addFlashAttribute("succes", "Rôle modifié avec succès.");
        return "redirect:/admin";
    }

    @PostMapping("/supprimer/{id}")
    public String desactivateAccount(@PathVariable Long id, HttpServletRequest request) {
        userService.desactivateAccount(id);
        request.getSession().invalidate();
        return "redirect:/admin";
    }

}

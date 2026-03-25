package fr.eni.enchere.admin.controller;

import fr.eni.enchere.article.bll.ArticleService;
import fr.eni.enchere.user.bll.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

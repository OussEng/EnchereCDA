package fr.eni.enchere.user.controller;


import fr.eni.enchere.user.bll.UserService;
import fr.eni.enchere.user.bo.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("utilisateurs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAll());
        return "user";
    }

    @GetMapping("/${id}")
    public String getUserById(Long id, Model model){
        model.addAttribute("user", userService.deleteById(id));
        return "user";
    }

    @PostMapping
    public String createUser(@Valid User user, Model model){
        model.addAttribute("newUser", userService.save(user));
        return "user";
    }

    @PatchMapping
    public String updateUser(@Valid User user, Model model){
        model.addAttribute("updateUser", userService.save(user));
        return "user";
    }

    @DeleteMapping("/${id}")
    public String deleteUser(Long id ,Model model){
        model.addAttribute("deleteUser", userService.deleteById(id));
        return "user";
    }

    @GetMapping("/email")
    public String getUserByEmail(@Valid @RequestParam String email, Model model){
        model.addAttribute("user", userService.getByEmail(email));
        return "user";
    }

    public String getUserByPseudo(@Valid @RequestParam String pseudo , Model model){
        model.addAttribute("user", userService.getByPseudo(pseudo));
        return "user";
    }



}

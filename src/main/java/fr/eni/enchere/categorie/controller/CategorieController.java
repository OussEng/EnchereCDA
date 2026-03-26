package fr.eni.enchere.categorie.controller;

import fr.eni.enchere.categorie.bll.CategorieService;
import fr.eni.enchere.categorie.bo.Categorie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }


    @PostMapping("/ajouter-categorie")
    public String add(@ModelAttribute Categorie categorie) {
        categorieService.save(categorie);
        return "redirect:/admin";
    }

    @PostMapping("/supprimer-categorie/{id}")
    public String delete(@PathVariable Long id) {
        categorieService.delete(id);
        return "redirect:/admin";
    }


}

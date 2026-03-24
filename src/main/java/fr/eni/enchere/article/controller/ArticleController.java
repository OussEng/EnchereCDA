
package fr.eni.enchere.article.controller;

import fr.eni.enchere.article.bll.ArticleService;
import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.categorie.bll.CategorieService;
import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.retrait.bll.RetraitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/encheres")
public class ArticleController {

    private final ArticleService articleService;
    private final CategorieService categorieService;
    private final RetraitService retraitService;
    private List<Article> articles;

    public ArticleController(ArticleService articleService, CategorieService categorieService, RetraitService retraitService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.retraitService = retraitService;
    }
    @GetMapping("")
    public String Redirect(){
        return "redirect:/encheres/";
    }


    @GetMapping("/")
    public String listArticles(Model model){
        List<Article> articles = articleService.getAll();

        model.addAttribute("articles", articles);
        return "home";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model){
        Optional<Article> article = articleService.getById(id);
       model.addAttribute("article", article.orElse(null));
        return "fragments/article/view-article-detail";
    }

    @GetMapping("/{id}/enchere")
    public String addEnchere(@PathVariable Long id, Model model){
        Optional<Article> article = articleService.getById(id);
        model.addAttribute("article", article.orElse(null));
        return "fragments/article/view-article-detail";
    }

    @GetMapping("/vendeur/{id}")
    public String findByVendeurId(@PathVariable Long id){
        articleService.getById(id);
        return "home";
    }

    @GetMapping("/create")
    public String pageCreationVente(Model model){
        model.addAttribute("categories",categorieService.getAll());
        model.addAttribute("newArticle", new Article());

        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        model.addAttribute("nowPlusUnMois", now.plusMonths(1));

        model.addAttribute("retraits", retraitService.getRetraitsByUserId(2L));

        return "fragments/article/create-vente.html";
    }

    @PostMapping("/create")
    public String createVente(@ModelAttribute("venteForm") Article article){

    articleService.create(article);
        return "redirect:/article";
    }

    @PutMapping("/update")
    public String update(){
        return "home";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        articleService.deleteById(id);
        return "home";
    }



}

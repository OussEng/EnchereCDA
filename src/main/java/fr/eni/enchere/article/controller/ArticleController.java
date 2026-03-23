
package fr.eni.enchere.article.controller;

import fr.eni.enchere.article.bll.ArticleService;
import fr.eni.enchere.article.bo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("")
    public String listArticles(Model model){
        List<Article> articles = articleService.getAll();

        model.addAttribute("articles", articles);
        return "home";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
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

    @PostMapping("/save")
    public String save(){
        return "home";
    }

    @PutMapping("/update")
    public String update(){
        return "home";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        articleService.deleteById(id);
        return "home";
    }



}

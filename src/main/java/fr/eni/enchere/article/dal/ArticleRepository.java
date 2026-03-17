package fr.eni.enchere.article.dal;

import fr.eni.enchere.article.bo.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepository {

    //jdbc TODO
    //jdbc template.

    public List<Article> findAll() {
        return List.of();
    }
}

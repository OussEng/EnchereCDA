package fr.eni.enchere.categorie.dal.dao;

import fr.eni.enchere.article.bo.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieRepository {
    public List<Article> findAll() {
        return List.of();
    }
}

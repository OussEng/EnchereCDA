package fr.eni.enchere.article.dal.dao;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.bo.enums.Etat_Article;

import java.util.List;
import java.util.Optional;

public interface IArticleDAO {

    public List<Article> findAll();


    List<Article> findByVendeurId(Long vendeurId);

    void save(Article article);

    void update(Article article);

    void deleteById(Long id);
}

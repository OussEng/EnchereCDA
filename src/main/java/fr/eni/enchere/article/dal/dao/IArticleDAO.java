package fr.eni.enchere.article.dal.dao;

import fr.eni.enchere.article.bo.Article;

import java.util.List;
import java.util.Optional;
public interface IArticleDAO {

    List<Article> findAll();

    Optional<Article> findById(Long id);


    List<Article> findByVendeurId(Long vendeurId);

    void save(Article article);

    void update(Article article);

    void deleteById(Long id);

    List<Article> getByName(String v);

    List<Article> findActive();
}

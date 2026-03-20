package fr.eni.enchere.article.dal.dao;

import fr.eni.enchere.article.bo.Article;

import java.util.List;
import java.util.Optional;

public interface IArticleDAO {

    public List<Article> findAll();

    Optional<Article> findById(Long id);


}

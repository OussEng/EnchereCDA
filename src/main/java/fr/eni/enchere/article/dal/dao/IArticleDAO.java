package fr.eni.enchere.article.dal.dao;

import fr.eni.enchere.article.bo.Article;

import java.util.List;

public interface IArticleDAO {

    public List<Article> findAll();


}

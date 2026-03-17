package fr.eni.enchere.article.dal.dao;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.dal.ArticleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleDAO implements IArticleDAO{

    private final ArticleRepository articleRepository;

    public ArticleDAO(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}

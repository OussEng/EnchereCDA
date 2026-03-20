package fr.eni.enchere.article.dal.dao;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.article.dal.ArticleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Profile("mysql")
@Component
@Profile("mysql")
public class ArticleDAO implements IArticleDAO{

    private final ArticleRepository articleRepository;

    public ArticleDAO(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }


    @Override
    public void save(Article article) {
        //TODO
    }

    @Override
    public void update(Article article) {
        //TODO
    }

    @Override
    public void deleteById(Long id) {
        //TODO
    }
}

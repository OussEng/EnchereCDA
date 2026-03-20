package fr.eni.enchere.article.bll;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.dal.dao.IArticleDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final IArticleDAO articleDAO;

    public ArticleService(IArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public List<Article> getAll(){
        return articleDAO.findAll();
    }

    public void create(Article article){
        articleDAO.save(article);
    }

    public Optional<Article> getById(Long id){
        return articleDAO.findById(id);
    }

    public List<Article> findByVendeurId(Long vendeurId){
        return articleDAO.findByVendeurId(vendeurId);
    }

    public void update(Article article){
        articleDAO.update(article);
    }

    public void deleteById(Long id){
        articleDAO.deleteById(id);
    }

}

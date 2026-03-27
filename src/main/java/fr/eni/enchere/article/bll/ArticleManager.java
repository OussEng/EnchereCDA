package fr.eni.enchere.article.bll;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.article.dal.dao.IArticleDAO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ArticleManager {

    private final IArticleDAO articleDAO;


    public ArticleManager(IArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }


    public void handleState(Article article){
            if (article.getEtatEnchere() == Etat_Article.CREEE){
                if (article.getDateDebutEncheres().isEqual(LocalDateTime.now()) || article.getDateDebutEncheres().isBefore(LocalDateTime.now())){
                    article.setEtatEnchere(Etat_Article.EN_COURS);
                    articleDAO.update(article);
                }
            }

            else if (article.getEtatEnchere() == Etat_Article.EN_COURS){
                if (article.getDateFinEncheres().isEqual(LocalDateTime.now()) || article.getDateFinEncheres().isBefore(LocalDateTime.now())){
                    article.setEtatEnchere(Etat_Article.TERMINEES);
                    articleDAO.update(article);
                }
            }
    }


    public void handleStateAll()
    {
        articleDAO.findActive().forEach(this::handleState);
    }


    public void assignAuctionWinner(Article article){

            if (article.getEtatEnchere() == Etat_Article.TERMINEES){
                article.setAcheteur(article.getCurrentBidder());
                if (article.getAcheteur() == article.getCurrentBidder()){
                    article.setEtatEnchere(Etat_Article.EFFECTUE);
                    articleDAO.update(article);
                }
            }
    }


    public void assignAuctionWinnerAll(){
        articleDAO.findFinished().forEach(this::assignAuctionWinner);
    }

    @Scheduled(fixedRate = 30000)
    public void manageAllAuctions(){
        handleStateAll();
        assignAuctionWinnerAll();
    }


    public void manageAuction(Article article){
        handleState(article);
        assignAuctionWinner(article);
    }


}

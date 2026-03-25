package fr.eni.enchere.article.bll;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.article.dal.dao.IArticleDAO;
import fr.eni.enchere.categorie.bll.CategorieService;
import fr.eni.enchere.enchere.bll.EnchereService;
import fr.eni.enchere.retrait.bll.RetraitService;
import fr.eni.enchere.security.AuthenticatedUser;
import fr.eni.enchere.user.bll.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class StateHandler {

    private final IArticleDAO articleDAO;
    private final CategorieService categorieService;
    private final RetraitService retraitService;
    private final AuthenticatedUser auth;
    private final EnchereService enchereService;
    private final UserService userService;

    public StateHandler(IArticleDAO articleDAO, CategorieService categorieService, RetraitService retraitService, AuthenticatedUser auth, EnchereService enchereService, UserService userService) {
        this.articleDAO = articleDAO;
        this.categorieService = categorieService;
        this.retraitService = retraitService;
        this.auth = auth;
        this.enchereService = enchereService;
        this.userService = userService;
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

    @Scheduled(fixedRate = 30000)
    public void handleStateAll()
    {
        articleDAO.findActive().forEach(this::handleState);
    }
}

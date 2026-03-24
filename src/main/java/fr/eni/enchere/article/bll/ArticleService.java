package fr.eni.enchere.article.bll;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.article.dal.dao.IArticleDAO;
import fr.eni.enchere.categorie.bll.CategorieService;
import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.enchere.bll.EnchereService;
import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.retrait.bll.RetraitService;
import fr.eni.enchere.security.AuthenticatedUser;
import fr.eni.enchere.user.bll.UserService;
import fr.eni.enchere.user.bo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final IArticleDAO articleDAO;
    private final CategorieService categorieService;
    private final RetraitService retraitService;
    private final AuthenticatedUser auth;
    private final EnchereService enchereService;
    private final UserService userService;

    public ArticleService(IArticleDAO articleDAO, CategorieService categorieService, RetraitService retraitService, AuthenticatedUser auth, EnchereService enchereService, UserService userService) {
        this.articleDAO = articleDAO;
        this.categorieService = categorieService;
        this.retraitService = retraitService;
        this.auth = auth;
        this.enchereService = enchereService;
        this.userService = userService;
    }

    public List<Article> getAll(){
        return articleDAO.findAll();
    }

    public void create(Article article){
        article.setCategorie(categorieService.getById(article.getCategorie().getId()).orElse(null));
        article.setEtatEnchere(Etat_Article.CREEE);
        article.setLieuRetrait(retraitService.getRetraitById(article.getLieuRetrait().getId()).orElse(null));
        article.setPrixVente(article.getMiseAPrix());
        article.setVendeur(auth.get());

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

    public List<Article> getByFilter(){
        return articleDAO.findAll();
    }

    @Transactional
    public void bid(int amount, Article article) {
        int previousPrice = article.getCurrentPrice();
        User encherit = auth.get();
        User lastBidder = article.getCurrentBidder();

        if (lastBidder != null) {
            lastBidder.setCredit(lastBidder.getCredit() + previousPrice);
            userService.updateCredit(lastBidder);
            encherit.setCredit(encherit.getCredit() - amount);
            userService.updateCredit(encherit);
        }


        Enchere enchere = new Enchere();
        enchere.setEncherit(encherit);
        enchere.setDateEnchere(LocalDateTime.now());
        enchere.setMontant(amount);
        enchere.setArticle(article);




        enchereService.create(enchere);
    }
}

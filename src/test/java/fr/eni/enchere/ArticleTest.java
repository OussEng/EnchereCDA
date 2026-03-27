package fr.eni.enchere;

import fr.eni.enchere.article.bll.ArticleService;
import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.article.dal.dao.IArticleDAO;
import fr.eni.enchere.categorie.bll.CategorieService;
import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.retrait.bll.RetraitService;
import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.security.AuthenticatedUser;
import fr.eni.enchere.user.bo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleTest {

    @Mock
    private IArticleDAO articleDAO;

    @Mock
    private CategorieService categorieService;

    @Mock
    private RetraitService retraitService;

    @Mock
    private AuthenticatedUser auth;


    @InjectMocks
    private ArticleService articleService;

    @Test
    void createArticleTest() {
        String nom = "Swotch 2";
        String description = "Swotch 2";
        LocalDateTime dateDebut = LocalDateTime.parse("2026-03-25T16:17");
        LocalDateTime dateFin = LocalDateTime.parse("2026-04-25T16:17");
        int misAPrix = 469;
        User vendeur = new User(2L, "mmartin", "Marie", "Martin", "marie.martin@email.com", "0123456789", "$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi");
        vendeur.setCredit(1200);
        Categorie categorie = new Categorie();
        categorie.setId(1L);
        Retrait lieuRetrait = new Retrait();
        lieuRetrait.setId(1L);
        Article article = new Article();
        article.setNom(nom);
        article.setDescription(description);
        article.setDateDebutEncheres(dateDebut);
        article.setDateFinEncheres(dateFin);
        article.setMiseAPrix(misAPrix);
        article.setCategorie(categorie);
        article.setLieuRetrait(lieuRetrait);

        when(categorieService.getById(1L)).thenReturn(Optional.of(categorie));
        when(retraitService.getRetraitById(1L)).thenReturn(Optional.of(lieuRetrait));
        when(auth.get()).thenReturn(vendeur);

        articleService.create(article);

        assertEquals(Etat_Article.CREEE, article.getEtatEnchere());
        assertEquals(misAPrix, article.getPrixVente());
        assertEquals(vendeur, article.getVendeur());
        verify(articleDAO).save(article);
    }
}
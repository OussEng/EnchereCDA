package fr.eni.enchere.enchere.bo;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.user.bo.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class Enchere {

    private Long id;

    @NotNull(message = "La date de l'enchère est obligatoire")
    private LocalDateTime dateEnchere;

    @Positive(message = "Le montant doit être supérieur à 0")
    private int montant;

    @NotNull(message = "L'article doit être défini")
    @Valid
    private Article article;

    @NotNull(message = "L'utilisateur doit être défini")
    @Valid
    private User encherit;

    public Enchere() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getEncherit() {
        return encherit;
    }

    public void setEncherit(User encherit) {
        this.encherit = encherit;
    }

    public LocalDateTime getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDateTime dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
}

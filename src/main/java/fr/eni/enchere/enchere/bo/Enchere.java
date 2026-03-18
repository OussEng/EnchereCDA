package fr.eni.enchere.enchere.bo;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.user.bo.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Enchere {

    private Long id;
    private LocalDateTime dateEnchere;
    private int montant;
    private Article article;
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

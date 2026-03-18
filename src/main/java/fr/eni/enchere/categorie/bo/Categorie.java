package fr.eni.enchere.categorie.bo;

import fr.eni.enchere.article.bo.Article;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
    private Long id;
    private String libelle;
    private final List<Article> articles = new ArrayList<>();

    public Categorie(){}

    public List<Article> getArticles() {
        return articles;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

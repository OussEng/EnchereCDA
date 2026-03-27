package fr.eni.enchere.categorie.bo;

import fr.eni.enchere.article.bo.Article;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
    private Long id;
    @NotBlank(message = "Le libellé de la catégorie ne peut pas être vide")
    private String libelle;

    @Valid
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

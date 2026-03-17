package fr.eni.enchere.article.bo;

import java.time.LocalDate;

public class Article {

    private Long id;
    private String nom;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int miseAPrix;
    private int prixVente;

    public Article() {
    }
}

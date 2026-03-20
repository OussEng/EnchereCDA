package fr.eni.enchere.article.bo;

import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.user.bo.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Article {

    private Long id;
    private String nom;
    private String description;
    private LocalDateTime dateDebutEncheres;
    private LocalDateTime dateFinEncheres;
    private int miseAPrix;
    private int prixVente;

    private User vendeur;
    private User acheteur;

    private Categorie categorie;
    private Etat_Article etatEnchere;

    private final List<Enchere> encheres = new ArrayList<>();

    private Retrait lieuRetrait;


    public Article() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDateTime getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(int miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public User getVendeur() {
        return vendeur;
    }

    public void setVendeur(User vendeur) {
        this.vendeur = vendeur;
    }

    public User getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(User acheteur) {
        this.acheteur = acheteur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Etat_Article getEtatEnchere() {
        return etatEnchere;
    }

    public void setEtatEnchere(Etat_Article etatEnchere) {
        this.etatEnchere = etatEnchere;
    }

    public List<Enchere> getEncheres() {
        return encheres;
    }

    public List<Enchere> getSortedEncheres() {
        return encheres.stream()
                .sorted(Comparator.comparingInt(Enchere::getMontant).reversed())
                .toList();
    }

    public Retrait getLieuRetrait() {
        return lieuRetrait;
    }

    public void setLieuRetrait(Retrait lieuRetrait) {
        this.lieuRetrait = lieuRetrait;
    }

    public int getCurrentPrice() {
        return encheres.stream()
                .mapToInt(Enchere::getMontant)
                .max()
                .orElse(this.miseAPrix);
    }
}

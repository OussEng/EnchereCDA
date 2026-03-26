package fr.eni.enchere.article.bo;

import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.user.bo.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Article {

    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    private String description;

    @NotNull(message = "La date de début des enchères est obligatoire")
    private LocalDateTime dateDebutEncheres;

    @NotNull(message = "La date de fin des enchères est obligatoire")
    private LocalDateTime dateFinEncheres;

    @Positive(message = "La mise à prix doit être positive")
    private int miseAPrix;

    @PositiveOrZero(message = "Le prix de vente doit être positif ou nul")
    private int prixVente;


    @Valid
    private User vendeur;

    @Valid
    private User acheteur;

    @NotNull()
    @Valid
    private Categorie categorie;

    @NotNull()
    private Etat_Article etatEnchere;

    @Valid
    private final List<Enchere> encheres = new ArrayList<>();

    @Valid
    @NotNull()
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

    public User getCurrentBidder() {
        return encheres.stream()
                .max(Comparator.comparingInt(Enchere::getMontant))
                .map(Enchere::getEncherit)
                .orElse(null);
    }

    public boolean isCreee() {
        return this.etatEnchere == Etat_Article.CREEE;
    }
}

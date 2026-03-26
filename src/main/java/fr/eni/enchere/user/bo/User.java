package fr.eni.enchere.user.bo;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.retrait.bo.Retrait;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;

    @NotBlank(message = "Veuillez saisir votre pseudo.")
    private String pseudo;

    @NotBlank(message = "Veuillez saisir votre prénom.")
    private String prenom;

    @NotBlank(message = "Veuillez saisir votre nom de famille.")
    private String nom;

    @Email(message = "Veuillez saisir une adresse email valide.")
    @NotBlank(message = "Veuillez saisir votre email.")
    private String email;

    @Size(min = 9, max = 10, message = "Veuillez saisir un numéro de téléphone valide (9 à 10 chiffres).")
    private String telephone;

    @NotBlank(message = "Veuillez saisir votre mot de passe.")
    @Size(min = 8, message = "Votre mot de passe doit contenir au moins 8 caractères.")
    private String motDePasse;

    private boolean actif = true;

    @PositiveOrZero(message = "Le crédit ne peut pas être inférieur à zéro.")
    private int credit = 0;

    private final List<Retrait> adresses = new ArrayList<>();

    private final List<Article> articles = new ArrayList<>();
    private final List<Enchere> encheres = new ArrayList<>();

    private Roles roles = Roles.USER;

    public User() {
    }

    public User(Long id, String pseudo, String prenom, String nom, String email, String telephone, String motDePasse) {
        this.id = id;
        this.pseudo = pseudo;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
    }

    public User(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public List<Retrait> getAdresses() {
        return adresses;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public List<Enchere> getEncheres() {
        return encheres;
    }
}

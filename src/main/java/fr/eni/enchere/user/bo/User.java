package fr.eni.enchere.user.bo;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.retrait.bo.Retrait;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;

    @NotBlank(message = "Le pseudo ne peut pas etre vide." )
    private String pseudo;

    @NotBlank(message = "Le prénom ne peut pas etre vide." )
    private String prenom;

    @NotBlank(message = "Le nom ne peut pas etre vide." )
    private String nom;

    @Email
    @NotBlank(message = "Le mail ne peut pas etre vide." )
    private String email;

    @Size(min = 9, max = 10)
    private String telephone;

    private String motDePasse;

    @Size(min = 0)
    private int credit = 0;

    private final List<Retrait> adresses = new ArrayList<>();

    private final List<Article> articles = new ArrayList<>();
    private final List<Enchere> encheres = new ArrayList<>();

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

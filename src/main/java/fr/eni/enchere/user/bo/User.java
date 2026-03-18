package fr.eni.enchere.user.bo;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.retrait.bo.Retrait;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String pseudo;
    private String prenom;
    private String nom;
    private String email;
    private String telephone;
    private String motDePasse;
    private int credit;

    private final List<Retrait> adresses = new ArrayList<>();

    private final List<Article> articles = new ArrayList<>();
    private final List<Enchere> encheres = new ArrayList<>();

    public User() {
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

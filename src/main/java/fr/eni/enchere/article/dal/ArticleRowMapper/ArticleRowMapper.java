package fr.eni.enchere.article.dal.ArticleRowMapper;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.user.bo.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArticleRowMapper implements RowMapper<Article> {
    @Nullable
    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {

        Article article = new Article();

        article.setId(rs.getLong("id"));
        article.setNom(rs.getString("nom_article"));
        article.setDescription(rs.getString("description"));
        article.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
        article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
        article.setMiseAPrix(rs.getInt("mise_a_prix"));

        int prixVente = rs.getInt("prix_vente");
        article.setPrixVente(prixVente);

        article.setEtatEnchere(Etat_Article.valueOf(rs.getString("etat_vente")));

        User vendeur = new User();
        vendeur.setId(rs.getLong("vendeur_id"));
        vendeur.setNom(rs.getString("vendeur_nom"));
        vendeur.setPrenom(rs.getString("vendeur_prenom"));
        vendeur.setPseudo(rs.getString("vendeur_pseudo"));
        vendeur.setEmail(rs.getString("vendeur_email"));
        vendeur.setTelephone(rs.getString("vendeur_telephone"));
        article.setVendeur(vendeur);

        long acheteurId = rs.getLong("acheteur_id");
        if (!rs.wasNull()) {
            User acheteur = new User();
            acheteur.setId(acheteurId);
            article.setAcheteur(acheteur);
        }

        Categorie categorie = new Categorie();
        categorie.setId(rs.getLong("categorie_id"));
        categorie.setLibelle(rs.getString("categorie_libelle"));
        article.setCategorie(categorie);

        Retrait retrait = new Retrait();
        retrait.setId(rs.getLong("retrait_id"));
        retrait.setCodePostal(rs.getString("retrait_code_postal"));
        retrait.setRue(rs.getString("retrait_rue"));
        retrait.setVille(rs.getString("retrait_ville"));
        article.setLieuRetrait(retrait);

        return article;
    }
}

package fr.eni.enchere.article.dal;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.dal.ArticleRowMapper.ArticleRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepository {


    private final JdbcTemplate jdbcTemplate;
    private final ArticleRowMapper articleRowMapper;

    public ArticleRepository(JdbcTemplate jdbcTemplate, ArticleRowMapper articleRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.articleRowMapper = articleRowMapper;
    }

    public List<Article> findAll() {

        return jdbcTemplate.query("""
            SELECT
                a.id,
                a.nom_article,
                a.description,
                a.date_debut_encheres,
                a.date_fin_encheres,
                a.mise_a_prix,
                a.prix_vente,
                a.etat_vente,
                v.id          AS vendeur_id,
                v.pseudo      AS vendeur_pseudo,
                v.nom         AS vendeur_nom,
                v.prenom      AS vendeur_prenom,
                v.email       AS vendeur_email,
                v.telephone   AS vendeur_telephone,
                v.credit      AS vendeur_credit,
                ac.id         AS acheteur_id,
                ac.pseudo     AS acheteur_pseudo,
                ac.nom        AS acheteur_nom,
                ac.prenom     AS acheteur_prenom,
                ac.email      AS acheteur_email,
                ac.telephone  AS acheteur_telephone,
                ac.credit     AS acheteur_credit,
                c.id          AS categorie_id,
                c.libelle     AS categorie_libelle,
                r.id          AS retrait_id,
                r.rue         AS retrait_rue,
                r.code_postal AS retrait_code_postal,
                r.ville       AS retrait_ville
            FROM articles a
            INNER JOIN utilisateurs v  ON v.id  = a.vendeur_id
            LEFT  JOIN utilisateurs ac ON ac.id = a.acheteur_id
            INNER JOIN categories c    ON c.id  = a.categorie_id
            INNER JOIN retraits r      ON r.id  = a.lieu_retrait_id
            """, articleRowMapper);

    }
}

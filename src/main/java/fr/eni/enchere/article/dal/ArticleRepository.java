package fr.eni.enchere.article.dal;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.dal.ArticleRowMapper.ArticleRowMapper;

import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.categorie.dal.CategorieRowMapper.CategorieRowMapper;
import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.enchere.dal.enchereRowMapper.EnchereRowMapper;
import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.enchere.dal.enchereRowMapper.EnchereRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {


    private final JdbcTemplate jdbcTemplate;
    private final ArticleRowMapper articleRowMapper;
    private final EnchereRowMapper enchereRowMapper;

    public ArticleRepository(JdbcTemplate jdbcTemplate, ArticleRowMapper articleRowMapper, EnchereRowMapper enchereRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.articleRowMapper = articleRowMapper;
        this.enchereRowMapper = enchereRowMapper;
    }

    public List<Article> findAll() {

       List<Article> articles =     jdbcTemplate.query("""
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
            INNER JOIN encheres e ON e.article_id = a.id
            """, articleRowMapper);


        for (Article article : articles) {
            List<Enchere> encheres = jdbcTemplate.query("""
                                                          SELECT
                                                           e.id,
                                                           e.date_enchere,
                                                           e.montant,
                                                           e.utilisateur_id,
                                                           u.nom         AS utilisateur_nom,
                                                           u.prenom      AS utilisateur_prenom,
                                                            u.pseudo      AS utilisateur_pseudo,
                                                            u.email       AS utilisateur_email,
                                                             u.telephone   AS utilisateur_telephone
                                                              FROM encheres e
                                                             INNER JOIN utilisateurs u ON u.id = e.utilisateur_id
                                                             WHERE e.article_id = ?
                                                        """, enchereRowMapper, article.getId());

            for (Enchere enchere : encheres){
                article.getEncheres().add(enchere);
            }
        }


        return articles;

    }


    public Optional<Article> findById(Long id){

        try {
            Article article = jdbcTemplate.queryForObject("""
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
                WHERE a.id = ?
                """, articleRowMapper, id);


            List<Enchere> encheres = jdbcTemplate.query("""
                                                          SELECT
                                                           e.id,
                                                           e.date_enchere,
                                                           e.montant,
                                                           e.utilisateur_id,
                                                           u.nom         AS utilisateur_nom,
                                                           u.prenom      AS utilisateur_prenom,
                                                            u.pseudo      AS utilisateur_pseudo,
                                                            u.email       AS utilisateur_email,
                                                             u.telephone   AS utilisateur_telephone
                                                              FROM encheres e
                                                             INNER JOIN utilisateurs u ON u.id = e.utilisateur_id
                                                             WHERE e.article_id = ?
                                                        """, enchereRowMapper, id);


            for (Enchere enchere : encheres){
                assert article != null;
                article.getEncheres().add(enchere);
            }


            return Optional.ofNullable(article);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }


    }

    public void save(Article article){

        jdbcTemplate.update("INSERT INTO articles(nom_article, description, date_debut_encheres, date_fin_encheres, mise_a_prix, prix_vente, vendeur_id, categorie_id, lieu_retrait_id) VALUES (?,?,?,?,?,?,?,?,?)", article.getNom(), article.getDescription(), article.getDateDebutEncheres(), article.getDateFinEncheres(), article.getMiseAPrix(), article.getPrixVente(), article.getVendeur().getId(), article.getCategorie().getId(), article.getLieuRetrait().getId());

    }

    public void update(Article article) {
        jdbcTemplate.update("UPDATE articles SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, mise_a_prix = ?, categorie_id = ?, lieu_retrait_id = ? WHERE articles.id = ?" , article.getNom(), article.getDescription(), article.getDateDebutEncheres(), article.getDateFinEncheres(), article.getMiseAPrix(), article.getCategorie().getId(), article.getLieuRetrait().getId(), article.getId());
    }
}

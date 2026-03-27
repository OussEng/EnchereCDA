package fr.eni.enchere.enchere.dal;

import fr.eni.enchere.enchere.bo.Enchere;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnchereRepository {

    private  final JdbcTemplate jdbcTemplate;

    public EnchereRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Enchere enchere) {
        jdbcTemplate.update("INSERT INTO  encheres (date_enchere, montant,article_id,utilisateur_id) values (?,?,?,?)", enchere.getDateEnchere(),enchere.getMontant(), enchere.getArticle().getId(), enchere.getEncherit().getId());
    }
}

package fr.eni.enchere.categorie.dal;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.categorie.dal.CategorieRowMapper.CategorieRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategorieRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CategorieRowMapper categorieRowMapper;

    public CategorieRepository(JdbcTemplate jdbcTemplate, CategorieRowMapper categorieRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.categorieRowMapper = categorieRowMapper;
    }

    public List<Categorie> findAll() {

        return jdbcTemplate.query("SELECT * FROM categories", categorieRowMapper);

    }

    public Optional<Categorie> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM categories c WHERE c.id = ?", categorieRowMapper, id));
    }

    public void save(Categorie categorie) {
        jdbcTemplate.update("INSERT INTO categories (libelle) VALUES (?)", categorie.getLibelle());
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM categories WHERE id = ?", id);
    }
}

package fr.eni.enchere.retrait.dal;

import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.retrait.dal.RetraitRowMapper.RetraitRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RetraitRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RetraitRowMapper retraitRowMapper;

    public RetraitRepository(JdbcTemplate jdbcTemplate, RetraitRowMapper retraitRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.retraitRowMapper = retraitRowMapper;
    }


    public void save(Retrait retrait, Long id) {

        jdbcTemplate.update("INSERT INTO retraits (utilisateur_id, rue,code_postal,ville) VALUES (?, ?, ?, ?)",id, retrait.getRue(), retrait.getCodePostal(),retrait.getVille());

    }

    public List<Retrait> findByUserId(Long id) {
        return jdbcTemplate.query("SELECT r.id as retrait_id, r.rue as retrait_rue, r.code_postal as retrait_code_postal, r.ville as retraits_ville FROM retraits r JOIN utilisateurs u on r.utilisateur_id = u.id where u.id = ? ", retraitRowMapper, id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM retraits WHERE retraits.id = ?", id);
    }



    public void update(Retrait retrait) {
        jdbcTemplate.update("UPDATE retraits SET rue = ?, code_postal = ?, ville = ? WHERE retraits.id = ? ", retrait.getRue(),retrait.getCodePostal(),retrait.getVille(),retrait.getId());
    }

    public Optional<Retrait> findByid(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT r.id as retrait_id, r.rue as retrait_rue, r.code_postal as retrait_code_postal, r.ville as retraits_ville FROM retraits r WHERE r.id = ?", retraitRowMapper, id));
    }
}

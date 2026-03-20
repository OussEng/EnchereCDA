package fr.eni.enchere.enchere.dal.enchereRowMapper;

import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.user.bo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EnchereRowMapper implements RowMapper<Enchere> {

    private final  JdbcTemplate jdbcTemplate;

    public EnchereRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {

        Enchere enchere = new Enchere();

        enchere.setId(rs.getLong("id"));
        enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime());
        enchere.setMontant(rs.getInt("montant"));

        User encherit = new User();
        encherit.setId(rs.getLong("utilisateur_id"));
        encherit.setNom(rs.getString("utilisateur_nom"));
        encherit.setPrenom(rs.getString("utilisateur_prenom"));
        encherit.setPseudo(rs.getString("utilisateur_pseudo"));
        encherit.setEmail(rs.getString("utilisateur_email"));
        encherit.setTelephone(rs.getString("utilisateur_telephone"));
        enchere.setEncherit(encherit);


        return enchere;
    }
}

package fr.eni.enchere.user.dal.UserRowMapper;

import fr.eni.enchere.user.bo.User;
import jakarta.annotation.Nullable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Nullable
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("id"));
        user.setPseudo(rs.getString("pseudo"));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setEmail(rs.getString("email"));
        user.setCredit(rs.getInt("credit"));
        user.setMotDePasse(rs.getString("mot_de_passe"));

        return user;
    }
}

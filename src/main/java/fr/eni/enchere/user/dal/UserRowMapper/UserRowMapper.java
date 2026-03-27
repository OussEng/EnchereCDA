package fr.eni.enchere.user.dal.UserRowMapper;

import fr.eni.enchere.user.bo.Roles;
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
        user.setTelephone(rs.getString("telephone"));
        user.setMotDePasse(rs.getString("mot_de_passe"));
        user.setActif(rs.getBoolean("actif"));
        user.setCredit(rs.getInt("credit"));
        String roleStr = rs.getString("role"); // ex: ROLE_SUPER_ADMIN
        if (roleStr.startsWith("ROLE_")) {
            roleStr = roleStr.substring(5); // enlève "ROLE_"
        }
        user.setRoles(Roles.valueOf(roleStr));

        return user;
    }
}

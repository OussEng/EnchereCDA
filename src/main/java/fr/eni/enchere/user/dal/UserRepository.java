package fr.eni.enchere.user.dal;

import fr.eni.enchere.user.bo.User;
import fr.eni.enchere.user.dal.UserRowMapper.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;
    private final UserRowMapper userRowMapper;

    public UserRepository(JdbcTemplate jdbc, UserRowMapper userRowMapper) {
        this.jdbc = jdbc;
        this.userRowMapper = userRowMapper;
    }

    public List<User> findAll(){
        return jdbc.query("SELECT * FROM utilisateurs u", userRowMapper);
    }

    public void save(User user) {
        jdbc.update("""
            INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, mot_de_passe, credit)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """,
                user.getPseudo(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getTelephone(),
                user.getMotDePasse(),
                user.getCredit()
        );
    }

    public void update(Long id, User user) {
        jdbc.update("""
            UPDATE utilisateurs
            SET pseudo = ?,
                nom = ?,
                prenom = ?,
                email = ?,
                telephone = ?
            WHERE id = ?
            """,
                user.getPseudo(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getTelephone(),
                id
        );
    }

    public void deleteById(Long id){
        jdbc.update("""
        DELETE FROM utilisateurs
        WHERE id = ?
        """, id);
    }

    public Optional<User> findById(Long id) {
        return jdbc.query("""
            SELECT * FROM utilisateurs
            WHERE id = ?
            """, userRowMapper, id).stream().findFirst();
    }

    public Optional<User> findByEmail(String email){
        return jdbc.query("""
            SELECT * FROM utilisateurs
            WHERE email = ?            
        """, userRowMapper, email).stream().findFirst();
    }

    public Optional<User> findByPseudo(String pseudo){
        return jdbc.query("""
            SELECT * FROM utilisateurs
            WHERE pseudo = ?         
        """, userRowMapper, pseudo).stream().findFirst();
    }

    public void desactivateAccount(Long userId) {
        String sql = "UPDATE utilisateurs SET actif = false WHERE id = ?";
        jdbc.update(sql, userId);
    }
}

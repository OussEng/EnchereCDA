package fr.eni.enchere.user.dal.dao;

import fr.eni.enchere.user.bo.User;
import org.springframework.context.annotation.Profile;
import java.util.List;
import java.util.Optional;

@Profile("mysql")
public interface IUserDAO {
    void save(User user);
    User update(Long id, User user);
    void deleteById(Long id);
    List<User> getAll();
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    Optional<User> getByPseudo(String pseudo);

    void updateCredit(User user);
}

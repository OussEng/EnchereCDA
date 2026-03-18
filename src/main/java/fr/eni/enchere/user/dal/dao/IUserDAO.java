package fr.eni.enchere.user.dal.dao;

import fr.eni.enchere.user.bo.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    void save(User user);
    void update(Long id, User user);
    void deleteById(Long id);
    List<User> getAll();
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    Optional<User> getByPseudo(String pseudo);
}

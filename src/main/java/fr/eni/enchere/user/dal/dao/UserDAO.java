package fr.eni.enchere.user.dal.dao;

import fr.eni.enchere.user.bo.User;
import fr.eni.enchere.user.dal.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("mysql")
@Repository
public class UserDAO implements IUserDAO{
    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        userRepository.update(id, user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void desactivateAccount(Long id) {
        userRepository.desactivateAccount(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getByPseudo(String pseudo) {
        return userRepository.findByPseudo(pseudo);
    }
}

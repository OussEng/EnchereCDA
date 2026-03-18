package fr.eni.enchere.user.dal.dao;

import fr.eni.enchere.user.bo.User;
import fr.eni.enchere.user.dal.UserRepository;

import java.util.List;
import java.util.Optional;

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
    public void update(Long id, User user) {
        userRepository.update(id, user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public Optional<User> getByPseudo(String pseudo) {
        return userRepository.getByPseudo(pseudo);
    }
}

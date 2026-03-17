package fr.eni.enchere.user.dal.dao;

import fr.eni.enchere.user.dal.UserRepository;

public class UserDAO implements IUserDAO{
    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

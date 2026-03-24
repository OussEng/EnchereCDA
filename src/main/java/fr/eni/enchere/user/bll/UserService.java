package fr.eni.enchere.user.bll;

import fr.eni.enchere.exeception.AlreadyExistsException;
import fr.eni.enchere.exeception.UserNotFoundException;
import fr.eni.enchere.user.bo.User;
import fr.eni.enchere.user.dal.dao.IUserDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Profile("mysql")
public class UserService {
    private final IUserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        if (userDAO.getByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyExistsException("L'email existe déjà, connectez-vous !");
        }
        if (userDAO.getByPseudo(user.getPseudo()).isPresent()) {
            throw new AlreadyExistsException("Le pseudo " + user.getPseudo() + " est déjà utilisé !");
        }
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        userDAO.save(user);
    }

    public void update(Long id, User user){
        if (userDAO.getByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyExistsException("L'email existe déjà, connectez-vous !");
        }
        if (userDAO.getByPseudo(user.getPseudo()).isPresent()) {
            throw new AlreadyExistsException("Le pseudo " + user.getPseudo() + " est déjà utilisé !");
        }
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        userDAO.update(id, user);
    }

    public void deleteById( Long id){
        if(userDAO.getById(id).isPresent()){
            userDAO.deleteById(id);
        }
        throw new UserNotFoundException("Utilisateur introuvable.");
    }

    public List<User> getAll(){
        return userDAO.getAll();
    }

    public Optional<User> getById( Long id){
        if(userDAO.getById(id).isPresent()){
            return userDAO.getById(id);
        }
        throw new UserNotFoundException("Utilisateur introuvable.");
    }

    public User getByEmail( String email){
        return userDAO.getByEmail(email).orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable."));
    }

    public User getByPseudo(String pseudo) {
        return userDAO.getByPseudo(pseudo).orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable."));
    }
}

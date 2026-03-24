package fr.eni.enchere.user.bll;

import fr.eni.enchere.exeception.AlreadyExistsException;
import fr.eni.enchere.exeception.UserNotFoundException;
import fr.eni.enchere.security.AuthenticatedUser;
import fr.eni.enchere.user.bo.User;
import fr.eni.enchere.user.dal.dao.IUserDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Profile("mysql")
public class UserService {
    private final IUserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void save(User user) {
        userDAO.getByEmail(user.getEmail()).ifPresent(u -> {
            throw new AlreadyExistsException("L'email existe déjà !");
        });

        userDAO.getByPseudo(user.getPseudo()).ifPresent(u -> {
            throw new AlreadyExistsException("Le pseudo existe déjà !");
        });
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        userDAO.save(user);
    }

    @Transactional
    public void update(Long id, User user) {
        User existing = userDAO.getById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable."));

        // Vérifie si email est utilisé par un autre
        userDAO.getByEmail(user.getEmail())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new AlreadyExistsException("L'email existe déjà !"); });

        // Vérifie si pseudo est utilisé par un autre
        userDAO.getByPseudo(user.getPseudo())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new AlreadyExistsException("Le pseudo existe déjà !"); });

        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));

        userDAO.update(id, user);
    }

    public void deleteById(Long id) {
        userDAO.getById(id).orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable."));
    // TODO : vérifier si l'user a des enchère en cours
        // Supprimer les données liées d'abord
        //enchereDAO.deleteByUserId(id);
        //articleDAO.deleteByUserId(id);
        //retraitDAO.deleteByUserId(id);
        userDAO.deleteById(id);
    }

    public void desactivateAccount(Long id) {
        userDAO.getById(id).orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable."));
        userDAO.desactivateAccount(id);
    }

    public List<User> getAll(){
        return userDAO.getAll();
    }

    public User getById( Long id){
        return userDAO.getById(id).orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable."));
    }

    public User getByEmail( String email){
        return userDAO.getByEmail(email).orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable."));
    }

    public User getByPseudo(String pseudo) {
        return userDAO.getByPseudo(pseudo).orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable."));
    }
}

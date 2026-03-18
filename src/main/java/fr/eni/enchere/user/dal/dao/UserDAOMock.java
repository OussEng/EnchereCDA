package fr.eni.enchere.user.dal.dao;

import fr.eni.enchere.user.bo.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAOMock implements IUserDAO{
    List<User> utilisateurs;

    public UserDAOMock() {

        utilisateurs = new ArrayList<>();

        utilisateurs.addAll(List.of(
                new User(1L, "the_nina",     "Nina",     "Vincent", "nina.vincent23@proton.me",         "+33 6 91 88 42 76", "mdp123"),
                new User(2L, "the_gabriel",  "Gabriel",  "Roux",    "gabriel.roux18@laposte.net",        "+33 6 42 72 75 67", "mdp123"),
                new User(3L, "the_baptiste", "Baptiste", "Vincent", "baptiste.vincent93@gmail.com",      "+33 6 41 71 49 27", "mdp123")
        ));

    }

    @Override
    public void save(User user) {
        utilisateurs.add(new User(user.getId(), user.getPseudo(), user.getPrenom(), user.getNom(), user.getEmail(), user.getTelephone(), user.getMotDePasse()));
    }

    @Override
    public void update(Long id, User user) {
        utilisateurs.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .ifPresent(u -> {
                    int index = utilisateurs.indexOf(u);
                    utilisateurs.set(index, new User(
                            id,
                            user.getPseudo()      != null ? user.getPseudo()      : u.getPseudo(),
                            user.getPrenom()      != null ? user.getPrenom()      : u.getPrenom(),
                            user.getNom()         != null ? user.getNom()         : u.getNom(),
                            user.getEmail()       != null ? user.getEmail()       : u.getEmail(),
                            user.getTelephone()   != null ? user.getTelephone()   : u.getTelephone(),
                            user.getMotDePasse()  != null ? user.getMotDePasse()  : u.getMotDePasse()
                    ));
                });
    }

    @Override
    public void deleteById(Long id) {
        utilisateurs.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public List<User> getAll() {
        return utilisateurs;
    }

    @Override
    public Optional<User> getById(Long id) {
        return utilisateurs.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return utilisateurs.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> getByPseudo(String pseudo) {
        return utilisateurs.stream()
                .filter(user -> user.getPseudo().equals(pseudo))
                .findFirst();
    }
}

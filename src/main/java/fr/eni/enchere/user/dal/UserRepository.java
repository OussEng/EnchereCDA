package fr.eni.enchere.user.dal;

import fr.eni.enchere.user.bo.User;
import fr.eni.enchere.user.dal.dao.UserDAOMock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private UserDAOMock mock;

    public void save(User user){
        mock.save(user);
    }

    public void update(Long id, User user){
        mock.update(id, user);
    }

    public void deleteById(Long id){
        mock.deleteById(id);
    }

    public List<User> getAll(){
        return mock.getAll();
    }

    public Optional<User> getById(Long id){
        return mock.getById(id);
    }

    public Optional<User> getByEmail(String email){
        return mock.getByEmail(email);
    }

    public Optional<User> getByPseudo(String pseudo){
        return mock.getByPseudo(pseudo);
    }

}

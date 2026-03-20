package fr.eni.enchere.user.dal;

import fr.eni.enchere.user.bo.User;
import fr.eni.enchere.user.dal.dao.UserDAOMock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    public void save(User user){

    }

    public void update(Long id, User user){

    }

    public void deleteById(Long id){

    }

    public List<User> findAll(){
        return null;
    }

    public Optional<User> findById(Long id){
        return null;
    }

    public Optional<User> findByEmail(String email){
        return null;
    }

    public Optional<User> findByPseudo(String pseudo){
        return null;
    }

}

package fr.eni.enchere.user.bll;

import fr.eni.enchere.record.ServiceResponse;
import fr.eni.enchere.user.bo.User;
import fr.eni.enchere.user.dal.dao.IUserDAO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class UserService {
    private final IUserDAO userDAO;

    public UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ResponseEntity<ServiceResponse<User>> save(@Valid @RequestBody User user){
        userDAO.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ServiceResponse(HttpStatus.CREATED.value(), "Element enregistré avec succes", user));
        // TODO : Mettre exeception
    }

    public ResponseEntity<ServiceResponse<User>> update(Long id, @Valid @RequestBody User user){
        userDAO.update(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ServiceResponse(HttpStatus.OK.value(), "Element enregistré avec succes", user));
        // TODO : Mettre exeception
    }

    public ResponseEntity<ServiceResponse> deleteById(@PathVariable Long id){
        if(userDAO.getById(id).isPresent()){
            userDAO.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ServiceResponse(HttpStatus.OK.value(), "Element supprimé avec succes", null));
        }
        // TODO : Mettre exeception
        return null;
    }

    public ResponseEntity<ServiceResponse> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ServiceResponse(HttpStatus.OK.value(), "Liste de tous les éléments", userDAO.getAll()));
    }

    public ResponseEntity<ServiceResponse> getById(@PathVariable Long id){
        if(userDAO.getById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ServiceResponse(HttpStatus.OK.value(), "Element trouvé avec succes", userDAO.getById(id)));
        }
        return null;
        // TODO : Mettre exeception
    }

    public ResponseEntity<ServiceResponse> getByEmail(@Valid @RequestBody String email){
        if(userDAO.getByEmail(email).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ServiceResponse(HttpStatus.OK.value(), "Element trouvé avec succes", userDAO.getByEmail(email)));
        }
        return null;
        // TODO : Mettre exeception
    }

    public ResponseEntity<ServiceResponse> getByPseudo(@Valid @RequestBody String pseudo){
        if(userDAO.getByPseudo(pseudo).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ServiceResponse(HttpStatus.OK.value(), "Element trouvé avec succes", userDAO.getByPseudo(pseudo)));
        }
        // TODO : Mettre exeception
        return null;
    }
}

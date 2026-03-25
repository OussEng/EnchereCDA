package fr.eni.enchere.retrait.bll;

import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.retrait.dal.dao.IRetraitDAO;
import fr.eni.enchere.user.bo.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetraitService {

    private final IRetraitDAO retraitDAO;

    public RetraitService(IRetraitDAO retraitDAO) {
        this.retraitDAO = retraitDAO;
    }

    public List<Retrait> getRetraitsByUserId(Long id) {
        return retraitDAO.findByUserId(id);
    }

    public Optional<Retrait> getRetraitById(Long id) {
        return retraitDAO.findById(id);
    }

    public void deleteRetrait(Long id){
        retraitDAO.delete(id);
    }

    public void updateRetrait( Retrait retrait){
        retraitDAO.update( retrait);
    }

    public void createRetrait(Retrait retrait, User user){
        retraitDAO.save(retrait, user.getId());
    }
}

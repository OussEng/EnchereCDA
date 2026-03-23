package fr.eni.enchere.retrait.bll;

import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.retrait.dal.dao.IRetraitDAO;
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
}

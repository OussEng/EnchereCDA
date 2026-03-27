package fr.eni.enchere.enchere.bll;

import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.enchere.dal.dao.IEnchereDAO;
import org.springframework.stereotype.Service;

@Service
public class EnchereService {
    private final IEnchereDAO enchereDAO;

    public EnchereService(IEnchereDAO enchereDAO) {
        this.enchereDAO = enchereDAO;
    }


    public void create(Enchere enchere){

        enchereDAO.save(enchere);

    }



}

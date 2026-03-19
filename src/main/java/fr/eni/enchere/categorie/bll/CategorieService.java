package fr.eni.enchere.categorie.bll;

import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.categorie.dal.dao.ICategorieDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {


    private final ICategorieDAO categorieDAO;

    public CategorieService(ICategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }

    public List<Categorie> getAll(){
        return categorieDAO.findAll();
    }


}

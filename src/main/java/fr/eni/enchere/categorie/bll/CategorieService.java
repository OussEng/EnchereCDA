package fr.eni.enchere.categorie.bll;

import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.categorie.dal.dao.ICategorieDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {


    private final ICategorieDAO categorieDAO;

    public CategorieService(ICategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }

    public List<Categorie> getAll(){
        return categorieDAO.findAll();
    }

    public Optional<Categorie> getById(Long id) { return categorieDAO.findById(id); }

    public void save(Categorie categorie) {
        categorieDAO.save(categorie);
    }

    public void delete(Long id) {
        categorieDAO.delete(id);
    }


}

package fr.eni.enchere.categorie.dal.dao;

import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.categorie.dal.CategorieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Profile("mysql")
@Component
public class CategorieDAO implements ICategorieDAO{
    private final CategorieRepository categorieRepository;

    public CategorieDAO(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }


    @Override
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @Override
    public Optional<Categorie> findById(Long id) {
        return categorieRepository.findById(id);
    }

    @Override
    public void save(Categorie categorie) {
        categorieRepository.save(categorie);
    }

    @Override
    public void delete(Long id) {
        categorieRepository.delete(id);
    }
}

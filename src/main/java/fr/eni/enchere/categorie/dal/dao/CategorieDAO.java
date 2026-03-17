package fr.eni.enchere.categorie.dal.dao;

import fr.eni.enchere.categorie.dal.CategorieRepository;

public class CategorieDAO implements ICategorieDAO{
    private final CategorieRepository categorieRepository;

    public CategorieDAO(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }
}

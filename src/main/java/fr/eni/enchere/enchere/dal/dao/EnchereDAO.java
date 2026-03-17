package fr.eni.enchere.enchere.dal.dao;

import fr.eni.enchere.enchere.dal.EnchereRepository;

public class EnchereDAO implements IEnchereDAO{

    private final EnchereRepository enchereRepository;

    public EnchereDAO(EnchereRepository enchereRepository) {
        this.enchereRepository = enchereRepository;
    }
}

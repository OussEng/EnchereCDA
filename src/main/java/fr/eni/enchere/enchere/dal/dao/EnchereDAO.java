package fr.eni.enchere.enchere.dal.dao;

import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.enchere.dal.EnchereRepository;
import org.springframework.stereotype.Component;

@Component
public class EnchereDAO implements IEnchereDAO{

    private final EnchereRepository enchereRepository;

    public EnchereDAO(EnchereRepository enchereRepository) {
        this.enchereRepository = enchereRepository;
    }

    @Override
    public void save(Enchere enchere) {
        enchereRepository.save(enchere);
    }
}

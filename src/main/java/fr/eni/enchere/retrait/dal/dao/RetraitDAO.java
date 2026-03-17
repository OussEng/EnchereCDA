package fr.eni.enchere.retrait.dal.dao;

import fr.eni.enchere.retrait.dal.RetraitRepository;

public class RetraitDAO implements IRetraitDAO{

    private final RetraitRepository retraitRepository;

    public RetraitDAO(RetraitRepository retraitRepository) {
        this.retraitRepository = retraitRepository;
    }
}

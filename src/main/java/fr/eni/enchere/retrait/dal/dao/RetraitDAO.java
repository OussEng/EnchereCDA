package fr.eni.enchere.retrait.dal.dao;


import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.retrait.dal.RetraitRepository;

import java.util.List;
import java.util.Optional;

public class RetraitDAO implements IRetraitDAO {

    private final RetraitRepository retraitRepository;

    public RetraitDAO(RetraitRepository retraitRepository) {
        this.retraitRepository = retraitRepository;
    }

    @Override
    public List<Retrait> findByUserId(Long id) {
        return List.of();
    }

    @Override
    public Optional<Retrait> findById(Long id) {
        return Optional.empty();
    }
}

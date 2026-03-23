package fr.eni.enchere.retrait.dal.dao;

import fr.eni.enchere.retrait.bo.Retrait;

import java.util.List;
import java.util.Optional;

public interface IRetraitDAO {
    List<Retrait> findByUserId(Long id);
    Optional<Retrait> findById(Long id);
}

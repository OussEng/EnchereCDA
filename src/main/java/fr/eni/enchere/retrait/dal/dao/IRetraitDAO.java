package fr.eni.enchere.retrait.dal.dao;

import fr.eni.enchere.retrait.bo.Retrait;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface IRetraitDAO {

    void save(Retrait retrait, Long id);

    List<Retrait> findByUserId(Long id);
    Optional<Retrait> findById(Long id);
    void delete(Long id);
    void update(Retrait retrait);

}

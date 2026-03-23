package fr.eni.enchere.retrait.dal.dao;

import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.retrait.dal.RetraitRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Profile("mysql")
public class RetraitDAO implements IRetraitDAO{

    private final RetraitRepository retraitRepository;

    public RetraitDAO(RetraitRepository retraitRepository) {
        this.retraitRepository = retraitRepository;
    }

    @Override
    public void save(Retrait retrait, Long id) {
        retraitRepository.save(retrait, id);
    }

    @Override
    public List<Retrait> findByUserId(Long id) {
        return retraitRepository.findByUserId(id);
    }

    @Override
    public Optional<Retrait> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        retraitRepository.delete(id);
    }

    @Override
    public void update(Retrait retrait) {
        retraitRepository.update(retrait);
    }
}

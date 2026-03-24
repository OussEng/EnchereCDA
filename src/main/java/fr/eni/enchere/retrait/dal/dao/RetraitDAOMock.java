package fr.eni.enchere.retrait.dal.dao;

import fr.eni.enchere.retrait.bo.Retrait;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Simule une table de jointure USER <-> RETRAIT en mémoire.
 * La Map<Long, List<Retrait>> joue le rôle d'un index userId -> adresses,
 * comme une table de BDD "user_retrait" le ferait.
 *
 * Les données sont cohérentes avec ArticleDAOMock :
 *   - Alice  (id=1) : Paris (id=1) + Marseille (id=4)
 *   - Bob    (id=2) : Lyon  (id=2)
 *   - Charlie(id=3) : Nantes(id=3)
 */
@Profile("mock")
@Component
public class RetraitDAOMock implements IRetraitDAO {

    /** Table simulée : userId → liste de Retrait */
    private final Map<Long, List<Retrait>> retraitTable = new HashMap<>();

    public RetraitDAOMock() {

        // ─── Adresses de retrait ──────────────────────────────────────
        Retrait retrait1 = new Retrait();
        retrait1.setId(1L);
        retrait1.setRue("12 rue des Lilas");
        retrait1.setCodePostal("75011");
        retrait1.setVille("Paris");

        Retrait retrait2 = new Retrait();
        retrait2.setId(2L);
        retrait2.setRue("8 avenue Foch");
        retrait2.setCodePostal("69006");
        retrait2.setVille("Lyon");

        Retrait retrait3 = new Retrait();
        retrait3.setId(3L);
        retrait3.setRue("3 rue du Port");
        retrait3.setCodePostal("44000");
        retrait3.setVille("Nantes");

        Retrait retrait4 = new Retrait();
        retrait4.setId(4L);
        retrait4.setRue("5 impasse des Roses");
        retrait4.setCodePostal("13000");
        retrait4.setVille("Marseille");

        // ─── Association userId → adresses ───────────────────────────
        // Alice (id=1) : Paris + Marseille
        retraitTable.put(1L, new ArrayList<>(List.of(retrait1, retrait4)));

        // Bob (id=2) : Lyon
        retraitTable.put(2L, new ArrayList<>(List.of(retrait2)));

        // Charlie (id=3) : Nantes
        retraitTable.put(3L, new ArrayList<>(List.of(retrait3)));
    }

    @Override
    public void save(Retrait retrait, Long id) {

    }

    /**
     * Équivaut à : SELECT * FROM retrait WHERE user_id = :id
     */
    @Override
    public List<Retrait> findByUserId(Long id) {
        return new ArrayList<>(retraitTable.getOrDefault(id, Collections.emptyList()));
    }

    /**
     * Équivaut à : SELECT * FROM retrait WHERE id = :id
     */
    @Override
    public Optional<Retrait> findById(Long id) {
        return retraitTable.values().stream()
                .flatMap(List::stream)
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Retrait retrait) {

    }
}

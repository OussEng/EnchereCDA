package fr.eni.enchere.categorie.dal.dao;

import fr.eni.enchere.categorie.bo.Categorie;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Profile("mock")
@Component
public class CategorieDAOMock implements ICategorieDAO {

    @Override
    public List<Categorie> findAll() {
        Categorie informatique = new Categorie();
        informatique.setId(1L);
        informatique.setLibelle("Informatique");

        Categorie telephonie = new Categorie();
        telephonie.setId(2L);
        telephonie.setLibelle("Téléphonie");

        Categorie jeuxVideo = new Categorie();
        jeuxVideo.setId(3L);
        jeuxVideo.setLibelle("Jeux vidéo");

        Categorie vetements = new Categorie();
        vetements.setId(4L);
        vetements.setLibelle("Vêtements");

        Categorie chaussures = new Categorie();
        chaussures.setId(5L);
        chaussures.setLibelle("Chaussures");

        Categorie electromenager = new Categorie();
        electromenager.setId(6L);
        electromenager.setLibelle("Électroménager");

        Categorie mobilier = new Categorie();
        mobilier.setId(7L);
        mobilier.setLibelle("Mobilier");

        Categorie sport = new Categorie();
        sport.setId(8L);
        sport.setLibelle("Sport & Loisirs");

        Categorie livres = new Categorie();
        livres.setId(9L);
        livres.setLibelle("Livres & BD");

        Categorie musique = new Categorie();
        musique.setId(10L);
        musique.setLibelle("Musique & Instruments");

        Categorie voitures = new Categorie();
        voitures.setId(11L);
        voitures.setLibelle("Voitures & Motos");

        Categorie maison = new Categorie();
        maison.setId(12L);
        maison.setLibelle("Maison & Jardin");

        return List.of(
                informatique, telephonie, jeuxVideo, vetements,
                chaussures, electromenager, mobilier, sport,
                livres, musique, voitures, maison
        );
    }

    @Override
    public Optional<Categorie> findById(Long id) {
        return findAll().stream()
                .filter(categorie -> categorie.getId().equals(id))
                .findFirst();
    }

}

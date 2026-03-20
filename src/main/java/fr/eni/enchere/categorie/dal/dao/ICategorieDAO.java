package fr.eni.enchere.categorie.dal.dao;

import fr.eni.enchere.categorie.bo.Categorie;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ICategorieDAO {

    List<Categorie> findAll();


}

package fr.eni.enchere.categorie.dal.CategorieRowMapper;

import fr.eni.enchere.categorie.bo.Categorie;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class CategorieRowMapper implements RowMapper<Categorie> {


    @Nullable
    @Override
    public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {

        Categorie categorie = new Categorie();
        categorie.setId(rs.getLong("id"));
        categorie.setLibelle(rs.getString("libelle"));


        return categorie;
    }
}

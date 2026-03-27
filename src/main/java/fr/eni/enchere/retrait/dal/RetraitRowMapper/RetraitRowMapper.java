package fr.eni.enchere.retrait.dal.RetraitRowMapper;

import fr.eni.enchere.retrait.bo.Retrait;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RetraitRowMapper implements RowMapper<Retrait> {
    @Nullable
    @Override
    public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {

        Retrait retrait = new Retrait();

        retrait.setId(rs.getLong("retrait_id"));
        retrait.setVille(rs.getString("retraits_ville"));
        retrait.setRue(rs.getString("retrait_rue"));
        retrait.setCodePostal(rs.getString("retrait_code_postal"));

        return retrait;
    }
}

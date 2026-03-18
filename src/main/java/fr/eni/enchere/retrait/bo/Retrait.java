package fr.eni.enchere.retrait.bo;

import fr.eni.enchere.retrait.bo.enums.Type_Address;

public class Retrait {
    private Long id;
    private String rue;
    private String codePostal;
    private String ville;
    private Type_Address typeAddress;

    public Retrait() {
    }


    public Type_Address getTypeAddress() {
        return typeAddress;
    }

    public void setTypeAddress(Type_Address typeAddress) {
        this.typeAddress = typeAddress;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

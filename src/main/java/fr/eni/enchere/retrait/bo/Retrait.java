package fr.eni.enchere.retrait.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Retrait {
    private Long id;

    @NotBlank(message = "La rue ne peut pas etre null.")
    private String rue;

    @NotBlank(message = "Le code postal ne peut pas etre null.")
    @Size(max = 5, message = "Le code postal doit contenir 5 caractères" )
    private String codePostal;

    @NotBlank(message = "La ville ne peut pas etre null.")
    private String ville;

    public Retrait() {
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

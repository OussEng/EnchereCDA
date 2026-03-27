package fr.eni.enchere.retrait.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Retrait {
    private Long id;

    @NotBlank(message = "La rue est obligatoire.")
    private String rue;

    @NotBlank(message = "Le code postal est obligatoire.")
    @Size(min = 5, max = 5, message = "Le code postal doit contenir exactement 5 chiffres.")
    @Pattern(regexp = "\\d{5}", message = "Le code postal doit contenir uniquement des chiffres.")
    private String codePostal;

    @NotBlank(message = "La ville est obligatoire.")
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

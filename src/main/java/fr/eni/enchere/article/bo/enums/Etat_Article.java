package fr.eni.enchere.article.bo.enums;

public enum Etat_Article {

    CREEE("Créée"),
    EN_COURS("En cours"),
    TERMINEES("Terminées"),
    ANNULEE("Annulée"),
    EFFECTUE("Effectué");

    private final String label;


    Etat_Article(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getDbValue() { return this.name(); }
}

package fr.eni.enchere.article.dal.dao;

import fr.eni.enchere.article.bo.Article;
import fr.eni.enchere.article.bo.enums.Etat_Article;
import fr.eni.enchere.categorie.bo.Categorie;
import fr.eni.enchere.enchere.bo.Enchere;
import fr.eni.enchere.retrait.bo.Retrait;
import fr.eni.enchere.user.bo.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Profile("mock")
@Component
public class ArticleDAOMock implements IArticleDAO {

    private final List<Article> articles = new ArrayList<>();
    private final AtomicLong idSequence       = new AtomicLong(1);
    private final AtomicLong enchereIdSequence = new AtomicLong(1);

    public ArticleDAOMock() {

        // ─── Catégories ───────────────────────────────────────────────
        Categorie informatique = new Categorie();
        informatique.setId(1L);
        informatique.setLibelle("Informatique");

        Categorie sport = new Categorie();
        sport.setId(2L);
        sport.setLibelle("Sport");

        Categorie mobilier = new Categorie();
        mobilier.setId(3L);
        mobilier.setLibelle("Mobilier");

        // ─── Lieux de retrait ─────────────────────────────────────────
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

        // ─── Utilisateurs ─────────────────────────────────────────────
        User alice = new User();
        alice.setId(1L);
        alice.setPseudo("alice75");
        alice.setPrenom("Alice");
        alice.setNom("Martin");
        alice.setEmail("alice.martin@mail.com");
        alice.setTelephone("0612345678");
        alice.setCredit(200);
        // Alice possède 2 adresses de retrait
        alice.getAdresses().add(retrait1);
        alice.getAdresses().add(retrait4);

        User bob = new User();
        bob.setId(2L);
        bob.setPseudo("bob_l");
        bob.setPrenom("Bob");
        bob.setNom("Lemaire");
        bob.setEmail("bob.lemaire@mail.com");
        bob.setTelephone("0698765432");
        bob.setCredit(50);
        // Bob possède 1 adresse de retrait
        bob.getAdresses().add(retrait2);

        User charlie = new User();
        charlie.setId(3L);
        charlie.setPseudo("charlie_n");
        charlie.setPrenom("Charlie");
        charlie.setNom("Nguyen");
        charlie.setEmail("charlie.nguyen@mail.com");
        charlie.setTelephone("0755443322");
        charlie.setCredit(500);
        // Charlie possède 1 adresse de retrait
        charlie.getAdresses().add(retrait3);

        // ─── Articles ─────────────────────────────────────────────────
        // prixVente  = prix de BASE fixé par le vendeur (immuable)
        // miseAPrix  = prix courant des enchères (= prixVente par défaut,
        //              puis incrémenté à chaque nouvelle offre)

        Article a1 = new Article();
        a1.setNom("Vélo de route");
        a1.setDescription("Vélo carbone 21 vitesses, très bon état.");
        a1.setPrixVente(150);      // prix de base vendeur
        a1.setMiseAPrix(150);      // = prixVente tant qu'aucune enchère
        a1.setDateDebutEncheres(LocalDateTime.now().minusDays(1));
        a1.setDateFinEncheres(LocalDateTime.now().plusDays(5));
        a1.setEtatEnchere(Etat_Article.EN_COURS);
        a1.setCategorie(sport);
        a1.setVendeur(alice);
        a1.setLieuRetrait(retrait1);
        save(a1);

        Article a2 = new Article();
        a2.setNom("iPhone 13 Pro");
        a2.setDescription("Reconditionné grade A, batterie 95%.");
        a2.setPrixVente(400);      // prix de base vendeur
        a2.setMiseAPrix(400);      // aucune enchère → = prixVente
        a2.setDateDebutEncheres(LocalDateTime.now().plusDays(2));
        a2.setDateFinEncheres(LocalDateTime.now().plusDays(10));
        a2.setEtatEnchere(Etat_Article.CREEE);
        a2.setCategorie(informatique);
        a2.setVendeur(bob);
        a2.setLieuRetrait(retrait2);
        save(a2);

        Article a3 = new Article();
        a3.setNom("Table basse en bois");
        a3.setDescription("Table scandinave, dimensions 120x60cm.");
        a3.setPrixVente(50);       // prix de base vendeur
        a3.setMiseAPrix(50);       // sera mis à jour après les enchères
        a3.setDateDebutEncheres(LocalDateTime.now().minusDays(10));
        a3.setDateFinEncheres(LocalDateTime.now().minusDays(1));
        a3.setEtatEnchere(Etat_Article.TERMINEES);
        a3.setCategorie(mobilier);
        a3.setVendeur(charlie);
        a3.setAcheteur(alice);
        a3.setLieuRetrait(retrait3);
        save(a3);

        Article a4 = new Article();
        a4.setNom("Casque gaming Razer");
        a4.setDescription("Casque surround 7.1, micro rétractable.");
        a4.setPrixVente(60);       // prix de base vendeur
        a4.setMiseAPrix(60);       // sera mis à jour après les enchères
        a4.setDateDebutEncheres(LocalDateTime.now().minusHours(3));
        a4.setDateFinEncheres(LocalDateTime.now().plusDays(3));
        a4.setEtatEnchere(Etat_Article.EN_COURS);
        a4.setCategorie(informatique);
        a4.setVendeur(alice);
        a4.setLieuRetrait(retrait1);
        save(a4);

        Article a5 = new Article();
        a5.setNom("Tapis de yoga");
        a5.setDescription("Tapis antidérapant 6mm, livré avec sangle.");
        a5.setPrixVente(20);       // prix de base vendeur
        a5.setMiseAPrix(20);       // aucune enchère → = prixVente
        a5.setDateDebutEncheres(LocalDateTime.now().plusDays(1));
        a5.setDateFinEncheres(LocalDateTime.now().plusDays(8));
        a5.setEtatEnchere(Etat_Article.CREEE);
        a5.setCategorie(sport);
        a5.setVendeur(bob);
        a5.setLieuRetrait(retrait2);
        save(a5);

        // ─── Enchères simulées ────────────────────────────────────────
        // Chaque enchère soumet un nouveau montant total.
        // La dernière (la plus haute) devient la miseAPrix de l'article.

        // a1 "Vélo de route" (EN_COURS) — 3 enchères
        addEnchere(a1, bob,     160, LocalDateTime.now().minusHours(20));
        addEnchere(a1, charlie, 175, LocalDateTime.now().minusHours(10));
        addEnchere(a1, alice,   190, LocalDateTime.now().minusHours(2));
        a1.setMiseAPrix(190);  // meilleure offre en cours

        // a3 "Table basse en bois" (TERMINEES, acheteur = alice) — 3 enchères
        addEnchere(a3, bob,     60, LocalDateTime.now().minusDays(9));
        addEnchere(a3, charlie, 70, LocalDateTime.now().minusDays(7));
        addEnchere(a3, alice,   85, LocalDateTime.now().minusDays(5));
        a3.setMiseAPrix(85);   // prix final, alice a remporté

        // a4 "Casque gaming Razer" (EN_COURS) — 2 enchères
        addEnchere(a4, charlie, 70, LocalDateTime.now().minusHours(3));
        addEnchere(a4, bob,     85, LocalDateTime.now().minusHours(1));
        a4.setMiseAPrix(85);   // meilleure offre en cours
    }

    /**
     * Crée une enchère, lui assigne un ID et l'attache directement
     * à la liste interne de l'article (article.getEncheres()).
     */
    private void addEnchere(Article article, User encherit, int montant, LocalDateTime date) {
        Enchere enchere = new Enchere();
        enchere.setId(enchereIdSequence.getAndIncrement());
        enchere.setArticle(article);
        enchere.setEncherit(encherit);
        enchere.setMontant(montant);
        enchere.setDateEnchere(date);
        article.getEncheres().add(enchere);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articles.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Article> findByVendeurId(Long vendeurId) {
        return articles.stream()
                .filter(a -> a.getVendeur() != null && a.getVendeur().getId().equals(vendeurId))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Article article) {
        article.setId(idSequence.getAndIncrement());
        articles.add(article);
    }

    @Override
    public void update(Article article) {
        Article existant = findById(article.getId())
                .orElseThrow(() -> new NoSuchElementException("Article introuvable avec l'id : " + article.getId()));
        articles.remove(existant);
        articles.add(article);
    }

    @Override
    public void deleteById(Long id) {
        Article existant = findById(id)
                .orElseThrow(() -> new NoSuchElementException("Article introuvable avec l'id : " + id));
        articles.remove(existant);
    }

    @Override
    public List<Article> getByName(String v) {
        return List.of();
    }

    @Override
    public List<Article> findActive() {
        return List.of();
    }

    @Override
    public List<Article> findByUserId(Long id) {
        return List.of();
    }
}
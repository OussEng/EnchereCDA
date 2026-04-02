# EnchereCDA — Plateforme de ventes aux enchères en ligne

> **Projet scolaire — Formation CDA (Concepteur Développeur d'Applications) @ ENI**  
> Réalisé en **10 jours ouvrés** dans le cadre d'un sprint de 2 semaines.

---

## Contexte & calendrier

Ce projet a été développé lors d'un sprint encadré avec une deadline fixe. Les deux semaines étaient structurées comme suit :

| Semaine | Focus |
|---------|-------|
| **Semaine 1** | Mise en place du projet · Workflow Git · Conception (diagramme BDD, diagramme de classes, diagramme de cas d'utilisation, wireframes) |
| **Semaine 2** | Développement full-stack · Implémentation des fonctionnalités · QA manuelle |

Le calendrier serré a conduit à reporter délibérément certaines bonnes pratiques d'ingénierie. Elles sont documentées dans la section [Limites connues](#️-limites-connues--ce-que-nous-ferions-différemment) — non pas parce que nous en étions ignorants, mais faute de temps.

---

## Fonctionnalités

**EnchereCDA** est une plateforme de ventes aux enchères en ligne de type eBay, où les utilisateurs peuvent :

- **Mettre en vente des articles** avec un prix de départ, une catégorie, des dates et une adresse de retrait
- **Parcourir & rechercher** les annonces actives (accès public, sans connexion)
- **Enchérir** grâce à un système de crédits virtuels
- **Remporter des enchères** automatiquement à l'expiration de la date de fin
- **Gérer leur profil** — informations personnelles, adresses de retrait, solde de crédits, historique d'achats
- **Acheter des crédits** pour financer leurs enchères (paiement par carte simulé)

Un panneau d'administration (restreint à `SUPER_ADMIN`) offre une supervision complète des utilisateurs, articles et catégories.

---

## Stack technique

| Couche | Technologie |
|--------|-------------|
| Langage | Java 17 |
| Framework | Spring Boot 3.5.11 |
| Sécurité | Spring Security · BCrypt |
| Vue | Thymeleaf |
| Persistance | **Spring JDBC (JdbcTemplate) — pas d'ORM** |
| Base de données | MySQL |
| Validation | Jakarta Bean Validation |
| Planification | Spring `@Scheduled` |
| Build | Gradle |
| Tests | JUnit |

> **Pourquoi pas d'ORM ?**  
> Contrainte du cahier des charges. JdbcTemplate uniquement. Chaque requête, jointure et mapping de résultat est implémenté manuellement via des classes `RowMapper` personnalisées.

---

## Architecture

L'application suit une **structure en couches orientée domaine** :

```
Requête HTTP
    └─► Controller            (Spring MVC)
            └─► Service / BLL  (logique métier, transactions)
                    └─► DAO / DAL  (interface + implémentation)
                              └─► Repository  (requêtes JdbcTemplate brutes)
                                        └─► Base des données
```

Chaque domaine métier est autonome :

```
fr.eni.enchere/
│
├── article/                              # Domaine principal — structure complète présentée ici
│   ├── bo/
│   │   ├── Article.java                 # Entité domaine (champs validés)
│   │   └── enums/Etat_Article.java      # CREEE · EN_COURS · TERMINEES · EFFECTUE · ANNULEE
│   ├── bll/
│   │   ├── ArticleService.java          # Logique métier, orchestration des enchères
│   │   └── ArticleManager.java          # @Scheduled — transitions d'état automatiques + à la demande
│   ├── controller/
│   │   └── ArticleController.java       # GET|POST /encheres/**
│   └── dal/
│       ├── dao/
│       │   ├── IArticleDAO.java         # Interface (agnostique au profil)
│       │   ├── ArticleDAO.java          # @Profile("mysql") — délègue au repository
│       │   └── ArticleDAOMock.java      # @Profile("mock")  — stub en mémoire
│       ├── ArticleRepository.java       # Requêtes JdbcTemplate brutes + SQL multi-JOIN
│       └── ArticleRowMapper.java
│
├── enchere/    # Enregistrements des enchères  — même structure 4 couches
├── user/       # Comptes & crédits             — même structure 4 couches
├── retrait/    # Adresses de retrait           — même structure 4 couches
├── categorie/  # Catégories                    — même structure 4 couches
│
├── admin/controller/AdminController.java       # /admin/** — SUPER_ADMIN uniquement
├── security/   # SecurityConfig · AuthController · UserPrincipal · AuthenticatedUser
├── exeception/ # Exceptions métier · GlobalExceptionHandler (@ControllerAdvice)
└── controller/MainController.java              # Redirection racine → /encheres/
```

### Décisions de conception notables

**Bascule de DAO par profil Spring** — Chaque domaine dispose d'un `*DAO` (MySQL) et d'un `*DAOMock` (stub en mémoire) derrière une interface commune. La bascule se fait via les profils Spring (`mysql` / `mock`), ce qui s'est révélé utile en début de développement avant la mise en place de la base de données.

**Un seul appel BDD à la connexion** — Le `UserDetailsService` charge l'objet `User` complet une seule fois lors de l'authentification et le stocke dans un `UserPrincipal` personnalisé. Les contrôleurs accèdent à l'utilisateur connecté via un bean `AuthenticatedUser` qui lit le `SecurityContextHolder`, éliminant ainsi les appels BDD par requête.

**Cycle de vie des enchères — `ArticleManager`** — Les transitions d'état et l'attribution du gagnant sont gérées par un manager dédié qui s'exécute de deux façons complémentaires : un scheduler en arrière-plan (`@Scheduled(fixedRate = 30000)`) comme filet de sécurité, et un appel à la demande déclenché à chaque requête utilisateur pertinente pour garantir que l'état de l'enchère est toujours exact au moment où ça compte. L'implémentation vaut le coup d'œil :
1. Transition `CREEE → EN_COURS` à l'atteinte de la date de début
2. Transition `EN_COURS → TERMINEES` à l'expiration de la date de fin
3. Attribution automatique du gagnant et passage à l'état `EFFECTUE` — les deux étapes sont séquencées dans un seul appel, donc une enchère qui vient d'expirer est résolue immédiatement

**Enchères transactionnelles** — La pose d'une enchère est enveloppée dans `@Transactional` : les crédits du précédent meilleur enchérisseur sont remboursés atomiquement avant déduction sur le nouveau.

**Protection CSRF & mots de passe** — Spring Security gère la protection CSRF sur tous les formulaires. Les mots de passe sont hashés avec BCrypt via `PasswordEncoder`.

**Gestion centralisée des exceptions** — Un `@ControllerAdvice` global (`GlobalExceptionHandler`) intercepte les exceptions métier et les redirige vers des pages d'erreur adaptées, évitant toute fuite de stack trace vers l'utilisateur final.

---
## Diagramme use case

![usecase](https://github.com/user-attachments/assets/c11b3ea2-e9ea-4e64-a435-52fa5dcfa3be)

## Schéma de base de données

![db](https://github.com/user-attachments/assets/a6a7fc94-a08c-4b9e-8906-92dc0753b78e)

## Diagramme de classes
![class](https://github.com/user-attachments/assets/090f0a12-8eb4-47ad-81dc-df84ae8de264)

## Maquettage

*Exemples de maquettes :*

![desktop](https://github.com/user-attachments/assets/b2e6cf26-c691-455d-b360-9fba40e56978)

![mobile](https://github.com/user-attachments/assets/23cc70b4-1e56-4df0-baab-e4b5175832b9)



### Machine à états des articles

| État | Signification |
|------|---------------|
| `CREEE` | Créé, enchère pas encore ouverte |
| `EN_COURS` | Enchères ouvertes |
| `TERMINEES` | Date de fin dépassée, gagnant pas encore attribué |
| `EFFECTUE` | Vente conclue, acheteur attribué |
| `ANNULEE` | Annulé par le vendeur (uniquement si aucune enchère placée) |

---

## Rôles & contrôle d'accès

| Rôle | Accès |
|------|-------|
| `ROLE_USER` | Parcourir, enchérir, vendre, gérer son profil |
| `ROLE_ADMIN` | *(réservé, non entièrement implémenté)* |
| `ROLE_SUPER_ADMIN` | Tout ce qui précède + tableau de bord `/admin/**` |

---

## Démarrage

### Prérequis

- Java 17
- MySQL
- Gradle

### 1 — Créer la base de données

```sql
SOURCE "Script SQL Création la bdd Enchere.sql";
```

Le script crée le schéma et insère les données de test (catégories, utilisateurs, articles, enchères).

### 2 — Configurer la source de données

Éditer `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/enchere
spring.datasource.username=VOTRE_UTILISATEUR
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

### 3 — Lancer

```bash
./gradlew bootRun
```

Application disponible sur **http://localhost:8080**

### Comptes de test

> ⚠️ Données de développement uniquement — ne jamais utiliser ces identifiants dans un environnement réel.

Tous les comptes partagent le mot de passe **`password`**.

| Nom d'utilisateur | Rôle | Crédits |
|-------------------|------|---------|
| `jdupont` | USER | 500 |
| `mmartin` | USER | 1 200 |
| `pleroy` | USER | 300 |
| `sberard` | USER | 800 |
| `tmoreau` | USER | 150 |
| `admin` | SUPER_ADMIN | 150 |

---

## ⚠️ Limites connues & ce que nous ferions différemment

Cette section existe par souci de transparence. Il ne s'agit pas de choses que nous avons négligées — ce sont des compromis assumés sous une deadline de 10 jours. Dans un projet réel, chacun de ces points serait traité avant une mise en production.

### Pas de couche DTO
Les contrôleurs reçoivent et retournent directement les objets du domaine (`User`, `Article`). Cela couple la vue au modèle de persistance et expose des champs internes. La solution est une **couche DTO / mapper** (ex. MapStruct) pour découpler la surface d'API du domaine.

### Pas de logging structuré
Il n'y a pas de logging structuré. Des `System.out.println` apparaissent à quelques endroits comme outil de debug rapide (ex. dans `GlobalExceptionHandler`). En production, cela devrait être remplacé par **SLF4J + Logback/Log4j2**, avec des niveaux de log, du request tracing et de l'agrégation (ELK, Loki, etc.).

### Pas de tests automatisés
En dehors d'un `ArticleTest.java` squelette, il n'y a aucun test unitaire ou d'intégration. Une suite de tests complète inclurait :
- Tests unitaires de la logique métier de la couche service (enchères, transitions d'état) avec **Mockito**
- Tests d'intégration des repositories avec **@JdbcTest** ou **Testcontainers** (MySQL)
- Tests de la couche sécurité avec **Spring Security Test**
- Tests end-to-end avec **Selenium / Playwright**

### Pas de pipeline CI/CD
Aucun pipeline automatisé. Dans un contexte professionnel, ce serait un workflow **GitHub Actions** (ou GitLab CI) couvrant : build → tests → analyse statique (SonarQube) → build image Docker → déploiement.

### Pas de conteneurisation
L'application n'a ni `Dockerfile` ni `docker-compose.yml`. Un fichier compose avec l'application + un service MySQL rendrait l'installation locale en une seule commande et éliminerait les dérives d'environnement.

### Pas de documentation API
Pas de spec Swagger/OpenAPI. Étant donné que c'est une application Thymeleaf rendue côté serveur, c'est moins critique, mais tout endpoint REST devrait être documenté.

---

## Structure du projet (référence rapide)

```
src/
├── main/
│   ├── java/fr/eni/enchere/   # Tous les sources Java (voir Architecture ci-dessus)
│   └── resources/
│       ├── application.properties
│       ├── static/            # JS (auth, profil, paiement crédits)
│       └── templates/         # Templates Thymeleaf (layout, fragments, pages)
└── test/
    └── java/fr/eni/enchere/
        └── ArticleTest.java   # Classe de test squelette
```

-- Script complet de création de la base ENCHERE
-- MySQL

DROP TABLE IF EXISTS encheres;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS retraits;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS utilisateurs;

-- Table utilisateurs
CREATE TABLE utilisateurs (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              pseudo VARCHAR(50) UNIQUE NOT NULL,
                              nom VARCHAR(100) NOT NULL,
                              prenom VARCHAR(100) NOT NULL,
                              email VARCHAR(100) UNIQUE NOT NULL,
                              telephone VARCHAR(20),
                              mot_de_passe VARCHAR(255) NOT NULL,
                              credit INT NOT NULL DEFAULT 0,
                              actif BOOLEAN NOT NULL DEFAULT 1,
                              role ENUM('ROLE_USER','ROLE_ADMIN','ROLE_SUPER_ADMIN') NOT NULL DEFAULT 'ROLE_USER'
);

-- Table categories
CREATE TABLE categories (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            libelle VARCHAR(100) UNIQUE NOT NULL
);

-- Table retraits
CREATE TABLE retraits (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          utilisateur_id BIGINT NOT NULL,
                          rue VARCHAR(255) NOT NULL,
                          code_postal VARCHAR(10) NOT NULL,
                          ville VARCHAR(100) NOT NULL
);

-- Table articles
CREATE TABLE articles (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          nom_article VARCHAR(255) NOT NULL,
                          description TEXT,
                          date_debut_encheres DATETIME NOT NULL,
                          date_fin_encheres DATETIME NOT NULL,
                          mise_a_prix INT NOT NULL,
                          prix_vente INT,
                          etat_vente ENUM('CREEE','ANNULEE','EN_COURS','TERMINEES','EFFECTUE') NOT NULL DEFAULT 'CREEE',
                          vendeur_id BIGINT NOT NULL,
                          acheteur_id BIGINT,
                          categorie_id BIGINT NOT NULL,
                          lieu_retrait_id BIGINT NOT NULL
);

-- Table encheres
CREATE TABLE encheres (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          date_enchere DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          montant INT NOT NULL,
                          article_id BIGINT NOT NULL,
                          utilisateur_id BIGINT NOT NULL
);

-- Clés étrangères
ALTER TABLE retraits ADD FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id);
ALTER TABLE articles ADD FOREIGN KEY (vendeur_id) REFERENCES utilisateurs(id);
ALTER TABLE articles ADD FOREIGN KEY (acheteur_id) REFERENCES utilisateurs(id);
ALTER TABLE articles ADD FOREIGN KEY (categorie_id) REFERENCES categories(id);
ALTER TABLE articles ADD FOREIGN KEY (lieu_retrait_id) REFERENCES retraits(id);
ALTER TABLE encheres ADD FOREIGN KEY (article_id) REFERENCES articles(id);
ALTER TABLE encheres ADD FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id);

-- Données initiales : catégories
INSERT INTO categories (libelle) VALUES
                                     ('Informatique'),
                                     ('Téléphonie & Tablettes'),
                                     ('Électroménager'),
                                     ('Jeux vidéo & Consoles'),
                                     ('Vêtements & Chaussures'),
                                     ('Maison & Décoration'),
                                     ('Sports & Loisirs'),
                                     ('Livres & Musique'),
                                     ('Automobile & Moto'),
                                     ('Bijoux & Montres');

-- Utilisateurs
INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, mot_de_passe, credit) VALUES
                                                                                           ('jdupont', 'Dupont', 'Jean', 'jean.dupont@email.com', '0612345678', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 500),
                                                                                           ('mmartin', 'Martin', 'Marie', 'marie.martin@email.com', '0623456789', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1200),
                                                                                           ('pleroy', 'Leroy', 'Pierre', 'pierre.leroy@email.com', '0634567890', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 300),
                                                                                           ('sberard', 'Berard', 'Sophie', 'sophie.berard@email.com', '0645678901', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 800),
                                                                                           ('tmoreau', 'Moreau', 'Thomas', 'thomas.moreau@email.com', '0656789012', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 150);

-- Retraits
INSERT INTO retraits (utilisateur_id, rue, code_postal, ville) VALUES
                                                                   (1, '12 Rue de la Paix', '75001', 'Paris'),
                                                                   (2, '45 Avenue des Fleurs', '69002', 'Lyon'),
                                                                   (3, '8 Boulevard Gambetta', '13001', 'Marseille'),
                                                                   (4, '22 Rue du Commerce', '31000', 'Toulouse'),
                                                                   (5, '3 Impasse des Lilas', '33000', 'Bordeaux');

-- Articles (TERMINEES, EFFECTUE, EN_COURS, CREEE)
INSERT INTO articles (nom_article, description, date_debut_encheres, date_fin_encheres, mise_a_prix, prix_vente, etat_vente, vendeur_id, acheteur_id, categorie_id, lieu_retrait_id) VALUES
-- TERMINEES
('MacBook Pro 2021 M1', 'Très bon état, batterie 92%, chargeur inclus.', '2025-03-01 10:00:00', '2025-03-08 10:00:00', 800, 1150, 'TERMINEES', 1, 2, 1, 1),
('iPhone 13 128Go Noir', 'Débloqué tout opérateur, boîte d\'origine.', '2025-03-05 09:00:00', '2025-03-12 09:00:00', 300, 420, 'TERMINEES', 3, 4, 2, 3),
('PS5 avec 2 manettes', 'Console en parfait état, 3 jeux inclus.', '2025-03-10 14:00:00', '2025-03-17 14:00:00', 400, 580, 'TERMINEES', 2, 1, 4, 2),

-- EFFECTUE (chaque utilisateur gagne un article)
('Table de ping-pong Pro', 'Table pliable, état neuf.', '2025-03-20 10:00:00', '2025-03-27 10:00:00', 150, 300, 'EFFECTUE', 2, 1, 7, 2),
('Casque audio Bose QC45', 'Annulation active noise cancelling, état excellent.', '2025-03-21 09:00:00', '2025-03-28 09:00:00', 200, 350, 'EFFECTUE', 3, 2, 1, 3),
('Sac à main Louis Vuitton', 'Modèle Neverfull GM, état impeccable.', '2025-03-22 14:00:00', '2025-03-29 14:00:00', 900, 1300, 'EFFECTUE', 4, 3, 5, 4),
('VTT Specialized', 'Taille L, suspension complète, très bon état.', '2025-03-23 11:00:00', '2025-03-30 11:00:00', 500, 750, 'EFFECTUE', 5, 4, 7, 5),
('Montre Apple Watch Series 9', 'Neuve, bracelet sport inclus.', '2025-03-24 08:00:00', '2025-03-31 08:00:00', 300, 450, 'EFFECTUE', 1, 5, 10, 1),

-- EN_COURS
('Vélo de route Trek Émonda', 'Cadre carbone, taille M, kilométrage faible.', '2025-03-15 08:00:00', '2026-04-15 08:00:00', 600, NULL, 'EN_COURS', 4, NULL, 6, 4),
('Montre Seiko Automatique', 'Mouvement mécanique, bracelet cuir marron.', '2025-03-16 12:00:00', '2026-04-10 12:00:00', 150, NULL, 'EN_COURS', 5, NULL, 10, 5),
('Lave-linge Samsung 8kg', 'Moins de 2 ans, programme vapeur, très bon état.', '2025-03-17 10:00:00', '2026-04-05 10:00:00', 180, NULL, 'EN_COURS', 1, NULL, 3, 1),
('Drone DJI Mini 4', 'Neuf, caméra 4K, batterie longue durée.', '2026-03-25 09:00:00', '2026-04-20 09:00:00', 400, NULL, 'EN_COURS', 2, NULL, 1, 2),
('Guitare électrique Fender Stratocaster', 'Très bon état, amplificateur inclus.', '2026-03-26 10:00:00', '2026-04-22 10:00:00', 500, NULL, 'EN_COURS', 3, NULL, 8, 3),

-- CREEE
('Canon EOS R50 + objectif 18-45mm', 'Appareil nu + kit objectif, moins de 500 déclenchements.', '2026-04-01 10:00:00', '2026-04-14 10:00:00', 500, NULL, 'CREEE', 2, NULL, 1, 2),
('Canapé 3 places tissu gris', 'Acheté il y a 1 an, très bon état, retrait uniquement.', '2026-04-05 09:00:00', '2026-04-19 09:00:00', 200, NULL, 'CREEE', 3, NULL, 6, 3),
('Chaussures Nike Air Max', 'Taille 42, modèle 2025, neuves.', '2026-04-01 09:00:00', '2026-04-15 09:00:00', 100, NULL, 'CREEE', 4, NULL, 5, 4);

-- Enchères (TERMINEES et EFFECTUE)
INSERT INTO encheres (date_enchere, montant, article_id, utilisateur_id) VALUES
-- TERMINEES
('2025-03-02 11:00:00', 850, 1, 2),
('2025-03-04 14:30:00', 950, 1, 4),
('2025-03-06 09:15:00', 1050, 1, 2),
('2025-03-07 18:00:00', 1150, 1, 2),
('2025-03-06 10:00:00', 320, 2, 1),
('2025-03-08 16:45:00', 370, 2, 4),
('2025-03-10 11:20:00', 420, 2, 4),
('2025-03-11 09:00:00', 420, 3, 1),
('2025-03-13 20:00:00', 500, 3, 5),
('2025-03-15 17:30:00', 580, 3, 1),
-- EFFECTUE
('2025-03-21 12:00:00', 160, 7, 3),
('2025-03-22 15:00:00', 220, 7, 1),
('2025-03-23 16:30:00', 300, 7, 1),
('2025-03-22 10:00:00', 220, 8, 4),
('2025-03-24 09:30:00', 350, 8, 2),
('2025-03-23 15:00:00', 1000, 9, 5),
('2025-03-25 11:00:00', 1300, 9, 3),
('2025-03-24 12:00:00', 600, 10, 1),
('2025-03-26 14:00:00', 750, 10, 4),
('2025-03-25 09:00:00', 350, 11, 2),
('2025-03-26 08:30:00', 450, 11, 5),
-- EN_COURS
('2025-03-16 09:00:00', 620, 4, 1),
('2025-03-17 11:00:00', 680, 4, 3),
('2025-03-17 13:00:00', 5, 5, 2),
('2025-03-18 08:30:00', 195, 5, 1),
('2025-03-18 10:30:00', 190, 6, 4),
('2026-03-25 11:00:00', 420, 12, 1),
('2026-03-25 15:30:00', 450, 12, 5),
('2026-03-26 12:00:00', 520, 13, 2),
('2026-03-26 16:00:00', 580, 13, 4);
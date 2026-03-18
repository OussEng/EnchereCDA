-- Script de création de la base de données ENCHERE
--   type :      MySQL
--


DROP TABLE utilisateurs;
DROP TABLE categories;
DROP TABLE retraits;
DROP TABLE articles;
DROP TABLE encheres;



CREATE TABLE `utilisateurs` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `pseudo` varchar(50) UNIQUE NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `email` varchar(100) UNIQUE NOT NULL,
  `telephone` varchar(20),
  `mot_de_passe` varchar(255) NOT NULL,
  `credit` int NOT NULL DEFAULT 0
);

CREATE TABLE `categories` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `libelle` varchar(100) UNIQUE NOT NULL
);

CREATE TABLE `retraits` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `utilisateur_id` bigint NOT NULL,
  `rue` varchar(255) NOT NULL,
  `code_postal` varchar(10) NOT NULL,
  `ville` varchar(100) NOT NULL
);

CREATE TABLE `articles` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `nom_article` varchar(255) NOT NULL,
  `description` text,
  `date_debut_encheres` datetime NOT NULL,
  `date_fin_encheres` datetime NOT NULL,
  `mise_a_prix` int NOT NULL,
  `prix_vente` int,
  `etat_vente` enum('CREEE','ANNULEE','EN_COURS','TERMINEES','EFFECTUE') NOT NULL DEFAULT 'CREEE',
  `vendeur_id` bigint NOT NULL,
  `acheteur_id` bigint,
  `categorie_id` bigint NOT NULL,
  `lieu_retrait_id` bigint NOT NULL
);

CREATE TABLE `encheres` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `date_enchere` datetime NOT NULL DEFAULT (CURRENT_TIMESTAMP),
  `montant` int NOT NULL,
  `article_id` bigint NOT NULL,
  `utilisateur_id` bigint NOT NULL
);

ALTER TABLE `retraits` ADD FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateurs` (`id`);

ALTER TABLE `articles` ADD FOREIGN KEY (`vendeur_id`) REFERENCES `utilisateurs` (`id`);

ALTER TABLE `articles` ADD FOREIGN KEY (`acheteur_id`) REFERENCES `utilisateurs` (`id`);

ALTER TABLE `articles` ADD FOREIGN KEY (`categorie_id`) REFERENCES `categories` (`id`);

ALTER TABLE `articles` ADD FOREIGN KEY (`lieu_retrait_id`) REFERENCES `retraits` (`id`);

ALTER TABLE `encheres` ADD FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`);

ALTER TABLE `encheres` ADD FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateurs` (`id`);




-- Creétion des Categories --

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




-- Utilisateurs (mot_de_passe = "password" bcrypt hash)
INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, mot_de_passe, credit) VALUES
('jdupont',   'Dupont',   'Jean',    'jean.dupont@email.com',    '0612345678', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 500),
('mmartin',   'Martin',   'Marie',   'marie.martin@email.com',   '0623456789', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 1200),
('pleroy',    'Leroy',    'Pierre',  'pierre.leroy@email.com',   '0634567890', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 300),
('sberard',   'Berard',   'Sophie',  'sophie.berard@email.com',  '0645678901', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 800),
('tmoreau',   'Moreau',   'Thomas',  'thomas.moreau@email.com',  '0656789012', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 150);

-- Retraits (adresses de retrait liées aux vendeurs)
INSERT INTO retraits (utilisateur_id, rue, code_postal, ville) VALUES
(1,  '12 Rue de la Paix',       '75001', 'Paris'),
(2,  '45 Avenue des Fleurs',     '69002', 'Lyon'),
(3, '8 Boulevard Gambetta',     '13001', 'Marseille'),
(4,  '22 Rue du Commerce',       '31000', 'Toulouse'),
(5, '3 Impasse des Lilas',      '33000', 'Bordeaux');

-- Articles
-- Terminée avec acheteur (etat=TERMINEES, prix_vente rempli)
-- En cours (etat=EN_COURS, dates actives)
-- Créée (etat=CREEE, pas encore commencée)

INSERT INTO articles (nom_article, description, date_debut_encheres, date_fin_encheres, mise_a_prix, prix_vente, etat_vente, vendeur_id, acheteur_id, categorie_id, lieu_retrait_id) VALUES

-- TERMINEES
('MacBook Pro 2021 M1',
 'Très bon état, batterie 92%, chargeur inclus.',
 '2025-03-01 10:00:00', '2025-03-08 10:00:00',
 800, 1150, 'TERMINEES', 1, 2, 1, 1),

('iPhone 13 128Go Noir',
 'Débloqué tout opérateur, boîte d\'origine.',
 '2025-03-05 09:00:00', '2025-03-12 09:00:00',
 300, 420, 'TERMINEES', 3, 4, 2, 3),

('PS5 avec 2 manettes',
 'Console en parfait état, 3 jeux inclus.',
 '2025-03-10 14:00:00', '2025-03-17 14:00:00',
 400, 580, 'TERMINEES', 2, 1, 4, 2),

-- EN_COURS
('Vélo de route Trek Émonda',
 'Cadre carbone, taille M, kilométrage faible.',
 '2025-03-15 08:00:00', '2026-04-15 08:00:00',
 600, NULL, 'EN_COURS', 4, NULL, 6, 4),

('Montre Seiko Automatique',
 'Mouvement mécanique, bracelet cuir marron.',
 '2025-03-16 12:00:00', '2026-04-10 12:00:00',
 150, NULL, 'EN_COURS', 5, NULL, 10, 5),

('Lave-linge Samsung 8kg',
 'Moins de 2 ans, programme vapeur, très bon état.',
 '2025-03-17 10:00:00', '2026-04-05 10:00:00',
 180, NULL, 'EN_COURS', 1, NULL, 3, 1),

-- CREEE (pas encore commencée)
('Canon EOS R50 + objectif 18-45mm',
 'Appareil nu + kit objectif, moins de 500 déclenchements.',
 '2026-04-01 10:00:00', '2026-04-14 10:00:00',
 500, NULL, 'CREEE', 2, NULL, 1, 2),

('Canapé 3 places tissu gris',
 'Acheté il y a 1 an, très bon état, retrait uniquement.',
 '2026-04-05 09:00:00', '2026-04-19 09:00:00',
 200, NULL, 'CREEE', 3, NULL, 6, 3);

-- Encheres (uniquement sur les articles EN_COURS ou TERMINEES)
-- Articles TERMINEES : 1 (MacBook), 2 (iPhone), 3 (PS5)
-- Articles EN_COURS  : 4 (Vélo), 5 (Montre), 6 (Lave-linge)

INSERT INTO encheres (date_enchere, montant, article_id, utilisateur_id) VALUES

-- MacBook (article 1, gagné par utilisateur 2 à 1150)
('2025-03-02 11:00:00', 850,  1, 2),
('2025-03-04 14:30:00', 950,  1, 4),
('2025-03-06 09:15:00', 1050, 1, 2),
('2025-03-07 18:00:00', 1150, 1, 2),

-- iPhone (article 2, gagné par utilisateur 4 à 420)
('2025-03-06 10:00:00', 320,  2, 1),
('2025-03-08 16:45:00', 370,  2, 4),
('2025-03-10 11:20:00', 420,  2, 4),

-- PS5 (article 3, gagné par utilisateur 1 à 580)
('2025-03-11 09:00:00', 420,  3, 1),
('2025-03-13 20:00:00', 500,  3, 5),
('2025-03-15 17:30:00', 580,  3, 1),

-- Vélo (article 4, EN_COURS)
('2025-03-16 09:00:00', 620,  4, 1),
('2025-03-17 11:00:00', 680,  4, 3),

-- Montre (article 5, EN_COURS)
('2025-03-17 13:00:00', 160,  5, 2),
('2025-03-18 08:30:00', 195,  5, 1),

-- Lave-linge (article 6, EN_COURS)
('2025-03-18 10:30:00', 190,  6, 4);
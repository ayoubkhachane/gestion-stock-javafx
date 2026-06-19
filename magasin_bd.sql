-- ============================================
-- Base de données : magasin_bd
-- Auteur : Khachane Ayoub
-- Date : 19/06/2026
-- ============================================

CREATE DATABASE IF NOT EXISTS magasin_bd;
USE magasin_bd;

-- ============================================
-- Table des catégories
-- ============================================
CREATE TABLE IF NOT EXISTS categories (
    id_cat INT PRIMARY KEY AUTO_INCREMENT,
    nom_cat VARCHAR(50) NOT NULL
);

-- ============================================
-- Table des produits
-- ============================================
CREATE TABLE IF NOT EXISTS produits (
    id_prod INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prix DOUBLE NOT NULL,
    quantite INT NOT NULL,
    seuil_min INT NOT NULL DEFAULT 10,
    id_cat INT,
    FOREIGN KEY (id_cat) REFERENCES categories(id_cat)
);

-- ============================================
-- Données de test
-- ============================================
INSERT INTO categories (nom_cat) VALUES 
('Électronique'), 
('Alimentation'), 
('Vêtements');

INSERT INTO produits (nom, prix, quantite, seuil_min, id_cat) VALUES
('Clavier', 150.0, 25, 10, 1),
('Souris', 80.0, 8, 10, 1),
('Riz', 30.0, 100, 20, 2),
('T-shirt', 120.0, 15, 10, 3);
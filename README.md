# 📦 Gestion de Stock - Magasin

Application JavaFX de gestion de stock avec base de données MySQL.

## 🎯 Fonctionnalités

- ✅ CRUD complet des produits
- ✅ CRUD complet des catégories
- ✅ Recherche par mot-clé
- ✅ Alertes stock bas
- ✅ Dashboard avec statistiques et camembert
- ✅ Export PDF
- ✅ Architecture MVC + DAO

## 🛠️ Technologies

- Java 17+
- JavaFX 25
- MySQL / JDBC
- iText (PDF)

## 📁 Structure
src/
├── app/           → Point d'entrée
├── controller/    → Logique métier
├── dao/           → Accès données (SQL)
├── model/         → Classes métier
└── view/          → Interface FXML


## 🚀 Lancement

1. Importer `magasin_bd.sql` dans MySQL
2. Configurer `model/SingletonConnection.java` (user/password)
3. Lancer `app/MainApp.java` avec VM arguments JavaFX

## 👤 Auteur

Khachane Ayoub
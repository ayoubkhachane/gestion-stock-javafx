package app;

import dao.IProduitDAO;
import dao.ProduitDAOImpl;
import model.Produit;

/**
 * Test console pour vérifier la connexion JDBC
 * et l'affichage des produits depuis MySQL.
 * 
 * @author Khachane Ayoub
 * @version 1.0
 */
public class TestConsole {
    
    /**
     * Point d'entrée du test console.
     * Affiche tous les produits et détecte les stocks bas.
     * 
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        
        IProduitDAO dao = new ProduitDAOImpl();
        
        System.out.println("=== TEST CONNEXION JDBC ===");
        System.out.println("Produits en stock:\n");
        
        for (Produit p : dao.getAll()) {
            System.out.println(
                p.getIdProd() + " | " + 
                p.getNom() + " | " + 
                p.getPrix() + " DH | Qté: " + 
                p.getQuantite() + " | Cat: " + 
                p.getNomCat() +
                (p.isStockBas() ? " [⚠️ STOCK BAS]" : " [OK]")
            );
        }
        
        System.out.println("\n=== RECHERCHE 'Riz' ===");
        for (Produit p : dao.search("Riz")) {
            System.out.println(p.getNom() + " trouvé!");
        }
    }
}
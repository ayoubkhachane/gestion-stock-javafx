package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Produit;
import dao.IProduitDAO;
import dao.ProduitDAOImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contrôleur du tableau de bord (Dashboard).
 * Affiche les statistiques et un camembert des catégories.
 * 
 * @author Khachane Ayoub
 * @version 1.0
 */
public class DashboardController {

    @FXML private Label lblTotalProduits;
    @FXML private Label lblStockBas;
    @FXML private Label lblValeurStock;
    @FXML private PieChart pieChart;

    private IProduitDAO produitDAO = new ProduitDAOImpl();

    /**
     * Initialise le dashboard avec les données actuelles.
     */
    @FXML
    public void initialize() {
        List<Produit> produits = produitDAO.getAll();

        // 1. Nombre total de produits
        int total = produits.size();
        lblTotalProduits.setText(String.valueOf(total));

        // 2. Produits en stock bas
        long stockBas = produits.stream().filter(Produit::isStockBas).count();
        lblStockBas.setText(String.valueOf(stockBas));

        // 3. Valeur totale du stock (prix * quantité)
        double valeurTotale = produits.stream()
            .mapToDouble(p -> p.getPrix() * p.getQuantite())
            .sum();
        lblValeurStock.setText(String.format("%.0f", valeurTotale));

        // 4. Camembert : répartition par catégorie
        Map<String, Long> parCategorie = produits.stream()
            .collect(Collectors.groupingBy(Produit::getNomCat, Collectors.counting()));

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Long> entry : parCategorie.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChart.setData(pieData);
    }

    /**
     * Ferme la fenêtre du dashboard.
     */
    @FXML
    public void fermer() {
        Stage stage = (Stage) lblTotalProduits.getScene().getWindow();
        stage.close();
    }
}
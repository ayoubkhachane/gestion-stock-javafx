package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.Categorie;
import model.Produit;
import dao.ICategorieDAO;
import dao.IProduitDAO;
import dao.CategorieDAOImpl;
import dao.ProduitDAOImpl;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur principal de l'interface JavaFX.
 * Gère les interactions utilisateur et la liaison avec la couche métier.
 * 
 * @author Khachane Ayoub
 * @version 1.0
 */
public class MainController implements Initializable {

    @FXML private TextField txtNom, txtPrix, txtQuantite, txtSeuil, txtRecherche;
    @FXML private ComboBox<Categorie> comboCat;
    @FXML private TableView<Produit> tableProduits;
    @FXML private TableColumn<Produit, Integer> colId, colQte, colSeuil;
    @FXML private TableColumn<Produit, String> colNom, colCat, colStatut;
    @FXML private TableColumn<Produit, Double> colPrix;
    @FXML private Label lblMessage;
    @FXML private HBox alertBox;
    @FXML private Label alertLabel;

    private IProduitDAO produitDAO = new ProduitDAOImpl();
    private ICategorieDAO categorieDAO = new CategorieDAOImpl();
    private ObservableList<Produit> data = FXCollections.observableArrayList();

    /**
     * Initialise le contrôleur après le chargement du FXML.
     * Configure les colonnes du TableView et charge les données initiales.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Configuration des colonnes du TableView
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(
            c.getValue().getIdProd()).asObject());
        colNom.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
            c.getValue().getNom()));
        colPrix.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(
            c.getValue().getPrix()).asObject());
        colQte.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(
            c.getValue().getQuantite()).asObject());
        colSeuil.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(
            c.getValue().getSeuilMin()).asObject());
        colCat.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
            c.getValue().getNomCat()));
        colStatut.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
            c.getValue().isStockBas() ? "STOCK BAS" : "OK"));

        // Chargement des catégories dans le ComboBox
        comboCat.getItems().addAll(categorieDAO.getAll());
        
        // Listener : quand on clique sur un produit, remplir le formulaire
        tableProduits.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    txtNom.setText(newSelection.getNom());
                    txtPrix.setText(String.valueOf(newSelection.getPrix()));
                    txtQuantite.setText(String.valueOf(newSelection.getQuantite()));
                    txtSeuil.setText(String.valueOf(newSelection.getSeuilMin()));
                    
                    // Sélectionner la bonne catégorie dans le ComboBox
                    for (Categorie cat : comboCat.getItems()) {
                        if (cat.getIdCat() == newSelection.getIdCat()) {
                            comboCat.getSelectionModel().select(cat);
                            break;
                        }
                    }
                }
            }
        );
        
        // Chargement initial des produits
        rafraichir();
    }

    /**
     * Ajoute un nouveau produit à la base de données.
     */
    @FXML
    public void ajouter() {
        try {
            Produit p = new Produit();
            p.setNom(txtNom.getText());
            p.setPrix(Double.parseDouble(txtPrix.getText()));
            p.setQuantite(Integer.parseInt(txtQuantite.getText()));
            p.setSeuilMin(Integer.parseInt(txtSeuil.getText()));
            p.setIdCat(comboCat.getValue().getIdCat());

            produitDAO.add(p);
            lblMessage.setText("Produit ajouté avec succès !");
            lblMessage.setTextFill(Color.GREEN);
            vider();
            rafraichir();
        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            lblMessage.setTextFill(Color.RED);
        }
    }

    /**
     * Modifie le produit sélectionné dans le TableView.
     */
    @FXML
    public void modifier() {
        try {
            Produit p = tableProduits.getSelectionModel().getSelectedItem();
            if (p == null) {
                lblMessage.setText("Veuillez sélectionner un produit.");
                return;
            }
            p.setNom(txtNom.getText());
            p.setPrix(Double.parseDouble(txtPrix.getText()));
            p.setQuantite(Integer.parseInt(txtQuantite.getText()));
            p.setSeuilMin(Integer.parseInt(txtSeuil.getText()));
            p.setIdCat(comboCat.getValue().getIdCat());

            produitDAO.update(p);
            lblMessage.setText("Produit modifié !");
            lblMessage.setTextFill(Color.GREEN);
            rafraichir();
        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            lblMessage.setTextFill(Color.RED);
        }
    }

    /**
     * Supprime le produit sélectionné après confirmation.
     */
    @FXML
    public void supprimer() {
        Produit p = tableProduits.getSelectionModel().getSelectedItem();
        if (p == null) {
            lblMessage.setText("Veuillez sélectionner un produit.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
            "Supprimer " + p.getNom() + " ?");
        if (alert.showAndWait().get().getButtonData().isDefaultButton()) {
            produitDAO.delete(p.getIdProd());
            lblMessage.setText("Produit supprimé !");
            lblMessage.setTextFill(Color.GREEN);
            vider();
            rafraichir();
        }
    }

    /**
     * Recherche les produits par mot-clé.
     */
    @FXML
    public void rechercher() {
        data.clear();
        data.addAll(produitDAO.search(txtRecherche.getText()));
        tableProduits.setItems(data);
        lblMessage.setText(data.size() + " produit(s) trouvé(s).");
    }

    /**
     * Rafraîchit le TableView avec tous les produits et vérifie les alertes stock bas.
     */
    @FXML
    public void rafraichir() {
        data.clear();
        data.addAll(produitDAO.getAll());
        tableProduits.setItems(data);
        
        // Vérification des alertes stock bas
        boolean alerte = data.stream().anyMatch(Produit::isStockBas);
        alertBox.setVisible(alerte);
        if (alerte) {
            long count = data.stream().filter(Produit::isStockBas).count();
            alertLabel.setText("ALERTE: " + count + " produit(s) en stock bas !");
        }
    }

    /**
     * Vide les champs du formulaire.
     */
    @FXML
    public void vider() {
        txtNom.clear();
        txtPrix.clear();
        txtQuantite.clear();
        txtSeuil.clear();
        comboCat.getSelectionModel().clearSelection();
    }

    /**
     * Ouvre la fenêtre du tableau de bord (Dashboard).
     */
    @FXML
    public void ouvrirDashboard() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/view/DashboardView.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load(), 600, 500);
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Tableau de Bord");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            lblMessage.setTextFill(Color.RED);
        }
    }

    /**
     * Exporte la liste des produits dans un fichier PDF.
     */
    @FXML
    public void exporterPDF() {
        try {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Enregistrer le PDF");
            fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf")
            );
            fileChooser.setInitialFileName("Stock_Magasin_" + 
                java.time.LocalDate.now().toString() + ".pdf");
            
            java.io.File file = fileChooser.showSaveDialog(tableProduits.getScene().getWindow());
            
            if (file != null) {
                ExportPDF.export(file.getAbsolutePath());
                lblMessage.setText("PDF exporté avec succès !");
                lblMessage.setTextFill(Color.GREEN);
            }
        } catch (Exception e) {
            lblMessage.setText("Erreur export PDF: " + e.getMessage());
            lblMessage.setTextFill(Color.RED);
        }
    }
    /**
     * Ouvre la fenêtre de gestion des catégories.
     */
    @FXML
    public void ouvrirCategories() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/view/CategoriesView.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load(), 500, 400);
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Gestion des Catégories");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            lblMessage.setText("Erreur: " + e.getMessage());
            lblMessage.setTextFill(Color.RED);
        }
    }
}
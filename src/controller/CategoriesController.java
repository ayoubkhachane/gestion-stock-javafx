package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.Categorie;
import dao.ICategorieDAO;
import dao.CategorieDAOImpl;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur pour la gestion des catégories (CRUD).
 * 
 * @author Khachane Ayoub
 * @version 1.0
 */
public class CategoriesController implements Initializable {

    @FXML private TextField txtNomCat;
    @FXML private TableView<Categorie> tableCategories;
    @FXML private TableColumn<Categorie, Integer> colIdCat;
    @FXML private TableColumn<Categorie, String> colNomCat;
    @FXML private Label lblMessageCat;

    private ICategorieDAO categorieDAO = new CategorieDAOImpl();
    private ObservableList<Categorie> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colIdCat.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(
            c.getValue().getIdCat()).asObject());
        colNomCat.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
            c.getValue().getNomCat()));

        // Listener sélection
        tableCategories.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, newSel) -> {
                if (newSel != null) {
                    txtNomCat.setText(newSel.getNomCat());
                }
            }
        );

        rafraichir();
    }

    @FXML
    public void ajouterCategorie() {
        try {
            Categorie c = new Categorie();
            c.setNomCat(txtNomCat.getText());
            categorieDAO.add(c);
            lblMessageCat.setText("Catégorie ajoutée !");
            lblMessageCat.setTextFill(Color.GREEN);
            txtNomCat.clear();
            rafraichir();
        } catch (Exception e) {
            lblMessageCat.setText("Erreur: " + e.getMessage());
            lblMessageCat.setTextFill(Color.RED);
        }
    }

    @FXML
    public void modifierCategorie() {
        try {
            Categorie c = tableCategories.getSelectionModel().getSelectedItem();
            if (c == null) {
                lblMessageCat.setText("Sélectionnez une catégorie.");
                return;
            }
            c.setNomCat(txtNomCat.getText());
            categorieDAO.update(c);
            lblMessageCat.setText("Catégorie modifiée !");
            lblMessageCat.setTextFill(Color.GREEN);
            rafraichir();
        } catch (Exception e) {
            lblMessageCat.setText("Erreur: " + e.getMessage());
            lblMessageCat.setTextFill(Color.RED);
        }
    }

    @FXML
    public void supprimerCategorie() {
        Categorie c = tableCategories.getSelectionModel().getSelectedItem();
        if (c == null) {
            lblMessageCat.setText("Sélectionnez une catégorie.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
            "Supprimer " + c.getNomCat() + " ?");
        if (alert.showAndWait().get().getButtonData().isDefaultButton()) {
            categorieDAO.delete(c.getIdCat());
            lblMessageCat.setText("Catégorie supprimée !");
            lblMessageCat.setTextFill(Color.GREEN);
            txtNomCat.clear();
            rafraichir();
        }
    }

    private void rafraichir() {
        data.clear();
        data.addAll(categorieDAO.getAll());
        tableCategories.setItems(data);
    }
}
package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe principale de l'application JavaFX.
 * Charge le fichier FXML et affiche la fenêtre principale.
 * 
 * @author Khachane Ayoub
 * @version 1.0
 */
public class MainApp extends Application {

    /**
     * Point d'entrée de l'application JavaFX.
     * Charge MainView.fxml et configure la scène.
     * 
     * @param primaryStage la fenêtre principale
     * @throws Exception si le chargement FXML échoue
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // CHANGEMENT ICI : "/view/MainView.fxml" au lieu de "MainView.fxml"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
        BorderPane root = loader.load();
        
        Scene scene = new Scene(root, 900, 700);
        
        primaryStage.setTitle("Gestion de Stock - Magasin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Méthode main - lance l'application.
     * 
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}
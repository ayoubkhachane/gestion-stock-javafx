package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.Produit;
import dao.IProduitDAO;
import dao.ProduitDAOImpl;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Classe utilitaire pour l'export PDF de la liste des produits.
 * 
 * @author Khachane Ayoub
 * @version 1.0
 */
public class ExportPDF {
    
    /**
     * Génère un fichier PDF contenant la liste des produits.
     * 
     * @param filePath chemin où sauvegarder le PDF
     */
    public static void export(String filePath) {
        try {
            IProduitDAO dao = new ProduitDAOImpl();
            List<Produit> produits = dao.getAll();
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            
            // Titre
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("Gestion de Stock - Magasin", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            // Date
            Font dateFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            Paragraph datePara = new Paragraph("Exporté le : " + date, dateFont);
            datePara.setAlignment(Element.ALIGN_CENTER);
            document.add(datePara);
            
            document.add(Chunk.NEWLINE);
            
            // Tableau
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            
            // En-têtes
            String[] headers = {"ID", "Nom", "Prix (DH)", "Quantité", "Seuil", "Catégorie"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
                cell.setBackgroundColor(BaseColor.DARK_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }
            
            // Données
            for (Produit p : produits) {
                table.addCell(String.valueOf(p.getIdProd()));
                table.addCell(p.getNom());
                table.addCell(String.valueOf(p.getPrix()));
                table.addCell(String.valueOf(p.getQuantite()));
                table.addCell(String.valueOf(p.getSeuilMin()));
                table.addCell(p.getNomCat());
            }
            
            document.add(table);
            document.add(Chunk.NEWLINE);
            
            // Statistiques
            long stockBas = produits.stream().filter(Produit::isStockBas).count();
            double valeurTotale = produits.stream()
                .mapToDouble(p -> p.getPrix() * p.getQuantite())
                .sum();
            
            Font statsFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
            document.add(new Paragraph("Total produits : " + produits.size(), statsFont));
            document.add(new Paragraph("Produits en stock bas : " + stockBas, statsFont));
            document.add(new Paragraph("Valeur totale du stock : " + String.format("%.2f", valeurTotale) + " DH", statsFont));
            
            document.close();
            
            System.out.println("PDF exporté avec succès : " + filePath);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'export PDF : " + e.getMessage());
        }
    }
}
package dao;

import model.Produit;
import model.SingletonConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des produits.
 * 
 * @author Khachane Ayoub
 * @version 1.0
 */
public class ProduitDAOImpl implements IProduitDAO {
    
    @Override
    public List<Produit> getAll() {
        List<Produit> list = new ArrayList<>();
        try {
            Connection c = SingletonConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(
                "SELECT p.*, c.nom_cat FROM produits p " +
                "JOIN categories c ON p.id_cat = c.id_cat"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produit p = new Produit();
                p.setIdProd(rs.getInt("id_prod"));
                p.setNom(rs.getString("nom"));
                p.setPrix(rs.getDouble("prix"));
                p.setQuantite(rs.getInt("quantite"));
                p.setSeuilMin(rs.getInt("seuil_min"));
                p.setIdCat(rs.getInt("id_cat"));
                p.setNomCat(rs.getString("nom_cat"));
                list.add(p);
            }
            rs.close(); ps.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    @Override
    public List<Produit> search(String motCle) {
        List<Produit> list = new ArrayList<>();
        try {
            Connection c = SingletonConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(
                "SELECT p.*, c.nom_cat FROM produits p " +
                "JOIN categories c ON p.id_cat = c.id_cat " +
                "WHERE p.nom LIKE ?"
            );
            ps.setString(1, "%" + motCle + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produit p = new Produit();
                p.setIdProd(rs.getInt("id_prod"));
                p.setNom(rs.getString("nom"));
                p.setPrix(rs.getDouble("prix"));
                p.setQuantite(rs.getInt("quantite"));
                p.setSeuilMin(rs.getInt("seuil_min"));
                p.setIdCat(rs.getInt("id_cat"));
                p.setNomCat(rs.getString("nom_cat"));
                list.add(p);
            }
            rs.close(); ps.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    @Override
    public void add(Produit p) {
        try {
            Connection c = SingletonConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO produits (nom, prix, quantite, seuil_min, id_cat) " +
                "VALUES (?, ?, ?, ?, ?)"
            );
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPrix());
            ps.setInt(3, p.getQuantite());
            ps.setInt(4, p.getSeuilMin());
            ps.setInt(5, p.getIdCat());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    @Override
    public void update(Produit p) {
        try {
            Connection c = SingletonConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(
                "UPDATE produits SET nom=?, prix=?, quantite=?, seuil_min=?, id_cat=? " +
                "WHERE id_prod=?"
            );
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPrix());
            ps.setInt(3, p.getQuantite());
            ps.setInt(4, p.getSeuilMin());
            ps.setInt(5, p.getIdCat());
            ps.setInt(6, p.getIdProd());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    @Override
    public void delete(int id) {
        try {
            Connection c = SingletonConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(
                "DELETE FROM produits WHERE id_prod=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
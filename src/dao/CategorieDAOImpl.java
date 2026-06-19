package dao;

import model.Categorie;
import model.SingletonConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des catégories.
 * 
 * @author Khachane Ayoub
 * @version 1.0
 */
public class CategorieDAOImpl implements ICategorieDAO {
    
    @Override
    public List<Categorie> getAll() {
        List<Categorie> list = new ArrayList<>();
        try {
            Connection c = SingletonConnection.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM categories");
            while (rs.next()) {
                list.add(new Categorie(
                    rs.getInt("id_cat"),
                    rs.getString("nom_cat")
                ));
            }
            rs.close(); st.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    @Override
    public void add(Categorie c) {
        try {
            Connection co = SingletonConnection.getConnection();
            PreparedStatement ps = co.prepareStatement(
                "INSERT INTO categories (nom_cat) VALUES (?)"
            );
            ps.setString(1, c.getNomCat());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    @Override
    public void update(Categorie c) {
        try {
            Connection co = SingletonConnection.getConnection();
            PreparedStatement ps = co.prepareStatement(
                "UPDATE categories SET nom_cat=? WHERE id_cat=?"
            );
            ps.setString(1, c.getNomCat());
            ps.setInt(2, c.getIdCat());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    @Override
    public void delete(int id) {
        try {
            Connection co = SingletonConnection.getConnection();
            PreparedStatement ps = co.prepareStatement(
                "DELETE FROM categories WHERE id_cat=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
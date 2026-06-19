package dao;

import java.util.List;

import model.Produit;

public interface IProduitDAO {
    List<Produit> getAll();
    List<Produit> search(String motCle);
    void add(Produit p);
    void update(Produit p);
    void delete(int id);
}
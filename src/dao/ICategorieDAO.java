package dao;

import java.util.List;

import model.Categorie;

public interface ICategorieDAO {
    List<Categorie> getAll();
    void add(Categorie c);
    void update(Categorie c);
    void delete(int id);
}
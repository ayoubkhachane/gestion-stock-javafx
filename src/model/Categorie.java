package model;

public class Categorie {
    private int idCat;
    private String nomCat;
    
    public Categorie() {}
    
    public Categorie(int idCat, String nomCat) {
        this.idCat = idCat;
        this.nomCat = nomCat;
    }
    
    public int getIdCat() { return idCat; }
    public void setIdCat(int idCat) { this.idCat = idCat; }
    
    public String getNomCat() { return nomCat; }
    public void setNomCat(String nomCat) { this.nomCat = nomCat; }
    
    @Override
    public String toString() {
        return nomCat;
    }
}
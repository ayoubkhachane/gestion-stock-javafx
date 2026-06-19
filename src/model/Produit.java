package model;

public class Produit {
    private int idProd;
    private String nom;
    private double prix;
    private int quantite;
    private int seuilMin;
    private int idCat;
    private String nomCat; // pour afficher le nom de la catégorie dans le tableau
    
    public Produit() {}
    
    // Getters et Setters
    public int getIdProd() { return idProd; }
    public void setIdProd(int idProd) { this.idProd = idProd; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    
    public int getSeuilMin() { return seuilMin; }
    public void setSeuilMin(int seuilMin) { this.seuilMin = seuilMin; }
    
    public int getIdCat() { return idCat; }
    public void setIdCat(int idCat) { this.idCat = idCat; }
    
    public String getNomCat() { return nomCat; }
    public void setNomCat(String nomCat) { this.nomCat = nomCat; }
    
    // Méthode métier : détecter si le stock est bas
    public boolean isStockBas() {
        return quantite < seuilMin;
    }
}
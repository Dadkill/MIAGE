/*
 * © Groupe MiageMan - L3I MIAGE Aix-Marseille Tous droits réservés
 */
package Models;

import Models.enigmes.Enigme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/**
 * Cette classe représente une zone du jeu
 * @author theobredoux
 */
public class Zone {
    /**
     * Nom de la zone
     */
    private String nom;
    /**
     * Texture de la zone
     */
    private String texture;
    /**
     * Etat bloqué ou non de la zone
     */
    private Boolean estBloquee;
    /**
     * Sorties possibles de la zone
     */
    private HashMap<Sortie, Zone> sorties;
    /**
     * Parchemins associés à la zone
     */
    private ArrayList<Parchemin> parchemins;
    /**
     * Enigmes associées à la zone
     */
    private ArrayList<Enigme> enigmes;

    /**
     * Constructeur d'une Zone
     * Permet de créer une Zone à partir de son nom et de sa texture
     * @param nom Nom de la zone
     * @param texture Texture de la zone
     */
    public Zone(String nom, String texture) {
        this.nom = nom;
        this.texture = texture;
        this.estBloquee = false;
        this.enigmes = new ArrayList<Enigme>();
        this.sorties = new HashMap<Sortie, Zone>();
        this.parchemins = new ArrayList<Parchemin>();
    }
    
    /**
     * Permet d'ajouter une énigme à la zone
     * @param enigme Enigme à ajouter
     */
    public void addEnigme(Enigme enigme){
        this.enigmes.add(enigme);
    }
    
    /**
     * Permet d'obtenir une énigme de la zone
     * @return Dernière Enigme à réaliser | null si aucune
     */
    public Enigme getEnigme(){
        if(this.hasEnigmes()){
            return this.enigmes.get(0);
        }
        return null;
        
    }
    
    /**
     * Permet de supprimer une énigme
     * @param enigme Enigme à supprimer
     */
    public void removeEnigme(Enigme enigme){
        this.enigmes.remove(enigme);
    }
    
    /**
     * Permet de définir l'Etat bloqué de l'Enigme
     * @param etat Etat à définir, true pour bloquée, false sinon
     */
    public void setEstBloquee(Boolean etat){
        this.estBloquee = etat;
    }
    
    /**
     * Permet d'ajouter une sortie à la zone
     * Lorsque l'utilisateur veut naviguer dans la carte, il change de zone en fonction
     * de positions NORD, SUD, EST ou OUEST
     * @param zone Zone de destination
     * @param sortie Sortie choisie
     */
    public void ajouterSortie(Zone zone, Sortie sortie){
        this.sorties.put(sortie, zone);
    }

    /**
     * Permet d'obtenir le nom de la Zone
     * @return Nom de la Zone
     */
    public String getNom() {
        return nom;
    }

    /**
     * Permet d'obtenir la texture de la Zone
     * @return Texture de la zone
     */
    public String getTexture() {
        return texture;
    }

    /**
     * Permet d'obtenir l'etat bloqué ou non de la Zone
     * @return Etat Bloqué ou non, true pour bloquée, false sinon
     */
    public Boolean getEstBloquee() {
        return estBloquee;
    }
   
    /**
     * Permet d'obtenir une sortie à partir de sa position
     * @param sortie Position NORD, SUD, EST ou OUEST
     * @return Zone de sortie
     * @throws SortieBloqueeException En cas de sortie bloquée par une Enigme
     */
    public Zone obtenirSortie(Sortie sortie) throws SortieBloqueeException {
        if(!this.sorties.containsKey(sortie)){
            throw new SortieBloqueeException("Cette sortie n'existe pas");
        }
        if(this.sorties.get(sortie).getEstBloquee()){
            throw new SortieBloqueeException("Cette sortie est bloquée");
        }
        return this.sorties.get(sortie);
    }
    
    /**
     * Permet d'ajouter un parchemin à la zone
     * Des parchemins sont cachés dans les zones, il est nécessaire de tous les obtenir
     * @param unParchemin Parchemin à ajouter à la zone
     */
    public void ajouterParchemin(Parchemin unParchemin){
        this.parchemins.add(unParchemin);
    }
    
    /**
     * Permet d'obtenir les parchemins de la zone
     * @return Liste des parchemins de la zone
     */
    public ArrayList<Parchemin> getParchemins(){
        return this.parchemins;
    }

    /**
     * Méthode de Hashage
     * @return Hash de l'Objet
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.nom);
        return hash;
    }

    /**
     * Méthode de vérification d'égalité d'une Zone
     * @param obj Autre Zone à tester
     * @return Etat equals ou non
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Zone other = (Zone) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Zone{" + "nom=" + nom + ", texture=" + texture + ", estBloquee=" + estBloquee + '}';
    }
    
    /**
     * Permet de savoir si une zone a des énigmes
     * @return 
     */
    public Boolean hasEnigmes(){
        return this.enigmes.size() !=0;
    }
    
    /**
     * Permet d'obtenir les différentes sorties possibles de la Zone
     * @return 
     */
    public Set<Sortie> getSortiesPossibles(){
        return this.sorties.keySet();
    }
    
    
}

/*
 * © Groupe MiageMan - L3I MIAGE Aix-Marseille Tous droits réservés
 */
package Models.enigmes;

import Models.Zone;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Méthode représentant l'énigme des couleurs
 * @author theobredoux
 */
public class EnigmeCouleur extends Enigme{
    /**
     * Résultat des saisies de l'utilisateur
     */
    private HashMap<Couleur, String> resultat;
    
    /**
     * Permet d'associer une demande du joueur puis d'appeler la méthode de {@link #valider() validation}
     * @param couleur Couleur choisie
     * @param valeur Valeur hexadécimale saisie
     */
    public void associer(Couleur couleur, String valeur){
        resultat.put(couleur, valeur);
        this.valider();
    }
    
    /**
     * Permet de valider la saisie de l'utilisateur
     * Cette méthode vérifie que toutes les couleurs ont été saisies. 
     * Dans le cas d'une erreur, cette méthode appelle {@link #reinitialiser() la réinitialisation}
     */
    private void valider(){
        if(this.resultat.size() == Couleur.values().length){
            for(Couleur couleur : resultat.keySet()){
                if(!couleur.hexaCode().equals(resultat.get(couleur))){
                    this.reinitialiser();
                    return;
                }
            }
            this.resoudre();
        }
    }
    
    /**
     * Méthode de réinitialisation de l'Enigme
     */
    private void reinitialiser(){
        this.resultat = new HashMap();
    }

    /**
     * Constructeur de l'Enigme Couleur
     * @param message Message de l'Enigme
     * @param zoneABloquer Zone choisie 
     */
    public EnigmeCouleur(String message, Zone zoneABloquer) {
        super(message, zoneABloquer);
        this.resultat = new HashMap<Couleur, String>();
    }
    
    /**
     * Permet d'obtenir le nombre de résultats de couleurs;
     * @return 
     */
    public int getNbResultats(){
        return this.resultat.size();
    }
    
    
}

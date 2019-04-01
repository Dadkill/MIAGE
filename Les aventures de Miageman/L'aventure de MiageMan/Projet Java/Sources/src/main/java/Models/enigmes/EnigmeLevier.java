/*
 * © Groupe MiageMan - L3I MIAGE Aix-Marseille Tous droits réservés
 */
package Models.enigmes;

import Models.Zone;
import java.util.ArrayList;

/**
 * Enigme des leviers
 * @author theobredoux
 */
public class EnigmeLevier extends Enigme {
    /**
     * Solution de l'Enigme
     */
    private ArrayList<Integer> solution;
    /**
     * Résultat de la saisie de l'utilisateur
     */
    private ArrayList<Integer> resultat;
    /**
     * Nombre d'actions effectuées par l'utilisateur
     */
    private int nbActions;
    
    /**
     * Permet d'enregistrer la saisie lors de l'activation d'un levier
     * @param position Position du levier
     */
    public void activer(int position){
        resultat.add(position); 
        nbActions++;
        this.valider();
    }
    
    /**
     * Permet de valider un levier
     */
    private void valider(){
        if(nbActions== solution.size()){
            for(int i=0; i<resultat.size();i++){
                if(!(resultat.get(i).equals(solution.get(i)))){
                    this.reinitialiser();
                    return;
                }
            }
            this.resoudre();
        }
    }
    
    /**
     * Permet d'obtenir le nombre de leviers
     * @return Nombre de leviers
     */
    public int nbPossibilites(){
        return this.solution.size();
    }
    
    /**
     * Permet de réinitialiser un levier
     */
    private void reinitialiser(){
        this.nbActions= 0;
        this.resultat = new ArrayList<Integer>();
        
    }

    /**
     * Permet d'obtenir le nombre d'actions réalisées
     * @return Nombre d'actions
     */
    public int getNbActions() {
        return nbActions;
    }

    /**
     * Constructeur de l'Enigme des Leviers
     * @param solution Solution de l'ordre d'activation des leviers
     * @param message Message de l'enigme
     * @param zoneABloquer Zone à bloquer
     */
    public EnigmeLevier(ArrayList<Integer> solution, String message, Zone zoneABloquer) {
        super(message, zoneABloquer);
        this.solution = solution;
        this.nbActions = 0;
        this.resultat = new ArrayList<Integer>();
        
    }
}

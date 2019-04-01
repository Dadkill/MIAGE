/*
 * © Groupe MiageMan - L3I MIAGE Aix-Marseille Tous droits réservés
 */
package Models.enigmes;

import Models.Zone;
import java.util.ArrayList;

/**
 * Enigmes textuelles d'Olivettom
 * @author theobredoux
 */
public class EnigmeOlivettom extends Enigme{
    /**
     * Solution de l'Enigme
     */
    private String solution;

    /**
     * Constructeur de l'énigme d'Olivettom
     * @param solution Solution de l'énigme
     * @param message Message de l'énigme
     * @param zoneABloquer Zone à bloquer ou valider
     */
    public EnigmeOlivettom(String solution, String message, Zone zoneABloquer) {
        super(message, zoneABloquer);
        this.solution = solution;
    }
    
    /**
     * Permet de tester une réponse
     * @param reponse Réponse entrée par l'utilisateur
     */
    public void tester(String reponse){
        if(reponse.toLowerCase().equals(solution.toLowerCase())){
            this.resoudre();
        }
        return;
        
    }
    
}

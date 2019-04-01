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
public class EnigmeDilemme extends Enigme{
    /**
     * Propositions de l'Enigme
     */
    private ArrayList<String> propositions;
    /**
     * Solution de l'Enigme
     */
    private String solution;
    
    private Zone zonePerdante;

    /**
     * Constructeur d'une EnigmeDilemme
     * @param propositions Propositions ddu dilemme
     * @param solution Solution du dilemme
     * @param message Message de l'énigme
     * @param zoneGagnante Zone en cas de bonne réponse
     * @param zonePerdante Zone en cas de mauvaise réponse
     */
    public EnigmeDilemme(ArrayList<String> props, String solution, String message, Zone zoneGagnante, Zone zonePerdante) throws ReponseNonProposeeException {
        super(message, zoneGagnante);
        this.propositions = props;
        this.solution = solution;
        this.zonePerdante = zonePerdante;
        zonePerdante.setEstBloquee(true);
        if(this.propositions == null ||(this.propositions.size()==0)){
            throw new ReponseNonProposeeException();
        }
    }
    
    /**
     * Permet de tester une réponse
     * @param reponse Réponse entrée par l'utilisateur
     * @throws ReponseNonProposeeException Dans le cas où la réponse n'est pas dans la liste des propositions
     */
    public void tester(String reponse) throws ReponseNonProposeeException{
        if(this.getMinPropositions().contains(reponse.toLowerCase())){
            if(reponse.toLowerCase().equals(solution.toLowerCase())){
                this.resoudre();
            }
            else{
                this.zonePerdante.setEstBloquee(false);
            }
            return;
        }
        throw new ReponseNonProposeeException();
    }

    /**
     * Permet d'obtenir les différentes propositions
     * @return Propositions
     */
    public ArrayList<String> getPropositions() {
        return propositions;
    }
    
    private ArrayList<String> getMinPropositions(){
        ArrayList<String> props = new ArrayList<String>();
        for(int i = 0 ; i<propositions.size();i++){
            props.add(propositions.get(i).toLowerCase());
        }
        return props;
    }
    
    
    
}

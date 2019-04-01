/*
 * © Groupe MiageMan - L3I MIAGE Aix-Marseille Tous droits réservés
 */
package Models.enigmes;

/**
 * Cette énumération représente les couleurs de l'énigme des couleurs ainsi que leurs valeurs possibles
 * @author theobredoux
 */
public enum Couleur {
    /**
     * Couleur Bleu
     */
    BLEU("#0000FF"), 
    /**
     * Couleur Rouge
     */
    ROUGE("#FF0000"), 
    /**
     * Couleur Noire
     */
    NOIR("#FFFFFF"), 
    /**
     * Couleur blanche
     */
    BLANC("#000000");
    
    private String hexaCode;
    Couleur(String hexaCode){
        this.hexaCode = hexaCode;
    }
    /**
     * Permet d'obtenir le code hexadecimal d'une couleur
     * @return Code Hexadecimal
     */
    public String hexaCode(){
        return this.hexaCode;
    }
}

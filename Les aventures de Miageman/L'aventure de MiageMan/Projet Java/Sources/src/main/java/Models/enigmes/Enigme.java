/*
 * © Groupe MiageMan - L3I MIAGE Aix-Marseille Tous droits réservés
 */
package Models.enigmes;

import Models.Zone;
import java.util.Objects;

/**
 * Cette classe représente une énigme
 * @author theobredoux
 */
public abstract class Enigme {
    /**
     * Message associé à l'énigme
     */
    private String message;
    /**
     * Etat est résolu ou non de l'énigme
     */
    private Boolean estResolue;
    /**
     * Zone associée à l'Enigme
     */
    private Zone zoneBloquee;
    
    /**
     * Permet de connaitre l'etat résolu ou non de l'Enigme
     * @return Etat de l'enigme
     */
    public Boolean resolue(){
        return this.estResolue;
    }

    /**
     * Permet d'obtenir le message de l'énigme
     * @return Message de l'énigme
     */
    public String getMessage() {
        return message;
    }
    
    

    /**
     * Constructeur d'une Enigme
     * @param message Message de l'Enigme
     * @param zoneABloquer Zone à bloquer
     */
    public Enigme(String message, Zone zoneABloquer) {
        this.message = message;
        this.estResolue = false;
        this.zoneBloquee = zoneABloquer;
        this.zoneBloquee.setEstBloquee(true);
    }
    
    /**
     * Permet de résoudre l'enigme
     */
    protected void resoudre(){
        this.estResolue = true;
        this.zoneBloquee.setEstBloquee(false);
    }

    /**
     * Méthode de Hashage de l'énigme
     * @return Hash de l'énigme
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.message);
        hash = 41 * hash + Objects.hashCode(this.zoneBloquee);
        return hash;
    }

    /**
     * Permet de comparer une énigme à une autre
     * @param obj Enigme à comparer
     * @return Etat égal ou non de l'énigme
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
        final Enigme other = (Enigme) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.zoneBloquee, other.zoneBloquee)) {
            return false;
        }
        return true;
    } 

    @Override
    public String toString() {
        return "Enigme{" + "message=" + message + ", estResolue=" + estResolue + ", zoneBloquee=" + zoneBloquee + '}';
    }
    
    
}

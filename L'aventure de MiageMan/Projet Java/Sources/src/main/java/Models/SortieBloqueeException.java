/*
 * © Groupe MiageMan - L3I MIAGE Aix-Marseille Tous droits réservés
 */
package Models;

/**
 * Cette exception apparait lorsque l'on essaye d'accéder à une Zone qui est bloquée par une enigme
 * @author theobredoux
 */
public class SortieBloqueeException extends Exception {

    /**
     * Constructeur d'une exception SortieBloquee
     * @param message Message de l'erreur
     */
    SortieBloqueeException(String message) {
        super(message);
    }

    /**
     * Constructeur d'une exception SortieBloquee
     */
    public SortieBloqueeException() {
    }
}

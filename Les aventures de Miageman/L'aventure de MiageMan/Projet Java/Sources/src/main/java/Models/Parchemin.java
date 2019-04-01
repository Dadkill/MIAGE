/*
 * © Groupe MiageMan - L3I MIAGE Aix-Marseille Tous droits réservés
 */
package Models;

import java.util.Objects;

/**
 * Cette classe représente le Parchemin
 * @author theobredoux
 */
public class Parchemin {
    /**
     * Nom du parchemin
     */
    private String nom;
    /**
     * Description du parchemin
     */
    private String description;
    /**
     * Texture associée au parchemin
     */
    private String texture;

    /**
     * Constructeur d'un parchemin
     * @param nom Nom du parchemin
     * @param description Description du parchemin
     * @param texture Texture du parchemin
     */
    public Parchemin(String nom, String description, String texture) {
        this(nom);
        this.description = description;
        this.texture = texture;
    }

    /**
     * Constructeur d'un parchemin à partir de son nom
     * @param nom Nom du parchemin
     */
    public Parchemin(String nom) {
        this.nom = nom;
    }
    /**
     * Permet d'obtenir le nom du parchemin
     * @return Nom du parchemin
     */
    public String getNom() {
        return nom;
    }

    /**
     * Permet d'obtenir la description du parchemin
     * @return Description du parchemin
     */
    public String getDescription() {
        return description;
    }

    /**
     * Permet d'obtenir la texture du parchemin
     * @return Texture du parchemin
     */
    public String getTexture() {
        return texture;
    }

    /**
     * Méthode de hachage d'un Parchemin
     * @return Hash du parchemin
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.nom);
        hash = 71 * hash + Objects.hashCode(this.description);
        return hash;
    }

    /**
     * Méthode de vérification d'égalité d'un Parchemin par rapport à un autre
     * @param obj Parchemin à vérifier
     * @return Booléen égal ou non
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
        final Parchemin other = (Parchemin) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    
}

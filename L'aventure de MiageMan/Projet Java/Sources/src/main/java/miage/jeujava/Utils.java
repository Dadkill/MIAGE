/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.jeujava;

import Models.Parchemin;
import Models.Sortie;
import Models.Zone;
import Models.enigmes.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.filter.*;

/**
 *
 * @author theobredoux
 */
public class Utils {
    static org.jdom.Document document;
    static Element racine;
    static ArrayList<Zone> mesZones;

    /**
     * Permet d'obtenir un jeu de données à partir du XML
     * @return Permière zone du jeu
     */
    public static Zone gameMaker(){
        SAXBuilder sxb = new SAXBuilder();
        mesZones = new ArrayList<Zone>();
        try
        {
            document = sxb.build(new File("src/main/resources/config/Jeu.xml"));
        }
        catch(Exception e){}
        racine = document.getRootElement();
        getZones();
        getSorties();
        getParchemins();
        getEnigmes();
        return mesZones.get(0);
    }
    
    private static void getZones(){
        if(racine.getChild("zones") != null){
            List listZones = racine.getChild("zones").getChildren("zone");
            Iterator i = listZones.iterator();
            while(i.hasNext())
            {
                Element courant = (Element)i.next();
                String description = courant.getAttributeValue("description");
                String image = courant.getAttributeValue("image");
                mesZones.add(new Zone(description, image)); 
            }
        }
        
    }
    
    /**
     * Permet d'obtenir la zone finale de fin de jeu depuis le fichier XML
     * @return Zone de fin de jeu
     */
    public static Zone getZoneRoi(){
        Zone maZone = null;
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            document = sxb.build(new File("src/main/resources/config/Jeu.xml"));
        }
        catch(Exception e){}
        racine = document.getRootElement();
        if(racine.getChild("zoneRoi") != null){
            Element courant = racine.getChild("zoneRoi");
            String description = courant.getAttributeValue("description");
            String image = courant.getAttributeValue("image");
            maZone = new Zone(description, image);  
        }
        return maZone;
    }
    
    private static void getSorties(){
        // Récupération des sorties
        List listZones = racine.getChild("zones").getChildren("zone");
        Iterator zones = listZones.iterator();
        while(zones.hasNext())
        {
            Element zoneCourante = (Element)zones.next();
            if(zoneCourante.getChild("sorties") != null){
                List listSorties = zoneCourante.getChild("sorties").getChildren("sortie");
                int idZone = 0;
                try {
                        idZone = zoneCourante.getAttribute("id").getIntValue();
                    } catch (DataConversionException ex) {
                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                Iterator sorties = listSorties.iterator();
                while(sorties.hasNext()){
                    Element sortieCourante = (Element)sorties.next();
                    int id = 0;
                    try {
                        id = sortieCourante.getAttribute("id").getIntValue();
                    } catch (DataConversionException ex) {
                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String position = sortieCourante.getAttributeValue("position");
                    mesZones.get(idZone).ajouterSortie(mesZones.get(id), parseSortie(position));
                }
            }
        }
    }
    
    private static Sortie parseSortie(String sortie){
        switch(sortie){
            case "NORD":
                return Sortie.NORD;
            case "SUD":
                return Sortie.SUD;
            case "EST":
                return Sortie.EST;
            case "OUEST":
                return Sortie.OUEST;
        }
        return null;
    }
    
    private static void getParchemins(){
       List listZones = racine.getChild("zones").getChildren("zone");
       Iterator zones = listZones.iterator();
       while(zones.hasNext())
       {
           Element zoneCourante = (Element)zones.next();
           if(zoneCourante.getChild("parchemins") != null){
               List listParchemins = zoneCourante.getChild("parchemins").getChildren("parchemin");
           int idZone = 0;
           try {
               idZone = zoneCourante.getAttribute("id").getIntValue();
           } 
           catch (DataConversionException ex) {
               Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
           }
           Iterator parchemins = listParchemins.iterator();
           while(parchemins.hasNext()){
               Element parcheminCourant = (Element)parchemins.next();
               String nom = parcheminCourant.getAttributeValue("nom");
                mesZones.get(idZone).ajouterParchemin(new Parchemin(nom));
            }
           }
           
        } 
    }
    
    private static void getEnigmes(){
        List listZones = racine.getChild("zones").getChildren("zone");
        Iterator zones = listZones.iterator();
        while(zones.hasNext())
        {
            Element zoneCourante = (Element)zones.next();
            if(zoneCourante.getChild("enigmes") != null){
                List listEnigmes = zoneCourante.getChild("enigmes").getChildren("enigme");
                int idZone = 0;
                try {
                    idZone = zoneCourante.getAttribute("id").getIntValue();
                } 
                catch (DataConversionException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
                Iterator enigmes = listEnigmes.iterator();
                while(enigmes.hasNext()){
                    Element enigmeCourante = (Element)enigmes.next();
                    String type = enigmeCourante.getAttributeValue("type");
                    Enigme monEnigme = null;
                    switch(type){
                        case "olivettom":
                            monEnigme = creerEnigmeOlivettom(enigmeCourante);
                            break;
                        case "dilleme":
                            monEnigme = creerEnigmeDilleme(enigmeCourante);
                            break;
                        case "couleurs":
                            monEnigme = creerEnigmeCouleurs(enigmeCourante);
                            break;
                        case "piliers":
                            monEnigme = creerEnigmePiliers(enigmeCourante);
                            break;
                    }

                    mesZones.get(idZone).addEnigme(monEnigme);
                }
            
            }
        }  
    }
    private static EnigmeOlivettom creerEnigmeOlivettom(Element enigme){
        String message = enigme.getText();
        message = message.replaceAll("( )+", " ");
        String solution = enigme.getAttributeValue("solution");
        int zone = 0;
        try {
            zone = enigme.getAttribute("bloquage").getIntValue();
        } catch (DataConversionException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new EnigmeOlivettom(solution, message, mesZones.get(zone));
    }
    
    private static EnigmeDilemme creerEnigmeDilleme(Element enigme){
        try {
            String message = enigme.getText();
            message = message.replaceAll("( )+", " ");
            ArrayList<String> props = new ArrayList<String>();
            String solution = enigme.getAttributeValue("solution");
            int zoneGagnante = enigme.getAttribute("zoneGagnante").getIntValue();
            int zonePerdante = enigme.getAttribute("zonePerdante").getIntValue();
            List listPropositions = enigme.getChild("propositions").getChildren("proposition");
            Iterator propositions = listPropositions.iterator();
            while(propositions.hasNext()){
                Element propositionCourante = (Element)propositions.next();
                props.add(propositionCourante.getText().replaceAll("( )+", " "));
                
            }
            return new EnigmeDilemme(props, solution, message, mesZones.get(zoneGagnante), mesZones.get(zonePerdante));
        } catch (DataConversionException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReponseNonProposeeException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private static EnigmeCouleur creerEnigmeCouleurs(Element enigme){
        String message = enigme.getText();
        message = message.replaceAll("( )+", " ");
        int zone = 0;
        try {
            zone = enigme.getAttribute("bloquage").getIntValue();
        } catch (DataConversionException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new EnigmeCouleur(message, mesZones.get(zone));
    }
    
    private static EnigmeLevier creerEnigmePiliers(Element enigme){
        String message = enigme.getText();
        message = message.replaceAll("( )+", " ");
        String solution = enigme.getAttributeValue("solution");
        String [] valeurs = solution.split("-");
        ArrayList<Integer> sol = new ArrayList<Integer>();
        for(int i =0 ; i<valeurs.length;i++){
            sol.add(Integer.valueOf(valeurs[i]));
        }
        int zone = 0;
        try {
            zone = enigme.getAttribute("bloquage").getIntValue();
        } catch (DataConversionException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new EnigmeLevier(sol, message, mesZones.get(zone));
    }
    
    /**
     * Permet d'obtenir la traduction de l'application 
     * @return Traduction de l'application
     */
    public static HashMap<String, String> getStrings(){
        HashMap<String, String> mesStrings = new HashMap<String, String>();
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            document = sxb.build(new File("src/main/resources/config/Jeu.xml"));
        }
        catch(Exception e){}
        racine = document.getRootElement();
        if(racine.getChild("strings") != null){
            List listZones = racine.getChild("strings").getChildren("string");
            Iterator i = listZones.iterator();
            while(i.hasNext())
            {
                Element courant = (Element)i.next();
                String id = courant.getAttributeValue("id");
                String value = courant.getText();
                value = value.replaceAll("( )+", " ");
                mesStrings.put(id, value);
            }
        }
        return mesStrings;
    }
    
    /**
     * Permet d'obtenir les ressources de l'application
     * @return Ressources de l'application
     */
    public static HashMap<String, String> getRessources(){
        HashMap<String, String> mesRessources = new HashMap<String, String>();
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            document = sxb.build(new File("src/main/resources/config/Jeu.xml"));
        }
        catch(Exception e){}
        racine = document.getRootElement();
        if(racine.getChild("ressources") != null){
            List listZones = racine.getChild("ressources").getChildren("ressource");
            Iterator i = listZones.iterator();
            while(i.hasNext())
            {
                Element courant = (Element)i.next();
                String id = courant.getAttributeValue("id");
                String value = courant.getAttributeValue("valeur");
                mesRessources.put(id, value);
            }
        }
        return mesRessources;
    }
}

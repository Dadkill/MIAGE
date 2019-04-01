package Controllers;

import Models.Parchemin;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import Models.Sortie;
import Models.SortieBloqueeException;
import Models.Zone;
import Models.enigmes.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import miage.jeujava.Utils;

/**
 * Controlleur de l'Application
 * @author theobredoux
 */
public class GameController implements Initializable {
    /**
     * Zone affichée à l'écran
     */
    private Zone zoneCourante;
    /**
     * Zone de fin
     */
    private Zone zoneRoi;
    /**
     * Etat dans une énigme ou non
     */
    private boolean dansEnigme = false;
    /**
     * Liste des parchemins récoltés
     */
    private ArrayList<Parchemin> mesParchemins = new ArrayList<Parchemin>();
    /**
     * Traductions
     */
    private HashMap<String, String> translations;
    /**
     * Ressources
     */
    private HashMap<String, String> ressources;
    /**
     * Image
     */
    @FXML
    private GridPane img;
    /**
     * Zone de saisie
     */
    @FXML
    private TextField input;
    /**
     * Feuille de texte
     */
    @FXML
    private VBox text;
    
    /**
     * Zone de texte défilable 
     */
    @FXML
    private ScrollPane scrollPane;
    
    /**
     * Fenetre principale
     */
    @FXML
    private AnchorPane main;
    
    /**
     * Indicateur d'une enigme
     */
    @FXML
    private ImageView enigmeicon;
    
    @FXML
    private ImageView paricon;
    /**
     * Indicateur Nord
     */
    @FXML 
    private Label indinord;
    
    /**
     * Indicateur Sud
     */
    @FXML 
    private Label indisud;
    /**
     * Indicateur Est
     */
    @FXML 
    private Label indiest;
    /**
     * Indicateur Ouest
     */
    @FXML 
    private Label indiouest;
    
    @FXML 
    private AnchorPane cent1;
    @FXML 
    private AnchorPane cent2;
    @FXML 
    private AnchorPane cent3;
    @FXML 
    private AnchorPane cent4;
    
    
    /**
     * Permet de modifier l'image de l'ImageView img à partir du nom du fichier. 
     * Ce fichier doit être positionné dans ressources/img 
     * @param nom Nom du fichier à mettre en place
     */
    private void chargerImage(String nom){
        Image image;
        try {
            image = new Image(new FileInputStream("src/main/resources/img/"+nom));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            image = null;
        }
        BackgroundSize backgroundSize = new BackgroundSize(img.getWidth(), img.getHeight(), true, true, true, false);
        img.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
    }
    
    private ImageView getImageObject(String url){
        Image image;
        try {
            image = new Image(new FileInputStream("src/main/resources/img/"+url));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            image = null;
        }
        return new ImageView(image);
    }
    
    
   /**
    * Méthode d'initialisation de l'application 
    * @param url Url du fichier FXML
    * @param rb Bundle
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        translations = Utils.getStrings();
        ressources = Utils.getRessources();
        enigmeicon.setImage(getImageObject(ressources.get("i-enigme")).getImage());
        paricon.setImage(getImageObject(ressources.get("i-parchemin")).getImage());
        System.out.println(ressources);
        chargerZone(Utils.gameMaker());
        afficherMessageDeBienvenue();
        checkEnigmes();
        checkParchemins();
        zoneRoi = Utils.getZoneRoi();
        System.out.println(zoneRoi);
        
    }
    /**
     * Permet d'afficher le message de bienvenue
     */
    private void afficherMessageDeBienvenue() {
        afficherTexte(translations.get("t-help"));
    }

    /**
     * Permet de charger une Zone
     * @param zone Zone à charger
     */
    private void chargerZone(Zone zone){
        zoneCourante = zone;
        chargerImage(zoneCourante.getTexture());
        afficherTexte(String.format(translations.get("t-entree-salle"), zoneCourante.getNom()));
        chargerIndicateurs();
    }

    /**
     * Permet d'afficher un texte
     * @param message Message à afficher
     * @param utilisateur Provenant d'un utilisateur
     */
    @FXML 
    private void afficherTexte(String message, Boolean utilisateur){
        Label label = new Label(message);
        label.setWrapText(true);
        if(!utilisateur){
            label.getStyleClass().add("bot-text");
        }
        text.getChildren().add(label);
        scrollPane.vvalueProperty().bind(text.heightProperty());
        
    }
    
    /**
     * Permet d'afficher un message
     * Fait appel à {@link #afficherTexte(String, Boolean) afficherTexte(message, utilisateur)} 
     * avec comme paramètre false
     * @param message Message à afficher
     */
    @FXML 
    private void afficherTexte(String message){
        afficherTexte(message, false);
        
    }
    
    /**
     * Permet d'afficher un ensemble de messages sur plusieurs lignes
     * @param messages Tableau de messages à afficher
     */
    @FXML
    private void afficherTexte(String[] messages){
        for(int i = 0; i<messages.length;i++){
            afficherTexte(messages[i]);
        }
    }
    
    /**
     * Handler d'une saisie
     * @param event Evènement de saisie
     */
    @FXML 
    private void validerEntree(ActionEvent event){
        String valeur = input.getText();
        afficherTexte(valeur, true);
        input.setText("");
        if(dansEnigme){
            menuEnigme(valeur);
        }
        else{
            menuGeneral(valeur);
        }
        checkEnigmes();
        checkParchemins();
    }
    
    /**
     * Menu présentant les énigmes
     * @param valeur Valeur de saisie
     */
    private void menuEnigme(String valeur){
        Enigme monEnigme = zoneCourante.getEnigme();
        switch(valeur){
            case "Q":
                dansEnigme=false;
                afficherTexte(translations.get("t-cancel"));
                break;
            case "?":
                afficherTexte(translations.get("h-enigme"));
                break;
            default:
                if(monEnigme instanceof EnigmeOlivettom){
                    ((EnigmeOlivettom) monEnigme).tester(valeur);
                }
                else if(monEnigme instanceof EnigmeDilemme){
                    try {
                        ((EnigmeDilemme) monEnigme).tester(valeur);
                    } catch (ReponseNonProposeeException ex) {
                        afficherTexte(translations.get("t-non-proposee"));
                        return;
                    }
                }
                if(monEnigme.resolue()){
                    afficherTexte(new String[] {
                        translations.get("t-bonne-reponse"),
                        translations.get("t-porte-ouverte")});
                    zoneCourante.removeEnigme(monEnigme);
                    dansEnigme=false;
                }
                else{
                    if(monEnigme instanceof EnigmeDilemme){
                        zoneCourante.removeEnigme(monEnigme);
                        dansEnigme=false;
                        afficherTexte(translations.get("t-porte-ouverte"));
                    }
                    else{
                        afficherTexte(translations.get("t-mauvaise-reponse"));
                    }
                    
                }
        }
    }
    
    /**
     * Menu Principal
     * @param valeur Valeur de saisie
     */
    private void menuGeneral(String valeur){
        switch (valeur) {
            case "N":
                try {
                    chargerZone(zoneCourante.obtenirSortie(Sortie.NORD));
                }
                catch(SortieBloqueeException e){
                    afficherTexte(e.getMessage());
                }
                break;
            case "S":
                try {
                    chargerZone(zoneCourante.obtenirSortie(Sortie.SUD));
                }
                catch(SortieBloqueeException e){
                    afficherTexte(e.getMessage());
                }
                break;
            case "E":
                try {
                    chargerZone(zoneCourante.obtenirSortie(Sortie.EST));
                }
                catch(SortieBloqueeException e){
                    afficherTexte(e.getMessage());
                }
                
                break;
            case "O":
                try {
                    chargerZone(zoneCourante.obtenirSortie(Sortie.OUEST));
                }
                catch(SortieBloqueeException e){
                    afficherTexte(e.getMessage());
                }
                break;
            
            case "P":
                if(zoneCourante.getParchemins().size() != 0){
                    mesParchemins.add(zoneCourante.getParchemins().get(0));
                    zoneCourante.getParchemins().remove(0);
                }
                break;
            case "R":
                if(zoneCourante.hasEnigmes()){
                    beginEnigme();
                }
                break;
             case "B":
                afficherTexte(String.format(translations.get("t-nb-parchemins"), mesParchemins.size()));
                break;
             case "Q":
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle(translations.get("q-quitter"));
                alert.setHeaderText(translations.get("q-quitter-texte"));
                alert.setContentText(translations.get("q-quitter-sure"));
                ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
                ButtonType buttonTypeOk = new ButtonType("Quitter", ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOk){
                    Platform.exit();
                } 
                break;
                 
            case "?":
                afficherTexte(translations.get("h-main"));
                break;
            default:
                afficherMessageDeBienvenue();
        }
    }
    
    /**
     * Permet d'afficher ou non l'indicateur d'énigmes
     */
    private void checkEnigmes(){
        if(zoneCourante.hasEnigmes()){
            enigmeicon.setVisible(true);
        }
        else{
            enigmeicon.setVisible(false);
        }
    }
    
    
    private void checkParchemins(){
        if(zoneCourante.getParchemins().size()!=0){
            paricon.setVisible(true);
        }
        else{
            paricon.setVisible(false);
        }
        if(mesParchemins.size() == 4){
            chargerZone(zoneRoi);
            afficherTexte(translations.get("t-gagne"));
        }
        
    }
    
    private void validerEnigmeLevier(){
        EnigmeLevier enigme =(EnigmeLevier) zoneCourante.getEnigme();
        if(enigme.resolue()){
            afficherTexte(translations.get("t-porte-ouverte"));
            dansEnigme = false;
            cent1.getChildren().clear();
            cent2.getChildren().clear();
            cent3.getChildren().clear();
            zoneCourante.removeEnigme(enigme);
        }
        else if(enigme.getNbActions() == 0){
            afficherTexte(translations.get("t-mauvaise-reponse"));
            cent1.getChildren().clear();
            cent2.getChildren().clear();
            cent3.getChildren().clear();
            ImageView v = getImageObject(ressources.get("levierF"));
            v.setFitHeight(cent1.getHeight());
            v.setFitWidth(cent1.getWidth());
            v.preserveRatioProperty().set(true);
            cent1.getChildren().add(v);
            ImageView v2 = getImageObject(ressources.get("levierF"));
            v2.setFitHeight(cent2.getHeight());
            v2.setFitWidth(cent2.getWidth());
            v2.preserveRatioProperty().set(true);
            cent2.getChildren().add(v2);
            ImageView v3 = getImageObject(ressources.get("levierF"));
            v3.setFitHeight(cent3.getHeight());
            v3.setFitWidth(cent3.getWidth());
            v3.preserveRatioProperty().set(true);
            cent3.getChildren().add(v3);
            
        }
        checkEnigmes();
    }
    
    private void beginEnigme(){
        afficherTexte(zoneCourante.getEnigme().getMessage());
        if(zoneCourante.getEnigme() instanceof EnigmeDilemme){
            EnigmeDilemme monEnigme = (EnigmeDilemme) zoneCourante.getEnigme();
            ArrayList<String> mesPropositions = monEnigme.getPropositions();
                for(int i = 0; i<mesPropositions.size();i++){
                    afficherTexte(mesPropositions.get(i));
                }
        }
        if(zoneCourante.getEnigme() instanceof EnigmeLevier){
            ImageView v = getImageObject(ressources.get("levierF"));
            v.setFitHeight(cent1.getHeight());
            v.setFitWidth(cent1.getWidth());
            v.preserveRatioProperty().set(true);
            cent1.getChildren().add(v);
            ImageView v2 = getImageObject(ressources.get("levierF"));
            v2.setFitHeight(cent2.getHeight());
            v2.setFitWidth(cent2.getWidth());
            v2.preserveRatioProperty().set(true);
            cent2.getChildren().add(v2);
            ImageView v3 = getImageObject(ressources.get("levierF"));
            v3.setFitHeight(cent3.getHeight());
            v3.setFitWidth(cent3.getWidth());
            v3.preserveRatioProperty().set(true);
            cent3.getChildren().add(v3);
        }
        if(zoneCourante.getEnigme() instanceof EnigmeCouleur){
            ImageView v = getImageObject(ressources.get("couleur-blanc"));
            v.setFitHeight(cent1.getHeight());
            v.setFitWidth(cent1.getWidth());
            v.preserveRatioProperty().set(true);
            cent1.getChildren().add(v);
            ImageView v2 = getImageObject(ressources.get("couleur-bleu"));
            v2.setFitHeight(cent2.getHeight());
            v2.setFitWidth(cent2.getWidth());                
            v2.preserveRatioProperty().set(true);
            cent2.getChildren().add(v2);
            ImageView v3 = getImageObject(ressources.get("couleur-noir"));
            v3.setFitHeight(cent3.getHeight());
            v3.setFitWidth(cent3.getWidth());
            v3.preserveRatioProperty().set(true);
            cent3.getChildren().add(v3);
            ImageView v4 = getImageObject(ressources.get("couleur-rouge"));
            v4.setFitHeight(cent4.getHeight());
            v4.setFitWidth(cent4.getWidth());
            v4.preserveRatioProperty().set(true);
            cent4.getChildren().add(v4);
        }
        dansEnigme = true;
    }
    
    /**
     * Permet d'afficher ou non les indicateurs de sortie
     */
    private void chargerIndicateurs(){
        indinord.setVisible(false);
        indisud.setVisible(false);
        indiest.setVisible(false);
        indiouest.setVisible(false);
        for(Sortie s : zoneCourante.getSortiesPossibles()){
            switch(s){
                case NORD:
                    indinord.setVisible(true);
                    break;
                case SUD:
                    indisud.setVisible(true);
                    break;
                case EST:
                    indiest.setVisible(true);
                    break;
                case OUEST:
                    indiouest.setVisible(true);
                    break;
            }
        }
    }
    
    /**
     * Action lors du clic sur une des images
     * @param e Evènement appelant
     */
    @FXML
    public void onClickImageHandler(MouseEvent e){
        AnchorPane element = (AnchorPane) e.getSource();
        if(dansEnigme){
            if(zoneCourante.getEnigme() instanceof EnigmeLevier){
                EnigmeLevier monEnigme = (EnigmeLevier) zoneCourante.getEnigme();
                
                switch (element.getId()){
                    case "cent1":
                        monEnigme.activer(1);
                        break;
                    case "cent2":
                        monEnigme.activer(2);
                        break;
                    case "cent3":
                        monEnigme.activer(3);
                        break;
                        
                }
                if(!element.getId().equals("cent4")){
                    ImageView v = getImageObject(ressources.get("levierO"));
                    v.setFitHeight(element.getHeight());
                    v.setFitWidth(element.getWidth());
                    v.preserveRatioProperty().set(true);
                    element.getChildren().clear();
                    element.getChildren().add(v);
                    validerEnigmeLevier();
                }
            }
            else if(zoneCourante.getEnigme() instanceof EnigmeCouleur){
                TextInputDialog dialog = new TextInputDialog("#xxxxxx");
                Couleur couleur = null;
                switch(element.getId()){
                    case "cent1":
                        couleur = Couleur.BLANC;
                        break;
                    case "cent2":
                        couleur = Couleur.BLEU;
                        break;
                    case "cent3":
                        couleur = Couleur.NOIR;
                        break;
                    case "cent4":
                        couleur = Couleur.ROUGE;
                        break;
                    
                }
                dialog.setTitle(translations.get("q-couleur"));
                dialog.setHeaderText(translations.get("q-hexa"));
                dialog.setContentText(translations.get("q-code"));
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    EnigmeCouleur monEnigme = (EnigmeCouleur) zoneCourante.getEnigme();
                    monEnigme.associer(couleur, result.get());
                }
                validerEnigmeCouleur();
            }
        }
    }
    
    private void validerEnigmeCouleur(){
        EnigmeCouleur enigme =(EnigmeCouleur) zoneCourante.getEnigme();
        if(enigme.resolue()){
            afficherTexte(translations.get("t-porte-ouverte"));
            dansEnigme = false;
            cent1.getChildren().clear();
            cent2.getChildren().clear();
            cent3.getChildren().clear();
            cent4.getChildren().clear();
            zoneCourante.removeEnigme(enigme);
        }
        else if(enigme.getNbResultats() == 0){
            afficherTexte(translations.get("t-mauvaise-reponse"));
        }
        checkEnigmes();
    }
}

package Models.enigmes;

import Models.Zone;
import Models.enigmes.Couleur;
import Models.enigmes.EnigmeCouleur;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
 
class EnigmeCouleurTest {
    Zone z1, z2;
    EnigmeCouleur e1, e2;
    
    @BeforeEach
    public void initialisation() {
        z1 = new Zone("Zone 1", "image.jpg");
        z2 = new Zone("Zone 2", "image.jpg");
        e1 = new EnigmeCouleur("Tester les couleurs", z1);
        e2 = new EnigmeCouleur("Tester les couleurs", z2);
    }
    
    @Test
    public void testEnigmeConstructor(){
        assertEquals("Tester les couleurs", e1.getMessage());
        assertTrue(z1.getEstBloquee());
        
    }
    
    @Test
    public void testEnigme(){
        e1.associer(Couleur.BLANC, Couleur.BLANC.hexaCode());
        e1.associer(Couleur.BLEU, Couleur.BLEU.hexaCode());
        e1.associer(Couleur.ROUGE, Couleur.ROUGE.hexaCode());
        e1.associer(Couleur.NOIR, Couleur.NOIR.hexaCode());
        assertTrue(e1.resolue());
        e2.associer(Couleur.BLANC, Couleur.BLANC.hexaCode());
        e2.associer(Couleur.BLEU, "223");
        e2.associer(Couleur.ROUGE, Couleur.ROUGE.hexaCode());
        e2.associer(Couleur.NOIR, Couleur.NOIR.hexaCode());
        assertFalse(e2.resolue());
    }
    
    @Test
    public void testCompteurEnigme(){
        assertEquals(0, e1.getNbResultats());
        e1.associer(Couleur.BLANC, Couleur.BLANC.hexaCode());
        assertEquals(1, e1.getNbResultats());
        e1.associer(Couleur.BLANC, Couleur.BLANC.hexaCode());
        assertEquals(1, e1.getNbResultats());
        e1.associer(Couleur.ROUGE, Couleur.ROUGE.hexaCode());
        assertEquals(2, e1.getNbResultats());
        e1.associer(Couleur.NOIR, Couleur.ROUGE.hexaCode());
        assertEquals(3, e1.getNbResultats());
        e1.associer(Couleur.BLEU, Couleur.ROUGE.hexaCode());
        assertEquals(0, e1.getNbResultats());
    }
    
    
}
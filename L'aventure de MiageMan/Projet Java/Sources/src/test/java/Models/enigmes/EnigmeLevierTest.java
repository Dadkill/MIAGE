package Models.enigmes;

import Models.Zone;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
 
class EnigmeLevierTest {
    Zone z1, z2;
    EnigmeLevier e1, e2, e3;
    ArrayList<Integer> resultat;
    
    @BeforeEach
    public void initialisation() {
        
        z1 = new Zone("Zone 1", "image.jpg");
        z2 = new Zone("Zone 2", "image.jpg");
        resultat = new ArrayList<Integer>();
        resultat.add(3);
        resultat.add(2);
        resultat.add(1);
        e1 = new EnigmeLevier(resultat, "Mon enigme", z1);
        e2 = new EnigmeLevier(resultat, "Mon enigme", z2);
        e3 = new EnigmeLevier(resultat, "Mon enigme", z1);
    }
    
    @Test
    public void testEnigmeConstructor(){
        assertEquals("Mon enigme", e1.getMessage());
        assertTrue(z1.getEstBloquee());
        assertEquals(3, e1.nbPossibilites());
        assertFalse(e1.resolue());
    }
    
    @Test 
    public void testResultat(){
        e1.activer(3);
        assertEquals(1, e1.getNbActions());
        e1.activer(2);
        assertEquals(2, e1.getNbActions());
        e1.activer(1);
        assertEquals(3, e1.getNbActions());
        assertTrue(e1.resolue());
        e2.activer(2);
        assertEquals(1, e2.getNbActions());
        e2.activer(1);
        assertEquals(2, e2.getNbActions());
        e2.activer(3);
        assertEquals(0, e2.getNbActions());
        assertFalse(e2.resolue());
        
        
        
    }
}
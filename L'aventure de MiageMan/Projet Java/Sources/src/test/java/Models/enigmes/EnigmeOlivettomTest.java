package Models.enigmes;

import Models.Zone;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
 
public class EnigmeOlivettomTest {
    Zone z1, z2;
    EnigmeOlivettom e1, e2;
    
    @BeforeEach
    public void initialisation() {
        z1 = new Zone("Zone 1", "image.jpg");
        z2 = new Zone("Zone 2", "image.jpg");
        e1 = new EnigmeOlivettom( "Toto", "Ma Question", z1);
        e2 = new EnigmeOlivettom("Toto", "Ma Question", z1);
    }
    
    @Test
    public void testEnigmeConstructor(){
        assertEquals("Ma Question", e1.getMessage());
        assertTrue(z1.getEstBloquee());
        assertFalse(e1.resolue());
    }
    
    @Test 
    public void testReponseNormal(){
        e1.tester("Toto");
        assertTrue(e1.resolue());
        e2.tester("Deux");
        assertFalse(e2.resolue());
        
    }
    
    @Test 
    public void testReponseMaj(){
        e1.tester("TOTO");
        assertTrue(e1.resolue());
        e2.tester("DEUX");
        assertFalse(e2.resolue());
    }
    
    @Test 
    public void testReponseMin(){
        e1.tester("toto");
        assertTrue(e1.resolue());
        e2.tester("deux");
        assertFalse(e2.resolue());
    }   
    
    
}
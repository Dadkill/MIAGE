package Models.enigmes;

import Models.Zone;
import Models.enigmes.EnigmeDilemme;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
 
class EnigmeDilemmeTest {
    Zone z1, z2;
    EnigmeDilemme e1, e2, e3;
    ArrayList<String> propositions;
    
    @BeforeEach
    public void initialisation() {
        try {
            z1 = new Zone("Zone 1", "image.jpg");
            z2 = new Zone("Zone 2", "image.jpg");
            propositions = new ArrayList<String>();
            propositions.add("Un");
            propositions.add("Deux");
            e1 = new EnigmeDilemme(propositions, "Un", "Quel Dilemme", z1, z2);
            e2 = new EnigmeDilemme(propositions, "Un", "Quel Dilemme", z1, z2);
            e3 = new EnigmeDilemme(propositions, "Un", "Quel Dilemme", z1, z2);
        } catch (ReponseNonProposeeException ex) {
            Logger.getLogger(EnigmeDilemmeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testEnigmeConstructor(){
        assertEquals("Quel Dilemme", e1.getMessage());
        assertTrue(z1.getEstBloquee());
        assertEquals(propositions, e1.getPropositions());
        assertFalse(e1.resolue());
    }
    
    @Test 
    public void testReponseNormal(){
        try {
            e1.tester("Un");
            assertTrue(e1.resolue());
            e2.tester("Deux");
            assertFalse(e2.resolue());
        } catch (ReponseNonProposeeException ex) {
            fail("L'exception ne doit pas se lever");
        }
        try {
            e3.tester("Toto");
            fail("L'exception ReponseNonProposeeException doit être levée");
        } catch (ReponseNonProposeeException ex) {}  
    }
    
    @Test 
    public void testReponseMaj(){
        try {
            e1.tester("UN");
            assertTrue(e1.resolue());
            e2.tester("DEUX");
            assertFalse(e2.resolue());
        } catch (ReponseNonProposeeException ex) {
            fail("L'exception ne doit pas se lever");
        }
        try {
            e3.tester("TOTO");
            fail("L'exception ReponseNonProposeeException doit être levée");
        } catch (ReponseNonProposeeException ex) {}  
    }
    
    @Test 
    public void testReponseMin(){
        try {
            e1.tester("un");
            assertTrue(e1.resolue());
            e2.tester("deux");
            assertFalse(e2.resolue());
        } catch (ReponseNonProposeeException ex) {
            fail("L'exception ne doit pas se lever");
        }
        try {
            e3.tester("toto");
            fail("L'exception ReponseNonProposeeException doit être levée");
        } catch (ReponseNonProposeeException ex) {}  
    }   
    
    @Test
    public void testNoProposition(){
        try {
            EnigmeDilemme e = new EnigmeDilemme(null, "Test 1", "Test", z1, z2);
            EnigmeDilemme e2 = new EnigmeDilemme(new ArrayList<String>(), "Test 1", "Test", z1, z2);
            fail("Une Exception ReponseNonProposeeException devrait être levée");
        } catch (ReponseNonProposeeException ex) {}
        
        try {
            Enigme e = new EnigmeDilemme(propositions, "Test1", "Test", z1, z2);
        } catch (ReponseNonProposeeException ex) {
            fail("L'Exception ReponseNonProposeeException n'aurait pas du être levée");
        }
        
    }
}
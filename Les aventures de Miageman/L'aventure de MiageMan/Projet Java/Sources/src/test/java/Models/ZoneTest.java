package Models;
import Models.Parchemin;
import Models.Sortie;
import Models.SortieBloqueeException;
import Models.Zone;
import Models.enigmes.EnigmeOlivettom;
import Models.enigmes.EnigmeOlivettomTest;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
 
class ZoneTest {
    Zone z1, z2, z3, z4;
    Parchemin p1, p2;
    
    @BeforeEach
    public void initialisation() {
        z1 = new Zone("Zone1", "logo1.png");
        z2 = new Zone("Zone2", "logo2.png");
        z3 = new Zone("Zone3", "logo3.png");
        z4 = new Zone("Zone4", "logo4.png");
        z1.ajouterSortie(z2, Sortie.NORD);
        z1.ajouterSortie(z3, Sortie.EST);
        z2.ajouterSortie(z1, Sortie.SUD);
        z3.ajouterSortie(z1, Sortie.OUEST);
        p1 = new Parchemin("Parchemin1", "Ceci est le premier parchemin", "p1.png");
        p2 = new Parchemin("Parchemin2", "Ceci est le second parchemin", "p2.png");
    }
    
    @Test
    public void testZoneConstructor(){
        assertEquals("Zone1", z1.getNom());
        assertEquals("Zone2", z2.getNom());
        assertEquals("Zone3", z3.getNom());
        assertEquals("Zone4", z4.getNom());
        assertEquals("logo1.png", z1.getTexture());
        assertEquals("logo2.png", z2.getTexture());
        assertEquals("logo3.png", z3.getTexture());
        assertEquals("logo4.png", z4.getTexture());  
    }
    
    @Test
    public void testParcoursZone() throws SortieBloqueeException{
        assertEquals(z2, z1.obtenirSortie(Sortie.NORD));
        assertEquals(z3, z1.obtenirSortie(Sortie.EST));
        try {
            z1.obtenirSortie(Sortie.SUD);
            fail("Exception SortieBloqueeException non levee");
        }
        catch(SortieBloqueeException e){}
        
        
        assertEquals(z1, z1.obtenirSortie(Sortie.NORD).obtenirSortie(Sortie.SUD));
    }
    
    @Test
    public void testParcoursZoneBloquee() {
        z2.setEstBloquee(true);
        try {
            z1.obtenirSortie(Sortie.NORD);
            fail("Exception SortieBloqueeException non levee");
        }
        catch(SortieBloqueeException e){}
        EnigmeOlivettom en = new EnigmeOlivettom("Toto", "Tutu", z3);
        try {
            z1.obtenirSortie(Sortie.EST);
            fail("Exception SortieBloqueeException non levee");
        }
        catch(SortieBloqueeException e){}
        
    }
}
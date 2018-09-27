package arithmetic;

import java.util.LinkedList;
import nl.tue._2WF90.common.Polynomial;

/**
 * Data structure that stores a computation. Includes things like the type
 * (E.g. display-poly, long-div-poly, etc.), the polynomials to operate on,
 * the answer (once computed), etc.
 * 
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 * 
 * @since 27 SEPTEMBER 2018
 */
public class Computation {
    private final Polynomial f; //stores mod-poly in case Finite Field Arithmetic is performed
    private final Polynomial g; //stores a in case Finite Field Arithmetic is performed
    private final Polynomial h; //stores b in case Finite Field Arithmetic is performed
    private Polynomial answer;
    
    private final String type;
    private final int mod;
    
    private boolean error = false; //indicates an error
    
    //constructor
    public Computation(Polynomial f, Polynomial g, Polynomial h, String type, int mod) {
        this.f = f;
        this.g = g;
        this.h = h;
        this.type = type;
        this.mod = mod;
    }
    
    
    
    /*Other stuff*/
    @Override
    public String toString() {
        return "(" + type + " (mod " + mod + "), " + f + ", " + g + ", " + h + ")";
    }
}

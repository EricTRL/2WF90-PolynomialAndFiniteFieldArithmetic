package nl.tue._2WF90.common;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * This is the base class that is run.
 * It reads input with InputReader, applies any of the arithmetic functions
 * and return the output via OutputWriter
 * 
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 * 
 * @since 27 SEPTEMBER 2018
 */
public class Arithmetic {
    //Main method
    public static void main(String args[]) {
        Polynomial poly = new Polynomial(new LinkedList<>(Arrays.asList(99, 0, 1, 5)));
        System.out.println(poly);
        System.out.println(new Polynomial("{-9,07,-5,0}"));
        System.out.println(new Polynomial("{0,1,-5,0}"));
        System.out.println(new Polynomial("{}"));
        System.out.println(new Polynomial("{0,0,0,0}"));
        
        //System.out.println(divide(new Polynomial("{1,0,0,5,3}"), new Polynomial("{1,0}")));
        
        Polynomial a = new Polynomial("1,0,-1");
        Polynomial b = new Polynomial("1,-1");
        System.out.println("derp");
        System.out.println(Division.divide(a, b, 10000000));
    }
    
    /**
     * Method that returns whether a given, positive integer is prime
     * @param p A positive integer
     * @pre p >= 0
     * @return true iff p is prime
     */
    public static boolean isPrime(int p) {
        if (p <= 1) {
            return false;
        }
        //TODO
        return true;
    }
    
    /**
     * Removes all leading zeros from a given Polynomial
     * @param x The polynomial to remove the leading zeros from (if any)
     * @pre x != null
     * @modifies x
     * @post x.getHighestDegree() != 0 || x.getDegree() == 1
     * @return x
     */
    public static Polynomial removeLeadingZeros(Polynomial x) {
        while (x.getLeadingCoefficient() == 0 && x.hasDegree()) {
            x.removeLeadingCoefficient();
        }
        return x;
    }
}

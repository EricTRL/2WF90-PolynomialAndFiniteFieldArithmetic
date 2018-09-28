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
        System.out.println(Polynomial.stringToPolynomial("{9,07,5,0}"));
        System.out.println(Polynomial.stringToPolynomial("{0,5,5,0}"));
        System.out.println(Polynomial.stringToPolynomial("{}"));
        System.out.println(Polynomial.stringToPolynomial("{0,0,0,0}"));
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
}

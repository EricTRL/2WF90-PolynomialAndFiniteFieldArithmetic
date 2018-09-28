package nl.tue._2WF90.common;

import java.util.LinkedList;
import java.util.Scanner;


/**
 * ADT to represent a polynomial of the form aX^n + bX^n-1 + ... + cX + d, with
 * a being integers and n being the degree of the polynomial.
 * 
 * Representation Invariants:
 * Is Zero or contains at least 1 non-zero element: (\exists i; poly.contains(i); i != 0) || poly.length() == 0
 * 
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 * 
 * @since 27 SEPTEMBER 2018
 */
public class Polynomial {
    private final LinkedList<Integer> poly;
    
    public Polynomial(LinkedList<Integer> poly) {
        this.poly = poly;
    }
    
    /**
     * Gets the degree of the polynomial
     * @return deg(poly), or -1 if poly == 0
     */
    public int getDegree() {
        if (!hasDegree()) {
            return -1;
        }
        return poly.size();
    }
    
    /**
     * Checks whether this polynomial has a degree
     * @return true iff poly.size() > 0
     */
    public boolean hasDegree() {
        return !poly.isEmpty() && !(poly.peekFirst() == 0 && poly.size() == 1);
    }
    
    @Override
    public String toString() {
        if (!hasDegree()) {
            return "0";
        }
        
        StringBuilder s = new StringBuilder();
        //start at highest polynomial
        int order = poly.size() - 1;
        for (int x : poly) {
            if (x != 0) {
                s.append(x);
                if (order > 0) {
                    s.append("X");
                }
                if (order > 1) {
                    s.append("^").append(order);
                }
                s.append("+");
            }
            order--;
        }
        //remove the last '+' that we added
        s.setLength(s.length() - 1);
        
        return s.toString();
    }
    
    /**
     * Converts a string to a polynomial
     * @param s String in the specified input format. E.g. {4,0,5,6204}
     * @return 
     */
    public static Polynomial stringToPolynomial(String s) {
        Scanner sc = new Scanner(s);
        LinkedList<Integer> l = new LinkedList<>();
        boolean nonZeroElemFound = false;
        
        sc.useDelimiter(",|\\{|\\}");
        while (sc.hasNextInt()) {
            int next = sc.nextInt();
            nonZeroElemFound = nonZeroElemFound || next != 0;
            if (nonZeroElemFound) {
                l.addLast(next);
            }
        }
        
        //handle 0-polynomials that are passed as {}
        if (l.isEmpty()) {
            l.addFirst(0);
        }
        return new Polynomial(l);
    }
}

package nl.tue._2WF90.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
public class Polynomial{
    private final LinkedList<Integer> poly;
    
    public Polynomial(LinkedList<Integer> poly) {
        if (poly == null || poly.isEmpty()) {
            this.poly = new LinkedList<>();
            this.poly.addFirst(0);
        } else {
            this.poly = poly;
        }
    }
    
    public Polynomial(List<Integer> poly) {
        this(new LinkedList<>(poly));
    }
    
    public Polynomial() {
        this((LinkedList) null);
    }
    
    /**
     * Allows construction of iX^termIndex
     * @param i Leading coefficient
     * @param degree degree
     */
    public Polynomial(int i, int degree) {
        this.poly = new LinkedList<>();
        
        for (int j = 0; j < degree; j++) {
            poly.addFirst(0);
        }
        poly.addFirst(i);
    }
    
    public Polynomial(int i) {
        this(new LinkedList<>(Arrays.asList(i)));
    }
    
    public Polynomial(String s) {
        Scanner sc = new Scanner(s);
        LinkedList<Integer> l = new LinkedList<>();
        boolean nonZeroElemFound = false;
        
        sc.useDelimiter(",|\\{|\\}"); //use commas, {, and } as a separator
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
        this.poly = l;
    }
    
    /**
     * Gets the degree of the polynomial
     * @return deg(poly), or -1 if poly == 0
     */
    public int getDegree() {
        if (!hasDegree()) {
            return -1;
        }
        return poly.size()-1;
    }
    
    /**
     * Checks whether this polynomial has a degree
     * @return true iff poly.size() > 0
     */
    public boolean hasDegree() {
        return !(poly.peekFirst() == 0 && poly.size() == 1);
    }
    
    /**
     * Gets the number of the highest degree
     * @return the number of the highest degree
     */
    public int getLeadingCoefficient() {
        return poly.getFirst();
    }
    
    /**
     * Removes the highest degree number from the Polynomial
     */
    public int removeLeadingCoefficient() {
        return poly.removeFirst();
    }
    
    /**
     * Gets the number of the lowest degree
     * @return the number of the lowest degree
     */
    public int getLowestCoefficient() {
        return poly.getLast();
    }
    
    /**
     * Gets an (ascending) iterator over the elements of the Polynomial 
     * @return ascending iterator over the elements of the polynomial
     */
    public Iterator<Integer> iterator() {
        return poly.iterator();
    }
    
    /**
     * Gets a descending iterator over the elements of the polynomial
     * @return descending Iterator over the elements of the Polynomial 
     */
    public Iterator<Integer> descendingIterator() {
        return poly.descendingIterator();
    }
    
    /**
     * Gets the size of the polynomial
     * @return size of the polynomial
     */
    public int getSize() {
        return poly.size();
    }
    
    /**
     * Adds a coefficient to the front of the polynomial
     * @param x the leading coefficient to add
     */
    public void addFirst(int x) {
        poly.addFirst(x);
    }
    
    /**
     * Adds a coefficient to the end of the polynomial
     * @param x the smallest coefficient to add
     */
    public void addLast(int x) {
        poly.addLast(x);
    }
    
    /**
     * Gets the coefficient at index x of the polynomial
     * @param x the index to get from the polynomial
     * @return the coefficient at index x from the polynomial
     */
    public int get(int x) {
        return poly.get(x);
    }
    
    /**
     * Sets the coefficient at index x of the polynomial to y
     * @param x the index to set at
     * @param y the coefficient to set index x to
     */
    public void set(int x, int y) {
        poly.set(x, y);
    }
    
    /**
     * 
     * @return 
     */
    public void specialSet(int index, int y) {
        if (index < 0) {
            while (index < poly.size()) {
                
            }
        } else {
            poly.set(index, y);
        }
    }

    /**
     * Returns whether this poly equals another poly
     * @param a the other poly we compare to
     * @return this.poly==a.poly
     */
    public boolean equals(Polynomial a) {
        Iterator<Integer> it = iterator(); Iterator<Integer> itA = a.iterator();
        if (getDegree()>a.getDegree()) {
            a.addZeros(getDegree()-a.getDegree());
        } else {
            addZeros(a.getDegree()-getDegree());
        }
        while (it.hasNext() && itA.hasNext()) {
            if (it.next()!=itA.next()) return false;
        }
        a.removeZeros(); removeZeros();
        return true;
    }
    

    @Override
    public String toString() {
        if (!hasDegree()) {
            return "0";
        }
        
        StringBuilder s = new StringBuilder();
        //start at highest polynomial
        boolean notLeadingTerm = false;
        int order = poly.size() - 1;
        for (int x : poly) {
            if (x != 0) {
                if (notLeadingTerm || x < 0) {
                    //add + or - (depending on Sign of X)
                    s.append(x > 0 ? "+" : "-");
                }
                notLeadingTerm = true;
                
                if ((x != 1 && x != -1) || order == 0) {
                    s.append(Math.abs(x));
                }
                if (order > 0) {
                    s.append("X");
                }
                if (order > 1) {
                    s.append("^").append(order);
                }
            } //else skip
            order--;
        }
        //System.out.println(poly);
        return s.toString();
    }
    
    /**
     * Gives the polynomial
     * @return 
     */
    public ArrayList<Integer> asArrayList() {
        return new ArrayList<>(poly);
    }

    /**
     * Function that adds leading zeros to a polynomial (useful when adding or subtracting)
     * @param z amount of leading zeros that need to be added
     * @post this.poly has z leading zeros
     */
    public void addZeros(int z) {
        for (int i = 0; i < z; i++) {
            poly.addFirst(0);
        }
    }

    /**
     * Removes all the leading zeros from this polynomial
     * @return 
     * @post removes all the leading zeros from the polynomial
     */
    public Polynomial removeZeros() {
        while (getLeadingCoefficient() == 0 && hasDegree()) {
            removeLeadingCoefficient();
        }
        return this;
    }
    
    /**
     * Copies the polynomial
     * @return an exact copy of the polynomial
     */
    public Polynomial copy() {
        return new Polynomial(poly);
    }

}

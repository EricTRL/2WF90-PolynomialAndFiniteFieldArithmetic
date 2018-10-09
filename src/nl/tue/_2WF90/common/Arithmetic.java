package nl.tue._2WF90.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
        System.out.println(new Polynomial("{9,07,5,0}"));
        System.out.println(new Polynomial("{0,5,5,0}"));
        System.out.println(new Polynomial("{}"));
        System.out.println(new Polynomial("{0,0,0,0}"));
        
        System.out.println(divide(new Polynomial("{1,0,0,5,3}"), new Polynomial("{1,0}")));
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
        while (x.getLeadingTerm() == 0 && x.hasDegree()) {
            x.removeLeadingTerm();
        }
        return x;
    }
    
    
    
    /**
     * Calculates x (mod m)
     * @param x polynomial to be reduced
     * @param m modulo
     * @return x (mod m)
     */
    public static Polynomial modulo(Polynomial x, int m) {
        return divide(x, new Polynomial(m)).r;
    }
    
    /**
     * Applies long division on Polynomials x and y
     * @param x numerator
     * @param y denominator
     * @pre y != 0
     * @return (q,r) with q=x/y and r=x%y
     */
    public static QuoRem divide(Polynomial x, Polynomial y) {
        //the first 'bit' of y cannot be 0. It doesn't matter for x, but it reduces the amount of computations we have to do, so we do it anyways.
        Arithmetic.removeLeadingZeros(x); Arithmetic.removeLeadingZeros(y);
        
        //TODO: special case x <= 0
        if (!x.hasDegree()) {
            return new QuoRem(new Polynomial(0), new Polynomial(0));
        }

        int k = x.getDegree() + 1;
        int l = y.getDegree() + 1;
        int b = 10; //use base 10
        
        //if one number is longer than the other one, it must be bigger
        //Moreover, long division will result in q = 0 and r = x
        if (l > k) {
            return new QuoRem(new Polynomial(0), x);
        }
        
        //initialize quotient and remainder
        LinkedList<Integer> q = new LinkedList<>();
        //arraylist as we index this list often (which is slow in LinkedLists)
        ArrayList<Integer> r = x.asArrayList(); //LINE 1 ALGO
        r.add(0, 0); //LINE 2 ALGO;
        
        //loop over the high-order words that x has but y does not have + 1
        //E.g. x = 210; y = 40 -> loop over 2 and 1
        for(int i = 0; i < k - l + 1; i++) { //LINE 3 AlGO
            q.addLast((r.get(i)*b+r.get(i+1))/y.getLeadingTerm()); //LINE 4 ALGO
            if (q.getLast() >= b) { //LINE 5 ALGO
                q.removeLast();
                q.addLast(b-1);
            }

            int carry = 0; //LINE 6 ALGO
            
            //loop
            {
                //using an iterator to avoid the costly y.get(j)
                Iterator<Integer> yIt = y.descendingIterator();
                int j = l-1;
                
                //for (int j = l-1; j >= 0; j--) 
                while (yIt.hasNext()) { //LINE 7 ALGO
                    int jthElem = yIt.next(); //jthElem = y.get(j)
                    int tmp = r.get(i+j+1) - q.getLast()*jthElem + carry; //LINE 8 ALGO
                    carry = Math.floorDiv(tmp, b); //LINE 9 ALGO (QuoRem puts quotient in carry, remainder in r)
                    r.set(i+j+1, Math.floorMod(tmp, b)); //STILL LINE 9 ALGO
                    j--;
                }
            }
            r.set(i, r.get(i) + carry); //LINE 10 ALGO
            
            while (r.get(i) < 0) { //LINE 11 ALGO
                carry = 0; //LINE 12 ALGO
                
                //using an iterator to avoid the costly y.get(j)
                Iterator<Integer> yIt = y.descendingIterator();                
                int j = l - 1;
                
                //for (int j = l-1; j >= 0; j--)
                while (yIt.hasNext()) { //LINE 13 ALGO
                    int jthElem = yIt.next(); //jthElem = y.get(j)
                    int tmp = r.get(i+j+1) + jthElem + carry; //LINE 14 ALGO
                    carry = Math.floorDiv(tmp,b); //LINE 15 ALGO
                    r.set(i+j+1, Math.floorMod(tmp, b)); //STILL LINE 15 ALGO
                    j--;
                }
                r.set(i, r.get(i) + carry); //LINE 16 ALGO
                q.addLast(q.removeLast() - 1);
            }
        }
        //convert r back to a LinkedList (our expected format)
        Polynomial rPoly = new Polynomial(new LinkedList<>(r));
        Polynomial qPoly = new Polynomial(q);
        return new QuoRem(removeLeadingZeros(qPoly), removeLeadingZeros(rPoly)); //LINE 18 ALGO
    }   
    
    
    /**
     * Data Structure that stores a quotient and a remainder in one spot
     * q: Quotient
     * r: Remainder
     */
    public static class QuoRem {
        public Polynomial q;
        public Polynomial r;
        public QuoRem(Polynomial q, Polynomial r) {
            this.q = q; this.r = r;
        }
        
        @Override
        public String toString() {
            return "(" + q + "," + r + ")";
        }
    }
}

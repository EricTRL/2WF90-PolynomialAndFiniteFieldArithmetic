package nl.tue._2WF90.common;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 *
 * @since 6 SEPTEMBER 2018
 */
public class Multiplication {
    public static void main(String[] args) {
        Polynomial a = new Polynomial("1,8,3");
        Polynomial b = new Polynomial("1,5");
        System.out.println(multiply(a, b, 10, null));
    }
    
    public static LinkedList<Integer> multiply(Computation c) {
        return multiply(c.getX(), c.getY(), c.getRadix(), c);
    }
    
    /**
     * Multiply large numbers in radix b
     *
     * @param x Polynomial with coefficients of first polynomial
     * @param y Polynomial with coefficients of second polynomial
     * @param b radix to be used
     * @param computation Computation to increment [count-mul] and [count-add] of (or null if we don't care)
     * @pre x.length==y.length
     * @return x*y in radix b
     */
    public static Polynomial multiply (Polynomial a, Polynomial b) {
        Arithmetic.makeLengthsEqual(a, b);
        int zerosAdded = 0; //amount of zeros added at the end (increases by one every iteration of i)
        Polynomial answer = new Polynomial("0");
        Polynomial answerToAdd = new Polynomial("");
        //equal(x,y); //Make the numbers of the same size by adding 0's to the smaller number
        Iterator<Integer> it_b = b.descendingIterator();
        while (it_b.hasNext()) {
            int y = it_b.next();
            Iterator<Integer> it_a = a.descendingIterator();
            while (it_a.hasNext()) {
                int x = it_a.next();
                answerToAdd.addFirst((x*y)%10);
            }
            for (int z = zerosAdded; z > 0; z--) {
                answerToAdd.addLast(0);
            }
            
            zerosAdded++; //Increase zerosAdded counter for next iteration
            answer = Addition.add(answer, answerToAdd, b, computation); //add current result to total result
            answerToAdd = new Polynomial(""); //reset answerToAdd array
        }
        
        Arithmetic.removeLeadingZeros(answer);
        return answer;
    }

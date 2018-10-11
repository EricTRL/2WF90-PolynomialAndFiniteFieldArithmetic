package nl.tue._2WF90.common;
import java.util.LinkedList;
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
        Polynomial b = new Polynomial("1,5,4");
        System.out.println(multiply(a, b));
    }
    /* obsolete, almost certain
    public static LinkedList<Integer> multiply(Computation c) {
        return multiply(c.getX(), c.getY(), c.getRadix(), c);
    }
    */
    
    /**
     * Multiply large numbers in radix b
     *
     * @param a Polynomial with coefficients of first polynomial
     * @param b Polynomial with coefficients of second polynomial
     * @pre x.length==y.length
     * @return x*y in radix b
     */
    public static Polynomial multiply (Polynomial a, Polynomial b) {
        //Arithmetic.makeLengthsEqual(a, b);
        Polynomial answer = new Polynomial("0");
        Polynomial answerToAdd = new Polynomial("");
        //equal(x,y); //Make the numbers of the same size by adding 0's to the smaller number
        Iterator<Integer> it_b = b.descendingIterator();
        int i = b.getSize()-1;
        while (it_b.hasNext()) {
            int y = it_b.next();
            int j = a.getSize()-1;
            Iterator<Integer> it_a = a.descendingIterator();
            while (it_a.hasNext()) {
                int x = it_a.next();
                answerToAdd.set(i+j,(x*y)%10);
                j--;
            }
            i--;
            answer = PolyArithmetic.polyAdd(answer, answerToAdd, 10); //add current result to total result
            answerToAdd = new Polynomial(""); //reset answerToAdd array
        }
        
        Arithmetic.removeLeadingZeros(answer);
        return answer;
    }
}

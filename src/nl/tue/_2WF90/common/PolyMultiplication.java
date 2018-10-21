package nl.tue._2WF90.common;
import java.util.ArrayList;
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
public class PolyMultiplication {
    public static void main(String[] args) {
        Polynomial a = new Polynomial("1,1");
        Polynomial b = new Polynomial("1,-1");
        System.out.println(polyMultiply(a, b, 100000));
        
        System.out.println(a + "*" + b);
    }
    
    
    /**
     * Polynomial Multiplication (mod p)
     * @param c
     * @return c.f * c.g (mod c.mod)
     */
    public static Polynomial polyMultiply(Computation c) {
        return polyMultiply(c.getF(), c.getG(), c.getMod());
    }     
    
    /**
     * Multiplies two Polynomials a and b
     * @param a Polynomial
     * @param b Polynomial
     * @param p Modulus
     * @pre a != null && b != null
     * @return x*y (mod p)
     */
    public static Polynomial polyMultiply(Polynomial a, Polynomial b, int p) {
        int sizeA = a.getSize();
        int sizeB = b.getSize();
        
        Polynomial answer = new Polynomial();
        ArrayList<Integer> answerToAdd = new ArrayList<>(sizeA+sizeB-1);
        
        while (answerToAdd.size() < sizeA+sizeB-1) {
            answerToAdd.add(0);
        }
        
        Iterator<Integer> it_b = b.descendingIterator();
        int i = b.getSize()-1;
        while (it_b.hasNext()) {
            int y = it_b.next();
            int j = a.getSize()-1;
            Iterator<Integer> it_a = a.descendingIterator();
            while (it_a.hasNext()) {
                int x = it_a.next();
                answerToAdd.set(i+j,Math.floorMod((x*y), p));
                j--;
            }
            i--;
            answer = PolyArithmetic.polyAdd(answer, new Polynomial(answerToAdd), p); //add current result to total result
            answerToAdd = new ArrayList<>(sizeA+sizeB-1); //reset answerToAdd array
            while (answerToAdd.size() < sizeA+sizeB-1) {
                answerToAdd.add(0);
            }
        }
        
        return answer.removeZeros();
    }
}

package nl.tue._2WF90.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This class implements the irreducibility algorithms
 *
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 *
 * @since 27 SEPTEMBER 2018
 */
public class Irreducible {
    public static void main(String[] args) {
        testIrreducible(new Polynomial("{5, 2, 1, 0}"), 7);
    }

    /**
     * Method that returns whether a polynomial is irreducible or not
     * @param a polynomial we test for irreducibility
     * @param p modulo prime p
     * @return whether polynomial a is irreducible
     */
    public static boolean testIrreducible(Polynomial a, int p) {
        int t = 1;
        Polynomial X;
        do {
            ArrayList<Integer> coeff = new ArrayList<>(7);
            coeff.add(1,1); coeff.add(5,1); coeff.add(6,0);
            X = new Polynomial(coeff);
            System.out.println(X.toString());
        } while (new Polynomial("{1}").equals(X));
        if (t==a.getDegree()) return true;
        else return false;
    }

    /**
     * Finds an irreducible polynomial of degree n
     * @param n degree of the required polynomial
     * @return irreducible polynomial of degree n
     */
    public static Polynomial produceIrreducible(int n, int p) {
        Random r = new Random();
        Polynomial f = new Polynomial(0);
        do {
            LinkedList<Integer> coeff = new LinkedList<>();
            coeff.add(1); //Leading coefficient 1 for a monic polynomial of degree n.
            for (int i = 1; i < n; i++) {
                coeff.add(r.nextInt(p));
            }
            f = new Polynomial(coeff);
        } while (!testIrreducible(f,p));
        return f;
    }
}

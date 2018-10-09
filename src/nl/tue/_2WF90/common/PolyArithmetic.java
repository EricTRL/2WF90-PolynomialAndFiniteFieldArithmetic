package nl.tue._2WF90.common;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class for the Polynomial arithmetic operations
 *
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 *
 * @since 27 SEPTEMBER 2018
 */
public class PolyArithmetic {
    /**
     * Polynomial addition (mod p)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param p modulo p, with p a prime, p < (bound)
     * @return a+b(mod p)
     */
    public Polynomial polyAdd(Polynomial a, Polynomial b, int p) {
        Polynomial anew = new Polynomial(a.asArrayList());
        Polynomial bnew = new Polynomial(b.asArrayList());
        LinkedList<Integer> ans = new LinkedList<>();
        Iterator<Integer> ita = anew.descendingIterator(); Iterator<Integer> itb = bnew.descendingIterator();
        //We add leading zeros to make the addition easier
        if (anew.getDegree()>bnew.getDegree()) {
            bnew.addZeros(anew.getDegree()-bnew.getDegree());
        } else {
            anew.addZeros(bnew.getDegree()-anew.getDegree());
        }

        while (ita.hasNext() && itb.hasNext()) {
            ans.add((ita.next()+itb.next())%p);
        }
        return new Polynomial(ans);
    }

    /**
     * Polynomial subtraction (mod p)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param p modulo p, with p a prime, p < (bound)
     * @return a-b(mod p)
     */
    public Polynomial polySubtract(Polynomial a, Polynomial b, int p) {
        LinkedList<Integer> ans = new LinkedList<>();
        Polynomial an = new Polynomial(a.asArrayList());
        Polynomial bn = new Polynomial(b.asArrayList());
        Iterator<Integer> ita = an.descendingIterator(); Iterator<Integer> itb = bn.descendingIterator();
        //Add leading zeros to make subtraction a lot easier
        if (an.getDegree()>bn.getDegree()) {
            bn.addZeros(an.getDegree()-bn.getDegree());
        } else {
            an.addZeros(bn.getDegree()-an.getDegree());
        }
        while (ita.hasNext() && itb.hasNext()) {
            int c = (ita.next()-itb.next()%p);
            if (c<0) c = p-c;
            ans.add(c);
        }
        return new Polynomial(ans);
    }

}

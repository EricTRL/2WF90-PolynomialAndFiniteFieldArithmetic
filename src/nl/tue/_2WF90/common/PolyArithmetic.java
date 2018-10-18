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
    public static void main(String[] args) {
        Polynomial a = new Polynomial("{-6,3,0,6}");
        Polynomial b = new Polynomial("{0,5,1,-4}");
        System.out.println(polySubtract(a,b,7).toString());
    }

    /**
     * Polynomial addition (mod p)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param p modulo p, with p a prime, p < (bound)
     * @return a+b(mod p)
     */
    public static Polynomial polyAdd(Polynomial a, Polynomial b, int p) {
        Polynomial anew = new Polynomial(a.asArrayList());
        Polynomial bnew = new Polynomial(b.asArrayList());
        LinkedList<Integer> ans = new LinkedList<>();
        //We add leading zeros to make the addition easier
        if (anew.getDegree()>bnew.getDegree()) {
            bnew.addZeros(anew.getDegree()-bnew.getDegree());
        } else {
            anew.addZeros(bnew.getDegree()-anew.getDegree());
        }
        Iterator<Integer> ita = anew.iterator(); Iterator<Integer> itb = bnew.iterator();
        while (ita.hasNext() && itb.hasNext()) {
            int answer = Math.floorMod((ita.next()+itb.next()), p);
            ans.add(answer);
        }

        return new Polynomial(ans).removeZeros();
    }

    /**
     * Polynomial subtraction (mod p)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param p modulo p, with p a prime, p < (bound)
     * @return a-b(mod p)
     */
    public static Polynomial polySubtract(Polynomial a, Polynomial b, int p) {
        LinkedList<Integer> ans = new LinkedList<>();
        Polynomial an = new Polynomial(a.asArrayList());
        Polynomial bn = new Polynomial(b.asArrayList());
        //Add leading zeros to make subtraction a lot easier
        if (an.getDegree()>bn.getDegree()) {
            bn.addZeros(an.getDegree()-bn.getDegree());
        } else {
            an.addZeros(bn.getDegree()-an.getDegree());
        }
        Iterator<Integer> ita = an.iterator(); Iterator<Integer> itb = bn.iterator();
        while (ita.hasNext() && itb.hasNext()) {
            int c = Math.floorMod(ita.next()-itb.next(), p);
            ans.add(c);
        }
        return new Polynomial(ans).removeZeros();
    }

    public static boolean polyIsLessThan(Polynomial a, Polynomial b, int p){
        Arithmetic.removeLeadingZeros(a);
        Arithmetic.removeLeadingZeros(b);
        if(a.getSize() < b.getSize()){
            return true;
        }else if(a.getSize() > b.getSize()){
            return false;
        }else{
            for(int i = 0; i < a.getSize();i++){
                if(a.get(i) < b.get(i)){
                    return true;
                }else if(a.get(i) > b.get(i)){
                    return false;
                }
            }
        }
        return false;
    }
}

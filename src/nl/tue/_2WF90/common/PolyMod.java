package nl.tue._2WF90.common;

import java.util.Iterator;

/**
 * Class that implements the polynomial mod operation
 *
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 *
 * @since 27 SEPTEMBER 2018
 */
public class PolyMod {
    /**
     * Method that returns whether a(mod m)=b(mod m)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param m Polynomial m
     * @param p Integer mod
     * @return a(mod m)==b(mod m)
     */
    public static boolean congruentModulo(Polynomial a, Polynomial b, Polynomial m, int p) {
        Polynomial aMod = Division.modulo(a, m, p);
        Polynomial bMod = Division.modulo(b, m, p);
        Iterator<Integer> ita = aMod.iterator(); Iterator<Integer> itb = bMod.iterator();
        boolean equal = true;
        while (ita.hasNext() && itb.hasNext()) {
            if (ita.next()!=itb.next()) {
                equal = false;
                break;
            }
        }
        return equal;
    }

}

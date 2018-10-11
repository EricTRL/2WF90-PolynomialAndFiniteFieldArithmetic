package nl.tue._2WF90.common;
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
     * @return a(mod m)==b(mod m)
     */
    public boolean congruentModulo(Polynomial a, Polynomial b, Polynomial m) {
        return false;
    }

    /**
     * Method that returns a(mod m)
     * @param a Polynomial
     * @param m Polynomial
     * @return a(mod m)
     */
    public Polynomial polyMod(Polynomial a, Polynomial m) {
        return new Polynomial("{1,0}");
    }

}

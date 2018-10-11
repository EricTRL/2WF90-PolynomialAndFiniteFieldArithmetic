package nl.tue._2WF90.common;

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
public class Division {
    /**
     * Calculates x (mod m)
     * @param x polynomial to be reduced
     * @param m modulo polynomial
     * @param p prime modulo with p < (bound)
     * @return x (mod m) in Z/2Z
     */
    public static Polynomial modulo(Polynomial x, Polynomial m, int p) {
        return divide(x, m, p).r;
    }
    
    
    /**
     * Uses Long Division on Polynomials a and b, finding Polynomials q, r such 
     * that a = q*b + r
     * @param a Numerator Polynomial
     * @param b Denominator Polynomial
     * @param p Modulo, with p is prime and p < (bound)
     * @return q,r such that a = q*b + r
     */
    public static QuoRem divide(Polynomial a, Polynomial b, int p) {
        Polynomial q = new Polynomial();
        Polynomial r = a.copy();
        
        int degR = r.getDegree();
        int degB = b.getDegree();
        
        while (degR >= degB) {
            //Determine X^(degR-degB)
            Polynomial x = new Polynomial(1, degR - degB);      
            
            //Calculate lc(r)/lc(b)
            Polynomial coeff = new Polynomial(r.getLeadingCoefficient()/b.getLeadingCoefficient());
            
            q = PolyArithmetic.polyAdd(q, PolyMultiplication.polyMultiply(coeff, x, p), p);
            
            r = PolyArithmetic.polySubtract(r, PolyMultiplication.polyMultiply(PolyMultiplication.polyMultiply(coeff, x, p), b, p), p);
            
            degR = r.getDegree();
        }
        return new QuoRem(q, r);
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

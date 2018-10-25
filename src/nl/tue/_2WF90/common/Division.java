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
    
    public static void main(String args[]) {
        int mod = 2;
        Polynomial a = new Polynomial("1,0,0");
        Polynomial b = new Polynomial("1,1,1");
        System.out.println(a + "/" + b);
        System.out.println(divide(a, b, mod));
        
        /*
        for (int i = -mod+1; i < mod; i++) {
            int inverse = smallNumberModularInversion(i, mod);
            System.out.println(i + "*" + inverse + " = " + Math.floorMod((i*inverse), mod) + " (mod " + mod + ")");
        }
        */
    }
    
    
    /**
     * Calculates x (mod m)
     * @param x polynomial to be reduced
     * @param m modulo polynomial
     * @param p prime modulo with p < (bound)
     * @return x (mod m) in Z/pZ
     */
    public static Polynomial modulo(Polynomial x, Polynomial m, int p) {
        return divide(x, m, p).r;
    }
    
    /**
     * Uses Long Division on c.getF() and c.getG() to find Polynomials q, r such 
     * that c.getF() = q*c.getG() + r
     * @param c Computation
     * @return q,r such that
     */
    public static QuoRem divide(Computation c) {
        return divide(c.getF(), c.getG(), c.getMod());
    }
    
    /**
     * Uses Long Division on Polynomials a and b to find Polynomials q, r such 
     * that a = q*b + r
     * @param a Numerator Polynomial
     * @param b Denominator Polynomial
     * @param p Modulo, with p is prime and p < (bound)
     * @pre a != null && b != null && 0 <= p < (bound)
     * @return q,r such that a = q*b + r
     */
    public static QuoRem divide(Polynomial a, Polynomial b, int p) {
        if (b.isZeroPolynomial()) {
            System.err.println("ERROR: Polynomial b cannot be the Zero-polynomial!");
            return null;
        }
        
        Polynomial q = new Polynomial();
        Polynomial r = a.copy(p);
        
        int degR = r.getDegree();
        int degB = b.getDegree();
        
        while (degR >= degB) {
            //Determine X^(degR-degB)
            Polynomial x = new Polynomial(1, degR - degB);      
            
            //leading coefficients
            int leadR = r.getLeadingCoefficient();
            int leadB = b.getLeadingCoefficient();
            
            //Determine lc(r)/lc(b)
            int lcrDivlcb;
            
            if (Math.floorMod(leadR, leadB) == 0) {
                //we can divide without needing the inverse
                lcrDivlcb = leadR/leadB;
            } else {
                //we need the inverse to be able to divide (since we can't have
                //fractions)
                int leadB_inverse = smallNumberModularInversion(leadB, p);
                if (leadB_inverse == 0) {
                    //leadB has no inverse, so we cannot divide further
                    //this will not happen if p is prime (unless leadB = 0)
                    break;
                }
                //leadB has an inverse
                lcrDivlcb = Math.floorMod(leadR*leadB_inverse, p);
                
                /*
                This works since:
                lc(r)/lc(b) = lc(r)/lc(b)*1 (mod p)
                            = lc(r)/lc(b)*(lc(b)*inverse(lc(b))) (mod p)
                            = lc(r)*inverse(lc(b)) (mod p)
                */
            }
            
            Polynomial coeff = new Polynomial(lcrDivlcb);
            
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
    
    /**
     * Performs modular inversion on small numbers
     * @param x Number to find the inverse of
     * @param m Modulus
     * @pre Integer.MIN_VALUE <= x <= Integer.MAX_VALUE &&
     *      Integer.MIN_VALUE <= m <= Integer.MAX_VALUE
     * @return x^(-1) such that x*(x)^(-1) = 1, or 0 if such x^(-1) does not exist
     */
    public static int smallNumberModularInversion(int x, int m) {
        int x_prime = Math.floorMod(x, m);
        int m_prime = m;
        int x1 = 1;
        int x2 = 0;
        int x3, q, r;
        
        while (m_prime > 0) {
            q = Math.floorDiv(x_prime, m_prime);
            r = x_prime - q*m_prime;
            
            x_prime = m_prime;
            m_prime = r;            
            
            x3 = Math.floorMod(x1 - q*x2, m);
            x1 = x2;
            x2 = x3;
        }
        
        if (x_prime == 1) {
            return x1;
        }
        return 0;
    }
}

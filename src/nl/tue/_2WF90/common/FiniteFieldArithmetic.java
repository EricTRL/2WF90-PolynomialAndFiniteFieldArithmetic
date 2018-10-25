package nl.tue._2WF90.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * DESCRIPTION
 * 
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 * 
 * @since 23 OCTOBER 2018
 */
public class FiniteFieldArithmetic {
    
    public static void main(String[] args) {
        FiniteField f = new FiniteField(new Polynomial("1,0,1,1"), 2);
        System.out.println(f);
        
        for (Polynomial p : f.getElements()) {
            Polynomial q = new Polynomial(1);
            for (int i = 1; i < 8; i++) {
                q = multiply(q, p, f);
                System.out.print("q^"+ (i) +"= ");
                System.out.println(q);
            }
            System.out.println("---------");
            
        }
        
        Polynomial a = new Polynomial("{-6,3,0,6}");
        Polynomial b = new Polynomial("{0,5,1,-4}");
        //System.out.println(polySubtract(a,b,7).toString());
        
        
        System.out.println(powerize(new Polynomial("1,0"), f, 10));
        
        for (Polynomial p : f.getElements()) {
            System.out.println(isPrimitive(p, f));
        }
        Polynomial qq = new Polynomial("2,0");
        
        
    }
    
    
    /**
     * Finite Field addition (mod p)
     * @param c
     * @return c.f + c.g (mod c.mod)
     */
    public static Polynomial add(Computation c) {
        return add(c.getF(), c.getG(), new FiniteField(c));
    }
    
    /**
     * Finite Field addition (mod p)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param f Finite Field f
     * @return a+b(mod f.modPoly, mod f.mod)
     */
    public static Polynomial add(Polynomial a, Polynomial b, FiniteField f) {
        return Division.modulo(PolyArithmetic.polyAdd(a, b, f.getMod()), f.getModPoly(), f.getMod());
    }
    
    /**
     * Finite Field subtraction (mod p)
     * @param c
     * @return c.f - c.g (mod c.mod)
     */
    public static Polynomial subtract(Computation c) {
        return add(c.getF(), c.getG(), new FiniteField(c));
    }

    /**
     * Finite Field subtraction
     * @param a Polynomial a
     * @param b Polynomial b
     * @param f Finite Field f
     * @return a-b(mod f)
     */
    public static Polynomial subtract(Polynomial a, Polynomial b, FiniteField f) {
        return Division.modulo(PolyArithmetic.polySubtract(a, b, f.getMod()), f.getModPoly(), f.getMod());
    }

    /**
     * Finite Field multiplication (mod p)
     * @param c
     * @return c.f * c.g (mod c.mod)
     */
    public static Polynomial multiply(Computation c) {
        return multiply(c.getF(), c.getG(), new FiniteField(c));
    }

    /**
     * Finite Field Multiplication (mod p)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param f Finite Field f
     * @return a*b(mod f)
     */
    public static Polynomial multiply(Polynomial a, Polynomial b, FiniteField f) {
        return Division.modulo(PolyMultiplication.polyMultiply(a, b, f.getMod()), f.getModPoly(), f.getMod());
    }
    
    /**
     * 
     * @param a
     * @param f
     * @param exponent
     * @return 
     */
    public static Polynomial powerize(Polynomial a, FiniteField f, int exponent) {
        Polynomial q = new Polynomial(1);
        for (int i = 0; i < exponent; i++) {
            q = multiply(q, a, f);
        }
        return q;
    }
    
    
    /**
     * Finite Field division (mod p)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param f Finite Field f
     * @return a/b(mod f.modPoly, mod f.mod)
     */
    public static Polynomial divide(Polynomial a, Polynomial b, FiniteField f) {
        if (b.isZeroPolynomial()) {
            System.err.println("ERROR: Polynomial b cannot be the Zero-polynomial!");
            return null;
        }
        Polynomial aa = a.copy();
        while (aa.isLessThan(b)) {
            aa = PolyArithmetic.polyAdd(aa, f.getModPoly(), f.getMod());
        }
        return Division.modulo(Division.divide(aa, b, f.getMod()).q, f.getModPoly(), f.getMod());  
    }
    
    /**
     * Finite Field equality check (mod p)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param f Finite Field f
     * @return a(mod m)==b(mod m)
     */
    public static boolean congruentField(Polynomial a, Polynomial b, FiniteField f) {
        return PolyMod.congruentModulo(a, b, f.getModPoly(), f.getMod());
    }    
    
    /**
     * Displays a Finite Field element
     * @param a Polynomial a
     * @param f Finite Field f
     * @return a displayed as an element of f
     */
    public static Polynomial displayFieldElement(Polynomial a, FiniteField f) {
        return Division.divide(a, f.getModPoly(), f.getMod()).r;
    }   

    /**
     * Finds the inverse of a in the field f, if it exists
     * @param a Polynomial a
     * @param f Finite Field f
     * @return a^(-1) if it exists, otherwise -1 if it doesn't exist
     */
    public static Polynomial inverseField(Polynomial a, FiniteField f) {
        Euclid.Euclidean e = Euclid.euclid(a, f.getModPoly(), f.getMod());
        Polynomial gcd = e.gcd;
        Polynomial x = e.a; Polynomial y = e.b;
        return (gcd.getDegree()==0) ? x : null;
    }

    /**
     * 
     * @param a
     * @param f
     * @return 
     */
    public static boolean isPrimitive(Polynomial a, FiniteField f) {  
        Polynomial b = displayFieldElement(a.copy(), f);
        if (b.isZeroPolynomial()) {
            return false;
        }
        
        int i = 1;
        int q = f.getOrder();
        ArrayList<Integer> primeDivisors = Arithmetic.getPrimeDivisors(q - 1);
        int k = primeDivisors.size();
        
        while (i <= k &&
                !congruentField(powerize(b, f, (q-1)/primeDivisors.get(i-1)), new Polynomial(1), f)) {
            i++;
        }
        return i > k;
    }
    
    /**
     * 
     * @param f
     * @return 
     */
    public static Polynomial findPrimitive(FiniteField f) {
        Random r = new Random();
        LinkedList<Polynomial> elem = f.getElements();
        Polynomial randomPol = elem.get(r.nextInt(elem.size()));
        do {
            randomPol = elem.get(r.nextInt(elem.size()));
        } while (!isPrimitive(randomPol, f));
        
        return randomPol;
    }
    
    
}

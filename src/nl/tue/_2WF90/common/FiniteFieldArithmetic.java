/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue._2WF90.common;

/**
 *
 * @author s165318
 */
public class FiniteFieldArithmetic {
    
    
    public static void main(String[] args) {
        Polynomial a = new Polynomial("{1}");
        Polynomial b = new Polynomial("{1,0}");
        FiniteField f = new FiniteField(new Polynomial("{1,1,1}"), 2);
        System.out.println(add(a,b,f).toString());
        System.out.println(divide(a,b,f).toString());
    }
    
    
    
    /**
     * Finite Field addition (mod p)
     * @param c
     * @return c.f + c.g (mod c.mod)
     */
    public static Polynomial polyAdd(Computation c) {
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
     * Finite Field division (mod p)
     * @param a Polynomial a
     * @param b Polynomial b
     * @param f Finite Field f
     * @return a/b(mod f.modPoly, mod f.mod)
     */
    public static Polynomial divide(Polynomial a, Polynomial b, FiniteField f) {
        return Division.modulo(Division.divide(a, b, f.getMod()).r, f.getModPoly(), f.getMod());  
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
}

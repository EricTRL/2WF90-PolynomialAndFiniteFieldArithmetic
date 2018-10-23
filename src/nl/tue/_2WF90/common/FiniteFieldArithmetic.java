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
        Polynomial a = new Polynomial("{-6,3,0,6}");
        Polynomial b = new Polynomial("{0,5,1,-4}");
        FiniteField f = new FiniteField(new Polynomial("{1,3,0}"), 5);
        System.out.println(add(a,b,f).toString());
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
     * @return a+b(mod f)
     */
    public static Polynomial add(Polynomial a, Polynomial b, FiniteField f) {
        return Division.modulo(PolyArithmetic.polyAdd(a, b, f.getMod()), f.getModPoly(), f.getMod());  
    }
    
    
}

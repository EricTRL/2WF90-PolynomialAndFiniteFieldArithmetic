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
        System.out.println(Add(a,b,7).toString());
    }
    
    
    
    /**
     * Finite Field addition (mod p)
     * @param c
     * @return c.f + c.g (mod c.mod)
     */
    
    
}

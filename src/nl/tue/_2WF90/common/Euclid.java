package nl.tue._2WF90.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 *
 * @since 6 SEPTEMBER 2018
 */

//GreaterOrEqual doesnt work
public class Euclid {
    
    public static void main(String[] args) {
        
        Polynomial a = new Polynomial("1,1,1");
        Polynomial b = new Polynomial("2,-2");
        
        euclid(a,b,7,null); //call without using computation
        
    }
    
    public static Polynomial euclid(Polynomial a, Polynomial b, int mod) {
        return euclid(a, b, mod, null);
    }
    
    public static Polynomial euclid(Polynomial a, Polynomial b, int mod, Computation c) {
        //Duplicate a and b for output only
        ArrayList<Integer> inputA = new ArrayList<>(a.asArrayList());
        ArrayList<Integer> inputB = new ArrayList<>(b.asArrayList());
        
        //Declare variables
        Polynomial x = new Polynomial("1");
        Polynomial y = new Polynomial("0");
        Polynomial q= new Polynomial("0");;
        Polynomial r= new Polynomial("0");;
        Polynomial x_;
        Polynomial y_;
        Polynomial u = new Polynomial("0");
        Polynomial v = new Polynomial("1");
        Division.QuoRem Q;
        
        //Set this boolean to true if X and Y get swapped, such that a1 and b1 are correct
        Boolean switched = false;
        
        //Make sure x > y
        if(PolyArithmetic.polyIsLessThan(a,b,mod)){
            Polynomial dummy = x.copy();
            x = y.copy();
            y = dummy.copy();
            switched = true;
            System.out.println("SWITCHED!!!!!!");
        }
        ////////////////////////
        
        //Compute Euclids Algorithm
        while(checkNotZero(b)){
            print("X:", x);
            print("Y:", y);
            sleep(10);
            System.out.println("Before divide");
            print("A:", a);
            print("B:", b);
            System.out.println("M: " + mod);
            Q = Division.divide(a,b,mod);
            System.out.println("After divide");
            q = Q.q;
            r = Q.r;
            print("q:", q);
            print("r:", r);
            a = b;
            b = r;
            x_ = x;
            y_ = y;
            x = u;
            y = v;
            System.out.println("So far"); 
            u = PolyArithmetic.polySubtract(x_, PolyMultiplication.polyMultiply(q, u,mod),mod);
            v = PolyArithmetic.polySubtract(y_, PolyMultiplication.polyMultiply(q, v,mod),mod);
            System.out.println("So far2");
        }
        
        //Check if x and y were switched
        if(switched){
            x_ = x;//TODO change variables?
            x = y;
            y = x_;
        }
        
        //Remove leading zero's
        Arithmetic.removeLeadingZeros(x);
        Arithmetic.removeLeadingZeros(y);
        
        
        // set answers in computation instance
//        if (c != null) {
//            c.setAnswA(x);
//            c.setAnswB(y);
//            c.setAnswD(a);
//        }
        
        // gcd
        return a;
    }
    
    //Check if LinkedList is not zero
    public static boolean checkNotZero(Polynomial y){
        Arithmetic.removeLeadingZeros(y);
        List<Integer> yList = y.asArrayList();
        return !yList.isEmpty();
    }
    
    
    // print linked list as number
    // eg: "list: 215643"
    public static void print(String title, Polynomial list) {
        System.out.print(title + ": ");
        for(int i =0;i <list.getSize();i++){
            System.out.print(list.get(i));
        }
        System.out.println("");
    }
    
    private static void sleep(int time) {
        try{
            Thread.sleep(time);
        } catch(Exception e){
        }
    }
    
}

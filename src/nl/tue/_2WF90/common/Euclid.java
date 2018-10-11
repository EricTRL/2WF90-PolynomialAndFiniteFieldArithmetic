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
        LinkedList<Integer> aList = new LinkedList<>(Computation.stringToList("5896363941d32eccd5c"));
        LinkedList<Integer> bList = new LinkedList<>(Computation.stringToList("c7eb8a91fbad0d1c1f03"));
        
        Polynomial a = new Polynomial(aList);
        Polynomial b = new Polynomial(bList);
        
        euclid(a,b,null); //call without using computation
    }
    
    public static Polynomial euclid(Computation c) {
        return euclid(c.getX(), c.getY(), c);
    }
    
    public static Polynomial euclid(Polynomial a, Polynomial b, Computation c) {
        //Duplicate a and b for output only
        ArrayList<Integer> inputA = new ArrayList<>(a.asArrayList());
        ArrayList<Integer> inputB = new ArrayList<>(b.asArrayList());
        
        //Declare variables
        Polynomial x = new Polynomial(new LinkedList<>(Arrays.asList(0)));
        Polynomial y = new Polynomial(new LinkedList<>(Arrays.asList(0)));
        Polynomial q = new Polynomial(new LinkedList<>(Arrays.asList(0)));
        Polynomial r = new Polynomial(new LinkedList<>(Arrays.asList(0)));
        Polynomial x_ = new Polynomial(new LinkedList<>(Arrays.asList(1)));
        Polynomial y_ = new Polynomial(new LinkedList<>(Arrays.asList(0)));
        Polynomial u = new Polynomial(new LinkedList<>(Arrays.asList(0)));
        Polynomial v = new Polynomial(new LinkedList<>(Arrays.asList(1)));
        
        ////////////////TODO switch thang
        //Set this boolean to true if X and Y get swapped, such that a1 and b1 are correct
        Boolean switched = false;
        
        //Make sure x > y
        if(Arithmetic.isLessThan(a,b)){
            Polynomial dummy = x.copy();
            x = y.copy();
            y = dummy.copy();
            switched = true;
        }
        ////////////////////////
        
        //Compute Euclids Algorithm
        while(checkNotZero(b)){
            q = divide(a,b);
            r = remainder(a,b);
            a = b;
            b = r;
            x_ = x;
            y_ = y;
            x = u;
            y = v;
            u = subtract(x_, multiply(q, u));
            v = subtract(y_, multiply(q, v));
        }
        
        //Check if x and y were switched
        if(switched){
            a2 = a1;//TODO change variables?
            a1 = b1;
            b1 = a2;
        }
        
        //Remove leading zero's
        Arithmetic.removeLeadingZeros(x);
        Arithmetic.removeLeadingZeros(y);
   
        // set answers in computation instance
        if (c != null) {
            c.setAnswA(x);
            c.setAnswB(y);
            c.setAnswD(a);
        }
        
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
    public static void print(String title, LinkedList<Integer> list) {
        System.out.print(title + ": ");
        for(int i =0;i <list.size();i++){
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

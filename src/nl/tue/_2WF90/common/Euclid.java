package nl.tue._2WF90.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static nl.tue._2WF90.common.Division.smallNumberModularInversion;

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
        
        Polynomial a = new Polynomial("2,-2");
        Polynomial b = new Polynomial("1,1,1");
        
        euclid(a,b,7); //call without using computation
        
    }

    public static Euclidean euclid(Computation c) {
        return euclid(c.getF(), c.getG(), c.getMod());
    }
    
    public static Euclidean euclid(Polynomial a, Polynomial b, int mod) {


        //Duplicate gcd and b for output only
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
        if(a.isLessThan(b)){
            Polynomial dummy = a.copy();
            a = b.copy();
            b = dummy.copy();
            switched = true;
        }
        ////////////////////////
        
        //Compute Euclids Algorithm
        while(checkNotZero(b)){
            Q = Division.divide(a,b,mod);
            q = Q.q;
            r = Q.r;
            a = b;
            b = r;
            x_ = x;
            y_ = y;
            x = u;
            y = v; 
            u = PolyArithmetic.polySubtract(x_, PolyMultiplication.polyMultiply(q, u,mod),mod);
            v = PolyArithmetic.polySubtract(y_, PolyMultiplication.polyMultiply(q, v,mod),mod);
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
        Arithmetic.removeLeadingZeros(a);
        
        if(a.getLeadingCoefficient() != 0){
            int a_inverse = smallNumberModularInversion(a.getLeadingCoefficient(),mod);
            Polynomial inverse = new Polynomial(a_inverse);
            a = PolyMultiplication.polyMultiply(a,inverse,mod);
            x = PolyMultiplication.polyMultiply(x,inverse,mod);
            y = PolyMultiplication.polyMultiply(y,inverse,mod);
        }

        // gcd
        return new Euclidean(x,y,a);
    }
    
    public static class Euclidean {
        public Polynomial a;
        public Polynomial b;
        public Polynomial gcd;
        public Euclidean(Polynomial a, Polynomial b, Polynomial gcd) {
            this.a = a; this.b = b; this.gcd = gcd;
        }
        
        @Override
        public String toString() {
            return "(" + a + "," + b + "," + gcd + ")";
        }
    }
    
    //Check if LinkedList is not zero
    public static boolean checkNotZero(Polynomial y){
        Arithmetic.removeLeadingZeros(y);
        List<Integer> yList = y.asArrayList();
        if(yList.size() == 1 && yList.get(0)==0){
            return false;
        }else{
            return true;
        }
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

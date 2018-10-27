package nl.tue._2WF90.common;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import nl.tue._2WF90.io.InputReader;
import nl.tue._2WF90.io.OutputWriter;

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
public class Arithmetic {
    //Main method
    public static void main(String args[]) throws FileNotFoundException {
        //expects res/Input.txt to exist
        //a FileInputStream can also be passed as a parameter instead
        InputReader reader = new InputReader();
        
        //List of computations to make
        List<Computation> computations = new LinkedList<>();
        
        //while there are computations to read
        Computation c;
        while ((c = reader.getNextComputation()) != null) {
            //the computation's type (and modulus) signify what to compute
            if (!isPrime(c.getMod())) {
                System.err.println("WARNING: Modulus in Computation + " + c + " + is NOT prime. Got mod = " + c.getMod());
                c.setMsg("ERROR");
            } else if (c.isFiniteFieldArithmetic() && c.getModPoly().isZeroPolynomial()) {
                System.err.println("WARNING: ModPoly in Computation + " + c + " + cannot be the Zero-polynomial!");
                c.setMsg("ERROR");
            } else {
                switch (c.getType()) {
                    //Polynomial Arithmetic
                    case "[display-poly]":  c.setAnswer(c.getF().copy().simpleModulo(c.getMod()));
                                            break;
                    case "[add-poly]":      c.setAnswer(PolyArithmetic.polyAdd(c));
                                            break;
                    case "[subtract-poly]": c.setAnswer(PolyArithmetic.polySubtract(c));
                                            break;
                    case "[multiply-poly]": c.setAnswer(PolyMultiplication.polyMultiply(c));
                                            break;
                    case "[long-div-poly]": Division.QuoRem answ = Division.divide(c);
                                            if (answ == null) {
                                                c.setMsg("ERROR");
                                                break;
                                            }
                                            c.setQuotient(answ.q);
                                            c.setRemainder(answ.r);
                                            break;
                    case "[euclid-poly]":   Euclid.Euclidean eu = Euclid.euclid(c);
                                            c.setGCD(eu.gcd);
                                            c.setAnswA(eu.a);
                                            c.setAnswB(eu.b);
                                            break;
                    case "[equals-poly-mod]":   if (c.getH().isZeroPolynomial()) {
                                                    c.setMsg("ERROR");
                                                    break;
                                                }
                                                c.setMsg(PolyMod.congruentModulo(c) ? "TRUE" : "FALSE");
                                                break;
                    case "[irreducible]":   c.setMsg(Irreducible.testIrreducible(c.getF(), c.getMod()) ? "TRUE" : "FALSE");
                                            break;
                    case "[find-irred]":    c.setAnswer(Irreducible.produceIrreducible(c.getDeg(), c.getMod()));
                                            break;
                    //Finite Field Arithmetic
                    case "[add-table]":     c.setMsg(new FiniteField(c).generateAdditionTable().toString());
                                            break;      
                    case "[mult-table]":    c.setMsg(new FiniteField(c).generateMultiplicationTable().toString());
                                            break; 
                    case "[display-field]": c.setAnswer(FiniteFieldArithmetic.displayFieldElement(c.getA(), new FiniteField(c)));
                                            break;  
                    case "[add-field]":     c.setAnswer(FiniteFieldArithmetic.add(c.getA(), c.getB(), new FiniteField(c)));
                                            break;  
                    case "[subtract-field]": c.setAnswer(FiniteFieldArithmetic.subtract(c.getA(), c.getB(), new FiniteField(c)));
                                            break;  
                    case "[multiply-field]": c.setAnswer(FiniteFieldArithmetic.multiply(c.getA(), c.getB(), new FiniteField(c)));
                                            break;  
                    case "[inverse-field]": c.setAnswer(FiniteFieldArithmetic.inverseField(c.getA(), new FiniteField(c)));
                                            if (c.getAnswer() == null) {
                                                c.setMsg("ERROR");
                                            }
                                            break;  
                    case "[division-field]": c.setAnswer(FiniteFieldArithmetic.divide(c.getA(), c.getB(), new FiniteField(c)));
                                            if (c.getAnswer() == null) {
                                                c.setMsg("ERROR");
                                            }
                                            break;  
                    case "[equals-field]":  c.setMsg(FiniteFieldArithmetic.congruentField(c.getA(), c.getB(), new FiniteField(c)) ? "TRUE" : "FALSE");
                                            break;
                    case "[primitive]":     c.setMsg(FiniteFieldArithmetic.isPrimitive(c.getA(), new FiniteField(c)) ? "TRUE" : "FALSE");
                                            break;
                    case "[find-prim]":     c.setAnswer(FiniteFieldArithmetic.findPrimitive(new FiniteField(c)));
                                            break;                        
                    //Neither Polynomial nor Finite Field Arithmetic
                    default:            System.err.println("Invalid Computation-Type found: " + c.getType()); break;
                }
            }
            computations.add(c);
        }
        OutputWriter.writeOutput(computations); //writes to res/output.txt by default
    }
    
    /**
     * Method that returns whether a given, positive integer is prime
     * @param p A positive integer
     * @pre p >= 0
     * @return true iff p is prime
     */
    public static boolean isPrime(int p) {
        if (p <= 1) {
            return false;
        }
        
        if ((p & 1) == 0 && p != 2) {
            //divisible by 2
            return false;
        }
        
        for (int i = 3; i <= Math.sqrt(p); i+=2) {
            if ((p % i) == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Inefficient Algorithm to compute the prime divisors of a given number
     * @param i
     * @pre i >= 2
     * @return 
     */
    public static ArrayList<Integer> getPrimeDivisors(int i) {
        if (i <= 1) {
            return null;
        }
        
        ArrayList<Integer> l = new ArrayList<>();
        if ((i & 1) == 0) {
            //divisible by 2
            l.add(2);
        }
        
        
        for (int j = 3; j <= i; j+=2) {
            if ((i % j) == 0 && isPrime(j)) {
                //j divides i and j is prime
                l.add(j);
            }
        }
        return l;
    }
    
    /**
     * Removes all leading zeros from a given Polynomial
     * @param x The polynomial to remove the leading zeros from (if any)
     * @pre x != null
     * @modifies x
     * @post x.getHighestDegree() != 0 || x.getDegree() == 1
     * @return x
     */
    public static Polynomial removeLeadingZeros(Polynomial x) {
        while (x.getLeadingCoefficient() == 0 && x.hasDegree()) {
            x.removeLeadingCoefficient();
        }
        return x;
    }
}

package nl.tue._2WF90.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.util.Pair;

/**
 * DataType to store a Finite Field described by Z/pZ[X]/(f),
 * with 0 <= prime p < (bound) and (f) = an irreducible Polynomial
 * 
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 * 
 * @since 27 SEPTEMBER 2018
 */
public class FiniteField {
    
    
    public static void main(String args[]) {
        Polynomial modPoly = new Polynomial("{1,1,1}");
        
        int mod = 2;
        
        FiniteField f = new FiniteField(modPoly, mod);
        
        OperationTable ot = f.generateMultiplicationTable();
        //ot.toTableForm();
        OperationTable ot2 = f.generateAdditionTable();
        System.out.println(ot2.toTableForm());
    }
    
    
    private final Polynomial modPoly;
    private final int mod;
    private final LinkedList<Polynomial> elements;
    
    public FiniteField(Computation c) {
        this(c.getModPoly(), c.getMod());
    }
    
    public FiniteField(Polynomial modPoly, int mod) {
        this.modPoly = modPoly;
        this.mod = mod;
        this.elements = new LinkedList<>();
        determineElements(new LinkedList<>());
    }
    
    /*Basic Getters*/
    public int getMod(){
        return mod;
    }
    
    public Polynomial getModPoly() {
        return modPoly;
    }
    
    public LinkedList<Polynomial> getElements() {
        return elements;
    }
    
    /**
     * (Recursively) determines the Elements in the Field
     * @param curPoly Previously generated list. Initially empty
     * @pre curPoly != null;
     * @modifies curPoly, this.elements
     * @post    this.elements contains all Elements of Field
     *          Z/(this.mod)Z[X]/(this.modPoly)
     */
    private void determineElements(LinkedList<Integer> curPoly) {
        int deg = curPoly.size() - 1;
        if (deg >= modPoly.getDegree()) {
            return;
        }
        
        for (int i = 0; i < mod; i++) {
            curPoly.addLast(i);
            //System.out.println(curPoly + " | " + new Polynomial(new LinkedList<>(curPoly)));
            Polynomial poly = new Polynomial(new LinkedList<>(curPoly));
            
            if (!contains(poly)) {
                //System.out.println(poly.asArrayList());
                elements.addLast(poly); //create a copy to get rid of references/pointers
            }
            determineElements(curPoly);
            curPoly.removeLast();
        }
    }
    
    @Override
    public String toString() {
        return elements.toString();
    }
    
    /**
     * Checks whether the Field currently contains a Polynomial congruent to
     * a given Polynomial p
     * @param p Polynomial to check
     * @return  true iff this.elements contains a Polynomial q such that
     *          p = q (mod this.modPoly)
     */
    public boolean contains(Polynomial p) {
        for (Polynomial q : elements) {
            if (PolyMod.congruentModulo(p, q, modPoly, mod)) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Generates the Addition Table for this Field
     * @return An Addition Table for this Field
     */
    public OperationTable generateAdditionTable() {
        return new OperationTable("+", elements, mod, modPoly);
    }
    
    /**
     * Generates the Multiplication Table for this Field
     * @return A Multiplication Table of this Field
     */
    public OperationTable generateMultiplicationTable() {
        return new OperationTable("*", elements, mod, modPoly);
    }
    
    /**
     * DataType for Addition and Multiplication Tables
     * Provides methods to retrieve the outcome of key(operation)key2, where key and key2
     * are Polynomials that were initially given, and where operation is * (for
     * Multiplication) or + (for Addition)
     * Also provides two methods for displaying the table. The standard toString
     * displays in the expected format, whereas toTableForm returns the Table in
     * an actual nice and readable Table-form.
     */
    public static class OperationTable {
        public final String operation;
        private final LinkedList<Polynomial> keys;
        private final HashMap<Pair<Polynomial, Polynomial>, Polynomial> values;
        
        public OperationTable(String operation, LinkedList<Polynomial> keys, int mod, Polynomial modPoly) {
            this.operation = (operation.equals("*") ? "*" : "+");
            this.keys = new LinkedList<>(keys);
            this.values = new HashMap<>();
            
            for (Polynomial p : keys) {
                for (Polynomial q : keys) {
                    Pair<Polynomial, Polynomial> pq = new Pair(p, q);
                    Pair<Polynomial, Polynomial> qp = new Pair(q, p);
                    if (!values.containsKey(pq) && !values.containsKey(qp)) {
                        Polynomial result;
                        if (operation.equals("*")) { //Multiplication
                            result = PolyMultiplication.polyMultiply(p, q, mod);
                        } else { //Addition
                            result = PolyArithmetic.polyAdd(p, q, mod);
                        }
                        
                        result = Division.modulo(result, modPoly, mod);
                        values.put(pq, result);
                        values.put(qp, result);
                    }
                }     
            }
        }
        
        /** Gets a (this.operation) b
          * @param a Polynomial a
          * @param b Polynomial b
          * @pre keys.contains(a) && keys.contains(b)
          * @return a(operation)b, where operation = '+' or '*'
          *         null if precondition is not satisfied
          */
        public Polynomial get(Polynomial a, Polynomial b) {
            Pair<Polynomial, Polynomial> ab = new Pair<>(a, b);
            if (!values.containsKey(ab)) {
                System.err.println("WARNING: Expected Polynomial a and Polynomial b to be elements of the Field! Got a = <" + a + "> and b = <" + b + "> instead!");
                return null;
            }
            return values.get(ab);
        }
        
        /**
         * Retrieves an iterator over the table's keys
         * @return An Iterator over the table's keys
         */
        public Iterator keyIterator() {
            return keys.iterator();
        }
        
        @Override
        public String toString() {
            for (Pair<Polynomial, Polynomial> ab : values.keySet()) {
                //System.out.println("(" + ab.getKey() + ") " + operation + " (" + ab.getValue() + ") = " + values.get(ab));
            }
            return "";
        }
        
        /**
         * Outputs a String of this Table in a nice and readable Table-format.
         * @return  String of this Table in a Table-format
         */
        public String toTableForm() {
            StringBuilder s = new StringBuilder();
            StringBuilder separator = new StringBuilder();
            
            s.append(String.format("%9s ", operation));
            separator.append("-----------");
            for (Polynomial p : keys) {
                s.append(String.format("|%9s ", p));
                separator.append("-----------");
            }
            s.append("\n");
            
            for (Polynomial p : keys) {
                s.append(separator);
                s.append("\n");
                s.append(String.format("%9s ", p));
                for (Polynomial q : keys) {
                    s.append(String.format("|%9s ", get(p, q)));
                }
                s.append("\n");
            }

            return s.toString();
        }
    }
}

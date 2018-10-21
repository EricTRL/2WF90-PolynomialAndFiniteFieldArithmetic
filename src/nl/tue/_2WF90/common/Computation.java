package nl.tue._2WF90.common;

/**
 * Data structure that stores a computation. Includes things like the type
 * (E.g. display-poly, long-div-poly, etc.), the polynomials to operate on,
 * the answer (once computed), etc.
 * 
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 * 
 * @since 27 SEPTEMBER 2018
 */
public class Computation {
    private final String type;  //Name of the Operation. E.g. [poly-add]
    private final int mod;      //Modulus to apply the operation in
    
    private final Polynomial f; //Stores the first polynomial (if any) in case
                                //of Polynomial Arithmetic
                                //Stores [mod-poly] in case of Finite Field
                                //Arithmetic
    private final Polynomial g; //Stores the 2nd polynomial (if any) in case of
                                //Polynomial Arithmetic
                                //Stores [a] in case of Finite Field Arithmetic
    private final Polynomial h; //Stores the 3rd polynomial (if any) in case of
                                //Polynomial Arithmetic
                                //Stores [b] in case of Finite Field Arithmetic
    private final int deg;      //Stores the Degree to find in case of [find-irred]
                                //Unused otherwise
    private Polynomial answer;  //Stores the Answer of the operation;
                                //Stores the Quotient in case of [long-div-poly]
                                //Stores the GCD in case of [euclid-poly]
    private Polynomial answerR; //Stores the Remainder in case of [long-div-poly]
                                //Stores A in case of [euclid-poly]
    private Polynomial answerB; //Stores B in case of [euclid-poly]
                                //Unsed Otherwise
    private final boolean isPolynomialArithmetic;
       
    private String msg = "";    //if not empty, outputs this instead of the answer
                                //Used for [mult-table], [add-table], and in case
                                //of errors. I.e. non-prime modulus passed, or
                                //divisoin by 0
    
    //constructor
    public Computation(Polynomial f, Polynomial g, Polynomial h, String type, int mod, boolean polyArithmetic) {
        this(f, g, h, type, mod, polyArithmetic, -1);
    }
    
    public Computation(Polynomial f, Polynomial g, Polynomial h, String type, int mod, boolean polyArithmetic, int deg) {
        this.f = f;
        this.g = g;
        this.h = h;
        this.type = type;
        this.mod = mod;
        this.deg = deg;
        this.isPolynomialArithmetic = polyArithmetic;
        
        //safety check
        if (!type.equals("[INVALID]") && mod < 2) {
            System.err.println("ERROR: Invalid Mod! Should be >= 2, but got <" + mod + ">");
        }
        
        
    }
    
    /*Basic Getters*/
    public String getType() {
        return type;
    }
    
    public int getMod() {
        return mod;
    }
    
    public boolean hasMsg() {
        return !msg.equals("");
    }
    
    public String getMsg() {
        return msg;
    }
    
    public Polynomial getAnswer() {
        return answer;
    }
        
    public boolean isPolynomialArithmetic() {
        return isPolynomialArithmetic;
    }
    
    public boolean isFiniteFieldArithmetic() {
        return !isPolynomialArithmetic;
    }
    
    /*Basic Polynomial Arithmetic Getters*/
    public Polynomial getF() {
        return f;
    }
    
    public Polynomial getG() {
        return g;
    }
    
    public Polynomial getH() {
        return h;
    }
    
    public Polynomial getQuotient() {
        return answer;
    }
    
    public Polynomial getRemainder() {
        return answerR;
    }
    
    public int getDeg() {
        return deg;
    }
    
    public Polynomial getGCD() {
        return answer;
    }
    
    public Polynomial getAnswA() {
        return answerR;
    }
    
    public Polynomial getAnswB() {
        return answerB;
    }
    
    /*Basic Finite Field Getters*/
    public Polynomial getModPoly() {
        return f;
    }
    
    public Polynomial getA() {
        return g;
    }
    
    public Polynomial getB() {
        return h;
    }
    
    /*Basic Setters */
    public void setAnswer(Polynomial p) {
        answer = p;
    }
    
    public void setQuotient(Polynomial q) {
        answer = q;
    }
    
    public void setRemainder(Polynomial r) {
        answerR = r;
    }
    
    public void setGCD(Polynomial gcd) {
        answer = gcd;
    }
    
    public void setAnswA(Polynomial a) {
        answerR = a;
    }
    
    public void setAnswB(Polynomial b) {
        answerB = b;
    }
    
    public void setMsg(String s) {
        msg = s;
    }
    
    /*Other stuff*/
    @Override
    public String toString() {
        return "(" + type + " (mod " + mod + "), " + f + ", " + g + ", " + h + ")";
    }
}

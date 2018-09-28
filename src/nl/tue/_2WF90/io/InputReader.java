
package nl.tue._2WF90.io;

import arithmetic.Computation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import nl.tue._2WF90.common.Polynomial;

/**
 * Reads (and parses) the inputs from a given input stream (E.g. System.in)
 * 
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 * 
 * @since 27 SEPT 2018
 */
public class InputReader {
    
    //input directories
    protected static final File DEFAULT_INPUT_FILE_PATH = new File("res/");
    protected static final String DEFAULT_INPUT_FILE_NAME = "input.txt";
    protected static final File DEFAULT_INPUT_FILE = new File(DEFAULT_INPUT_FILE_PATH + "/" + DEFAULT_INPUT_FILE_NAME);
    
    //create neccesary folder structure
    static {
        DEFAULT_INPUT_FILE_PATH.mkdirs();
    }
    //Scanner that scans an individual line at once
    private final Scanner lineScanner;
    
    /**
     * Initializes an inputreader to read from a given InputStream
     * @param streamIn Stream to read from
     */
    public InputReader(InputStream streamIn) {
        this.lineScanner = new Scanner(streamIn);
    }
    
    /**
     * Initializes an inputreader to read from DEFAULT_INPUT_FILE
     * (res/input.txt by default)
     * @throws FileNotFoundException 
     */
    public InputReader() throws FileNotFoundException {
        this(new FileInputStream(DEFAULT_INPUT_FILE));
    }
    
    /**
     * Reads the data from the input File
     *
     * @return the data as a 'Computation', or null if the type of computation
     * was not given
     */
    public Computation getNextComputation() {        
        String type = null;
        boolean polyArithmetic = false;
        int mod = -1;
        int deg = -1;
        String f = "0";
        String g = "0";
        String h = "0";
        boolean EoF = true;
        
        //if true, empty lines will be skipped instead of separating computations
        //I.e. this allows multiple empty lines/comments to be before a computation
        boolean skipEmptyLines = true;
        
        //keep reading lines until there is nothingn to read anymore
        while(lineScanner.hasNextLine()) {
            String line = lineScanner.nextLine();
            
            //skip empty lines and comments if we're in skip-phase
            if (skipEmptyLines && (line.isEmpty() || line.charAt(0) == '#')) {
                continue;
            } else {
                //any data (other than a comment/empty line) was read, so we
                //have arrived at a computation
                skipEmptyLines = false;
            }

            //useful data was read, so empty lines separate computations now
            if (line.isEmpty()) {                
                //empty lines separate computations
                EoF = false;
                break;
            }
            
            //still skip comments
            if (line.charAt(0) == '#') {
                continue;
            }
            //scanner over an individual line (to break down words)
            Scanner sc = new Scanner(line);
            if (!sc.hasNext()) {
                System.err.println("An empty line was read -> This should never happen;");
                EoF = false;
                break;
            }
            
            //scan the next word (order of the words does not matter. E.g. y can
            //be before radix)
            String next = sc.next();
            switch (next) {
                case "[mod]":
                    mod = sc.nextInt();
                    break;
                //polynomial arithmetic
                case "[display-poly]": 
                case "[add-poly]": 
                case "[subtract-poly]":
                case "[multiply-poly]":
                case "[long-div-poly]":
                case "[euclid-poly]":
                case "[equals-poly-mod]":
                case "[irreducible]":
                case "[find-irred]":
                    polyArithmetic = true;
                    type = next;
                    break;
                //final-field-arithmetic
                case "[add-table]":    
                case "[mult-table]":  
                case "[display-field]":    
                case "[add-field]": 
                case "[subtract-field]":    
                case "[multiply-field]": 
                case "[inverse-field]": 
                case "[division-field]":    
                case "[equals-field]": 
                case "[primitive]": 
                case "[find-prim]": 
                    polyArithmetic = false;
                    type = next;
                    break;
                case "[f]":
                case "[mod-poly]":
                    f = sc.next();
                    break;
                case "[g]":
                case "[a]":
                    g = sc.next();
                    break;
                case "[h]":
                case "[b]":
                    h = sc.next();
                    break;
                case "[deg]":
                    deg = sc.nextInt();
                    break;
                case "[answer]":
                case "[count-add]":
                case "[count-mul]":
                case "[answ-a]":
                case "[answ-b]":
                case "[answ-d]":
                case "[answ-q]":
                case "[answ-r]":
                    //ignore these values
                    break;
                default: 
                    //any other value should not break the code (as they cannot
                    //occur), but this lets us know during testing (in case we
                    //make typos)
                    type = (type == null) ? "[INVALID]" : type;
                    System.err.println("Unexpected input read: " + next); break;
            }
        }
        //empty line was read (or EoF was reached) -> computation data read
        
        //if we reached the End of File, then there are no more computations        
        if (EoF && type == null) {
            return null;
        }
        
        //create the Computation with the data that was just read
        return new Computation(Polynomial.stringToPolynomial(f), Polynomial.stringToPolynomial(g), Polynomial.stringToPolynomial(h), type, mod, polyArithmetic, deg);
    }
}    

    
  

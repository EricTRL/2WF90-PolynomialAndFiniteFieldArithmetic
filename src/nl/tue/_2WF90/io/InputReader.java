
package nl.tue._2WF90.io;

import arithmetic.Computation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

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
        int radix = -1;
        String x = "1";
        String y = "1";
        String m = "[INVALID]";
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
                case "[radix]": radix = sc.nextInt(); break;
                case "[add]": 
                case "[subtract]": 
                case "[multiply]":
                case "[karatsuba]":
                case "[reduce]":
                case "[inverse]":
                case "[euclid]":
                    type = next;
                    break;
                case "[x]": x = sc.next(); break;
                case "[y]": y = sc.next(); break;
                case "[m]": m = sc.next(); break;
                case "[answer]":
                case "[count-add]":
                case "[count-mul]":
                case "[answ-a]":
                case "[answ-b]":
                case "[answ-d]":
                    //ignore these values
                    break;
                default: 
                    //any other value should not break the code (as they cannot
                    //occur), but this lets us know during testing
                    type = (type == null) ? "[INVALID]" : type;
                    System.err.println("Unexpected input read: " + next); break;
            }
        }
        //empty line was read (or EoF was reached) -> computation data read
        
        //if we reached the End of File, then there are no more computations        
        if (EoF && type == null) {
            return null;
        }
      
        if (type == null) {
            type = "[INVALID]";
        }
        
        //create the Computation with the data that was just read
        return new Computation(x, y, radix, type, m);
    }
}    

    
  

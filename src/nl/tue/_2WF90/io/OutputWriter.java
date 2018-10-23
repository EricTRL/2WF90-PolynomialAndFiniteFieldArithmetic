package nl.tue._2WF90.io;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tue._2WF90.common.Computation;

/**
 * Class related to writing outputs.
 *
 * @author E.M.A. Arts (1004076)
 * @author K. Degeling (1018025)
 * @author R.M. Jonker (1011291)
 * @author S. Jacobs (1005276)
 * @author M. Schotsman (0995661)
 *
 * @since 27 SEPTEMBER 2018
 */
public class OutputWriter {
    //input directories
    protected static final File DEFAULT_OUTPUT_FILE_PATH = new File("res/");
    protected static final String DEFAULT_OUTPUT_FILE_NAME = "output.txt";
    protected static final File DEFAULT_OUTPUT_FILE = new File(DEFAULT_OUTPUT_FILE_PATH + "/" + DEFAULT_OUTPUT_FILE_NAME);

    //fields that should be printed in the output, sorted by type of calculatio.
    private static final Map<String, List<String>> OUTPUT_FOR_CALCULATION;
    static {
        Map<String, List<String>> map = new HashMap<>();
        //mod: always printed
        //modPoly: printed when (see below)
        //type: always printed        
        //Polynomial Arithmetic
        map.put("f", new ArrayList<>(Arrays.asList("[display-poly]", "[add-poly]", "[subtract-poly]", "[multiply-poly]", "[long-div-poly]", "[euclid-poly]", "[equals-poly-mod]", "[irreducible]")));
        map.put("g", new ArrayList<>(Arrays.asList("[add-poly]", "[subtract-poly]", "[multiply-poly]", "[long-div-poly]", "[euclid-poly]", "[equals-poly-mod]")));
        map.put("h", new ArrayList<>(Arrays.asList("[equals-poly-mod]")));
        map.put("deg", new ArrayList<>(Arrays.asList("[find-irred]")));
        
        map.put("answ-q", new ArrayList<>(Arrays.asList("[long-div-poly]")));
        map.put("answ-r", new ArrayList<>(Arrays.asList("[long-div-poly]")));
        map.put("answ-a", new ArrayList<>(Arrays.asList("[euclid-poly]")));
        map.put("answ-b", new ArrayList<>(Arrays.asList("[euclid-poly]")));
        map.put("answ-d", new ArrayList<>(Arrays.asList("[euclid-poly]")));
        
        //Finite Field Arithmetic
        map.put("mod-poly", new ArrayList<>(Arrays.asList("[add-table]", "[mult-table]", "[display-field]", "[add-field]", "[subtract-field]", "[multiply-field]", "[inverse-field]", "[division-field]", "[equals-field]", "[primitive]", "[find-prim]")));
        map.put("a", new ArrayList<>(Arrays.asList("[display-field]", "[add-field]", "[subtract-field]", "[multiply-field]", "[inverse-field]", "[division-field]", "[equals-field]", "[primitive]")));
        map.put("b", new ArrayList<>(Arrays.asList("[add-field]", "[subtract-field]", "[multiply-field]", "[division-field]", "[equals-field]")));
       
        //Any Arithmetic
        map.put("answer", new ArrayList<>(Arrays.asList("[display-poly]", "[add-poly]", "[subtract-poly]", "[multiply-poly]", "[equals-poly-mod]", "[irreducible]", "[find-irred]",
                                                        "[add-table]", "[mult-table]", "[display-field]", "[add-field]", "[subtract-field]", "[multiply-field]", "[inverse-field]", "[division-field]", "[equals-field]", "[primitive]", "[find-prim]")));
        OUTPUT_FOR_CALCULATION = Collections.unmodifiableMap(map);
    }

    /**
     * Method which will print the output to the DEFAULT_OUTPUT_FILE
     * (res/output.txt by default)
     *
     * @param computations A list of computations to print
     */
    public static void writeOutput(List<Computation> computations) {
        DEFAULT_OUTPUT_FILE_PATH.mkdirs();
        try (FileOutputStream streamOut = new FileOutputStream(DEFAULT_OUTPUT_FILE);) {
            writeOutput(streamOut, computations);
        } catch (FileNotFoundException e) {
            //hide error messages
            System.err.println("Could not find file");
        } catch (IOException e) {
            System.err.println("Could not read file");
        }
    }

    /**
     * Method which will print the output to a given OutputStream
     *
     * @param streamOut PrintStream of where to print (E.g. System.out)
     * @param computations A list of computations to print
     */
    public static void writeOutput(OutputStream streamOut, List<Computation> computations) {
        //do nothing if there is nothing to print
        if (computations == null) {
            return;
        }

        try (
                BufferedWriter fileWriter =
                        new BufferedWriter(new OutputStreamWriter(streamOut, "UTF-8"));
        ) {
            //print the output for each Computation
            for (Computation computation : computations) {
                writeOutput(fileWriter, computation);
            }
        } catch (FileNotFoundException e) {
            //hide error messages
            System.err.println("Could not find file");
        } catch (IOException e) {
            System.err.println("Could not read file");
        }
    }

    /**
     * Writes the output for a single c
     * @param fileWriter Writer that writes the output
     * @param c Computation to write the output for
     * @throws IOException When the filewriter cannot write
     */
    private static void writeOutput(BufferedWriter fileWriter, Computation c) throws IOException {
        //skip invalid computations
        if (c.getType().equals("[INVALID]")) {
            fileWriter.write("[INVALID COMPUTATION]"); fileWriter.newLine();
            fileWriter.newLine();
            return;
        }

        //always output [mod]
        fileWriter.write("[mod] " + c.getMod()); fileWriter.newLine();
        String type = c.getType();
        
        if (OUTPUT_FOR_CALCULATION.get("mod-poly").contains(type)) {
            fileWriter.write("[mod-poly] " + c.getModPoly().toInputString()); fileWriter.newLine();
        }
          
        //always output [type]
        fileWriter.write(type); fileWriter.newLine();

        //Polynomial Arithmetic
        if (OUTPUT_FOR_CALCULATION.get("f").contains(type)) {
            fileWriter.write("[f] " + c.getF().toInputString()); fileWriter.newLine();
        }
        if (OUTPUT_FOR_CALCULATION.get("g").contains(type)) {
            fileWriter.write("[g] " + c.getG().toInputString()); fileWriter.newLine();
        }
        if (OUTPUT_FOR_CALCULATION.get("h").contains(type)) {
            fileWriter.write("[h] " + c.getH().toInputString()); fileWriter.newLine();
        }
        if (OUTPUT_FOR_CALCULATION.get("deg").contains(type)) {
            fileWriter.write("[deg] " + c.getDeg()); fileWriter.newLine();
        }
        if (OUTPUT_FOR_CALCULATION.get("answ-q").contains(type)) {
            fileWriter.write("[answ-q] " + (c.hasMsg() ? c.getMsg() : c.getQuotient())); fileWriter.newLine();
        }
        if (OUTPUT_FOR_CALCULATION.get("answ-r").contains(type)) {
            fileWriter.write("[answ-r] " + (c.hasMsg() ? c.getMsg() : c.getRemainder())); fileWriter.newLine();
        }
        if (OUTPUT_FOR_CALCULATION.get("answ-a").contains(type)) {
            fileWriter.write("[answ-a] " + (c.hasMsg() ? c.getMsg() : c.getAnswA())); fileWriter.newLine();
        }
        if (OUTPUT_FOR_CALCULATION.get("answ-b").contains(type)) {
            fileWriter.write("[answ-b] " + (c.hasMsg() ? c.getMsg() : c.getAnswB())); fileWriter.newLine();
        }
        if (OUTPUT_FOR_CALCULATION.get("answ-d").contains(type)) {
            fileWriter.write("[answ-d] " + (c.hasMsg() ? c.getMsg() : c.getGCD())); fileWriter.newLine();
        }
        
        //Finite Field Arithmetic
        if (OUTPUT_FOR_CALCULATION.get("a").contains(type)) {
            fileWriter.write("[a] " + c.getA().toInputString()); fileWriter.newLine();
        }
        if (OUTPUT_FOR_CALCULATION.get("b").contains(type)) {
            fileWriter.write("[b] " + c.getB().toInputString()); fileWriter.newLine();
        }
        
        //Answer
        if (OUTPUT_FOR_CALCULATION.get("answer").contains(type)) {
            fileWriter.write("[answer] " + (c.hasMsg() ? c.getMsg() : c.getAnswer())); fileWriter.newLine();
        }
        
        fileWriter.newLine();
    }
}

package nl.tue._2WF90.common;

import java.util.LinkedList;

/**
 * DESCRIPTION
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
    
    private final Polynomial modPoly;
    private final int mod;
    private final LinkedList<Polynomial> elements;
    
    public FiniteField(Polynomial modPoly, int mod) {
        this.modPoly = modPoly;
        this.mod = mod;
        this.elements = new LinkedList<>();
        determineElements(new LinkedList<>());
    }
    
    private void determineElements(LinkedList<Integer> curPoly) {
        int deg = curPoly.size() - 1;
        if (deg >= modPoly.getDegree()) {
            return;
        }
        
        for (int i = 0; i < mod; i++) {
            curPoly.addFirst(i);
            //System.out.println(curPoly + " | " + new Polynomial(new LinkedList<>(curPoly)));
            Polynomial poly = new Polynomial(new LinkedList<>(curPoly));
            if (!contains(poly)) {
                elements.addLast(poly); //create a copy to get rid of references/pointers
            }
            determineElements(curPoly);
            curPoly.removeFirst();
        }
    }
    
    @Override
    public String toString() {
        return elements.toString();
    }
    
    public static void main(String args[]) {
        Polynomial modPoly = new Polynomial("{1,1,1}");
        
        System.out.println(new FiniteField(modPoly, 2).getElements());
        
    }
    
    public boolean contains(Polynomial p) {
        for (Polynomial q : elements) {
            if (p.isEqual(q)) {
                return true;
            }
        }
        return false;
    }
    

    
    public int getMod(){
        return mod;
    }
    
    public Polynomial getModPoly() {
        return modPoly;
    }
    
    public LinkedList<Polynomial> getElements() {
        return elements;
    }
    
    
    
    
}

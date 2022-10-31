package semantics;

import java.util.Set;

import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import parser.AFDataStructures;

public class Complete extends Semantics{

    protected Admissibility admissibility;

    public Complete(AFDataStructures structures){
        super(structures);
        latexGenericFormula = ""; //TODO
        
        admissibility = new Admissibility(structures);
        latexFormulaHeader = ""; //TODO
    }



    
    public IVec<IVecInt> calculateReduction() {
        // TODO
        return null;
    }

    @Override
    public Set<Set<String>> getExtensions() throws Exception {
        // TODO
        return null;
    }
    
}

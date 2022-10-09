package semantics;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import parser.AFDataStructures;
import parser.ParserException;

public abstract class Semantics{

    protected IVec<IVecInt> attacks;
    protected Map<Integer, String> arguments; 
    protected IVec<IVecInt> clauses;
    protected String latexGenericFormula;
    protected String latexFormulaHeader;
    protected String latexFormulaBody;
   
    public Semantics(AFDataStructures structures){
        attacks = structures.attacks;
        arguments = structures.argumentsByInteger;
        clauses = new Vec<IVecInt>();
        latexGenericFormula = "";
        latexFormulaBody = "";
        latexFormulaHeader = "";
    }

    public abstract IVec<IVecInt> calculateReduction();

    public String getLatexFormulaBody(){
        return latexFormulaBody;
    }

    public String getLatexFullFormula(){
        return latexGenericFormula + " \\\\ " + latexFormulaHeader + latexFormulaBody;
    }
}
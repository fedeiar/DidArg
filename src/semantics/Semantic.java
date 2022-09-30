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

public abstract class Semantic{

    protected IVec<IVecInt> attacks;
    protected Map<Integer, String> arguments; 
    protected IVec<IVecInt> clauses;
    protected String latexFormula;

   
    public Semantic(AFDataStructures structures){
        attacks = structures.attacks;
        arguments = structures.argumentsByInteger;
        clauses = new Vec<IVecInt>();
        latexFormula = "";

        //TODO: esta bien esto? ya que es la unica forma de que se tenga en cuenta cada variable por su cuenta sin pertenecer a una clausula.
        /* for(Entry<Integer, String> argument : arguments.entrySet()){
            int[] array = {argument.getKey(), argument.getKey() * -1};
            clauses.push(new VecInt(array));
        } */
    }

    public abstract IVec<IVecInt> calculateReduction() throws IOException, ParserException;

    public String getLatexFormula(){
        return latexFormula;
    }
}
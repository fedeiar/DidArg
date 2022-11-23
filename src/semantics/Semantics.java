package semantics;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.sat4j.core.Vec;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import parser.AFDataStructures;
import util.Utils;

public abstract class Semantics{

    protected IVec<IVecInt> attacks;
    protected Map<Integer, String> arguments; 
    public IVec<IVecInt> clauses;
    protected String latexGenericFormula;
    protected String latexArguments;
    protected String latexAttacks;
    protected String latexFormulaHeader;
    protected String latexFormulaBody;

    protected String explanation;
   
    public Semantics(AFDataStructures structures){
        attacks = structures.attacks;
        arguments = structures.argumentsByInteger;
        clauses = new Vec<IVecInt>();
        latexGenericFormula = "";
        buildLatexArguments();
        buildLatexAttacks();
        latexFormulaHeader = "";
        latexFormulaBody = "";
    }

    private void buildLatexArguments(){
        latexArguments = "Ar = \\{";
        for(Entry<Integer, String> argument : arguments.entrySet()){
            latexArguments += argument.getValue()+", ";
        }
        latexArguments = Utils.removeLastOperatorFromLatexFormula(latexArguments, 2);
        latexArguments += "\\}";
    }

    private void buildLatexAttacks(){
        latexAttacks = "att = \\{";
        for(int i = 0; i < attacks.size(); i++){
            IVecInt attack = attacks.get(i);
            latexAttacks += "("+ arguments.get(attack.get(0)) +", "+ arguments.get(attack.get(1)) +"), ";
        }
        latexAttacks = Utils.removeLastOperatorFromLatexFormula(latexAttacks, 2);
        latexAttacks += "\\}";
    }

    public abstract IVec<IVecInt> calculateReduction();

    public abstract Set<Set<String>> getExtensions() throws Exception;

    public String getLatexFormulaHeader(){
        return latexFormulaHeader;
    }

    public String getLatexFormulaBody(){
        return latexFormulaBody;
    }

    public String getLatexFullFormula(){
        return latexGenericFormula + " \\\\ " + latexArguments + " \\\\ " + latexAttacks + "  \\\\ " + latexFormulaHeader + latexFormulaBody;
    }

    public String getExplanation(){
        return explanation;
    }
}
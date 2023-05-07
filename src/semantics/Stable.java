package semantics;

import java.util.Set;
import java.util.Map.Entry;

import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import extensions.ExtensionEnumerator;
import parser.AFDataStructures;
import util.Utils;

public class Stable extends Semantics{
    
    protected ConflictFreenes conflictFreenes;

    public Stable(AFDataStructures structures){
        super(structures);
        conflictFreenes = new ConflictFreenes(structures);

        latexGenericFormula = "st_{Ar, att} := \\underset{a \\in Ar}{\\bigwedge} \\left( (v_a \\rightarrow \\underset{(b, a) \\in att}{\\bigwedge} \\neg v_b) \\wedge (\\neg v_a \\rightarrow \\underset{(b, a) \\in att}{\\bigvee} v_b) \\right)";
        latexFormulaHeader = "st_{Ar, att} := ";

        explanation = "In adittion to having to be conflict-free, every argument that doesn't belong to the extension must be attacked at least by one argument from the extension. The first part of the formula is the same as the one for conflict-freenes. The other part encodes that if an argument 'a' is not accepted, then at least one attacker of 'a' must be accepted.";
    }

    //TODO: esta bien?
    public IVec<IVecInt> calculateReduction(){
        clauses = conflictFreenes.calculateReduction();
        latexFormulaBody = conflictFreenes.getLatexFormulaBody();

        if(arguments.size() == 0){
            return clauses;
        }

        latexFormulaBody += "\\land \\\\"; // Add the "and" and newline that was erased in ConflictFreenes
        VecInt clause;
        boolean argumentIsAttacked;
        
        for(Entry<Integer, String> argument : arguments.entrySet()){
            latexFormulaBody += "(\\neg v_"+argument.getValue()+" \\rightarrow ( ";
            argumentIsAttacked = false;
            clause = new VecInt();
            clause.push(argument.getKey()); // The argument is always in the clause.
            for(int i = 0; i < attacks.size(); i++){ // Look for attackers of the argument.
                IVecInt attack1 = attacks.get(i);
                if(attack1.get(1) == argument.getKey()){ // This means that the argument is attacked.
                    argumentIsAttacked = true;
                    latexFormulaBody += "v_"+arguments.get(attack1.get(0))+" \\lor ";
                    clause.push(attack1.get(0)); // We simply add the attacker to the clause.
                }
            }
            if(!argumentIsAttacked){ // If the argument isn't attacked by anyone, then it must be true (can't be false)
                latexFormulaBody += "\\bot ";
            } else{
                latexFormulaBody = Utils.removeLastOperatorFromLatexFormula(latexFormulaBody, 5); // Delete the last "or" added when we were recognizing attacks.
            }
            latexFormulaBody += ") ) \\ \\land \\\\ ";

            clauses.push(clause);
        }

        latexFormulaBody = Utils.removeLastOperatorFromLatexFormula(latexFormulaBody, 9); // Remove the last "and" when we were adding clauses.

        return clauses;
    }

    public Set<Set<String>> getExtensions() throws Exception{

        IVec<IVecInt> clauses = this.calculateReduction();
        ExtensionEnumerator extensionEnumerator = new ExtensionEnumerator(arguments, clauses);
        Set<Set<String>> extensions = extensionEnumerator.getExtensions();

        return extensions;
    }
}

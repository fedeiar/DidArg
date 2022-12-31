package semantics;

import java.util.Set;
import java.util.Map.Entry;

import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import extensions.ExtensionEnumerator;
import parser.AFDataStructures;
import util.Utils;

public class Admissibility extends Semantics{

    protected ConflictFreenes conflictFreenes;

    public Admissibility(AFDataStructures structures){
        super(structures);
        conflictFreenes = new ConflictFreenes(structures);

        latexGenericFormula = "adm_{Ar, att} := \\underset{a \\in Ar}{\\bigwedge} \\left( ( v_a \\rightarrow \\underset{(b, a) \\in att}{\\bigwedge} \\neg v_b) \\land (v_a \\rightarrow \\underset{(b, a) \\in att}{\\bigwedge} (\\underset{(c, b) \\in att}{\\bigvee} v_c))  \\right)";
        latexFormulaHeader = "adm_{Ar, att} := ";

        explanation = "In adittion to having to be conflict-free, an admissible set must also defend every argument it has from every attack that any of its arguments recieves. The first part of the formula is the same as the one for conflict-freenes, the other part encodes that if an argument 'a' is accepted, then for all of its attackers 'b', at least one argument 'c' that attacks 'b' must be accepted.";
    }

    //TODO: esta bien?
    public IVec<IVecInt> calculateReduction(){
        clauses = conflictFreenes.calculateReduction();
        latexFormulaBody = conflictFreenes.getLatexFormulaBody();
        latexFormulaBody += "\\land \\\\"; // Add the "and" and newline that was erased in ConflictFreenes
        VecInt clause;
        boolean argumentIsAttacked;
        boolean argumentIsDefendedFromAttack;
        
        for(Entry<Integer, String> argument : arguments.entrySet()){
            latexFormulaBody += "(v_"+argument.getValue()+" \\rightarrow ( ";
            argumentIsAttacked = false;
            for(int i = 0; i < attacks.size(); i++){ // Look for attackers of the argument.
                IVecInt attack1 = attacks.get(i);
                if(attack1.get(1) == argument.getKey()){ // This means that the argument is attacked.
                    latexFormulaBody += "( ";
                    clause = new VecInt();
                    clause.push(argument.getKey() * -1); // We add to the argument negated by the equivalence rule with the implication.
                    argumentIsAttacked = true;
                    int attackerOfArgument = attack1.get(0);
                    argumentIsDefendedFromAttack = false;
                    for(int j = 0; j < attacks.size(); j++){ // Look for defenders of the argument.
                        IVecInt attack2 = attacks.get(j);
                        if(attack2.get(1) == attackerOfArgument){ // It means that the argument is defended from that attack by attack2.get(0).
                            argumentIsDefendedFromAttack = true;
                            clause.push(attack2.get(0)); // Add the defender of the argument
                            latexFormulaBody += "v_"+arguments.get(attack2.get(0))+" \\lor ";
                        }
                    }
                    if(!argumentIsDefendedFromAttack){
                        latexFormulaBody += "\\bot ";
                    } else{
                        latexFormulaBody = Utils.removeLastOperatorFromLatexFormula(latexFormulaBody, 5); // Delete the last added "or" when we were recognizing defenders. 
                    }
                    latexFormulaBody += ") \\land ";
                    clauses.push(clause); // We have a clause for an attacker.
                }
            }
            if(!argumentIsAttacked){ // If the argument isn't attacked by anyone, then it can be assigned any truth value.
                clause = new VecInt();
                clause.push(argument.getKey() * -1);
                clause.push(argument.getKey());
                clauses.push(clause);
                latexFormulaBody += "\\top ";
            } else{
                latexFormulaBody = Utils.removeLastOperatorFromLatexFormula(latexFormulaBody, 6); // Delete the last "and" added when we were recognizing attacks.
            }
            latexFormulaBody += ") ) \\ \\land \\\\ ";
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

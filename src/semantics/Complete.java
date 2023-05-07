package semantics;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import extensions.ExtensionEnumerator;
import parser.AFDataStructures;
import util.Utils;

public class Complete extends Semantics {

    protected ConflictFreenes conflictFreenes;

    public Complete(AFDataStructures structures) {
        super(structures);
        conflictFreenes = new ConflictFreenes(structures);

        latexGenericFormula = "comp_{Ar, att} := \\underset{a \\in Ar}{\\bigwedge} \\left( ( v_a \\rightarrow \\underset{(b, a) \\in att}{\\bigwedge} \\neg v_b) \\land (v_a \\leftrightarrow \\underset{(b, a) \\in att}{\\bigwedge} (\\underset{(c, b) \\in att}{\\bigvee} v_c))  \\right)";
        latexFormulaHeader = "comp_{Ar, att} := ";
        
        explanation = "In adittion to having to be admissible, a complete extension must also include every argument it defends. The formula is very similar to the admissibility one, but while the 'admissibility formula' allows us to accept an argument if it is defended by the extension (\"implication\" relation in the formula), the 'complete formula' forces us to accept an argument if it is defended by the extension (\"if and only if\" relation in the formula).";
    }

    //TODO: esta bien? la reduccion que calcula es a la fbf admisible, pero tuve que copiar y pegar para cambiar el rightarrow.
    public IVec<IVecInt> calculateReduction() {
        clauses = conflictFreenes.calculateReduction();
        latexFormulaBody = conflictFreenes.getLatexFormulaBody();

        if(arguments.size() == 0){
            return clauses;
        }
        
        latexFormulaBody += "\\land \\\\"; // Add the "and" and newline that was erased in ConflictFreenes
        VecInt clause;
        boolean argumentIsAttacked;
        boolean argumentIsDefendedFromAttack;
        
        for(Entry<Integer, String> argument : arguments.entrySet()){
            latexFormulaBody += "(v_"+argument.getValue()+" \\leftrightarrow ( ";
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

    // TODO: esta bien? o es muy ineficiente?
    public Set<Set<String>> getExtensions() throws Exception {
        IVec<IVecInt> clauses = this.calculateReduction();
        ExtensionEnumerator extensionEnumerator = new ExtensionEnumerator(arguments, clauses);
        Set<Set<String>> admissibleExtensions = extensionEnumerator.getExtensions();

        Set<Set<String>> completeExtensions = new HashSet<Set<String>>();
        boolean isCompleteExtension;
        for(Set<String> extension : admissibleExtensions){
            isCompleteExtension = true;
            for(Entry<Integer, String> argument : arguments.entrySet()){
                if (!extension.contains(argument.getValue()) && isDefendedByExtension(argument, extension)) {
                    isCompleteExtension = false;
                    break;
                }
            }
            if(isCompleteExtension){
                completeExtensions.add(extension);
            }
        }

        return completeExtensions;
    }

    private boolean isDefendedByExtension(Entry<Integer, String> argument, Set<String> extension){
        boolean isDefendedByExtension = true;

        for(int i = 0; i < attacks.size(); i++){
            IVecInt attack1 = attacks.get(i);
            if(attack1.get(1) == argument.getKey()){ // If the argument is attacked, check if the extension defends it.
                int attacker = attack1.get(0);
                boolean defendedFromThisAttack = false;
                for(int j = 0; j < attacks.size(); j++){
                    IVecInt attack2 = attacks.get(j);
                    if(attack2.get(1) == attacker && extension.contains(arguments.get(attack2.get(0))) ){ // If any argument from the extension attacks the attacker, then it defends the argument from that attack.
                        defendedFromThisAttack = true;
                        break;
                    }
                }
                if(!defendedFromThisAttack){
                    isDefendedByExtension = false; // If nobody from the extension defends the argument from that attack, then the argument isn't defended by the extension.
                    break;
                }
            }
        }

        return isDefendedByExtension;
    }

}

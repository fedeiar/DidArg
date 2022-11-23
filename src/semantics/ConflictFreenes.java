package semantics;
import java.util.Set;
import java.util.Map.Entry;

import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import extensions.ExtensionEnumerator;
import parser.AFDataStructures;
import util.Utils;

public class ConflictFreenes extends Semantics{

    public ConflictFreenes(AFDataStructures structures){
        super(structures);
        latexGenericFormula = "cf_{Ar, att} := \\underset{a \\in Ar}{\\bigwedge} ( ( v_a \\rightarrow \\underset{(b, a) \\in att}{\\bigwedge} \\neg v_b) ";
        latexFormulaHeader = "cf_{Ar, att} := ";

        explanation = "Despite conflict-freenes is not a semantics, but it's a basic requirement that all semantics in this application must satisfy. The formula is basically saying that if an argument 'a' is accepted, none other argument 'b' that attacks 'a' or is attacked by 'a' can be accepted.";
    }
    
    //TODO: esta bien?
    public IVec<IVecInt> calculateReduction(){
        VecInt clause;
        boolean argumentIsAttacked;
        
        for(Entry<Integer, String> argument : arguments.entrySet()){
            argumentIsAttacked = false;
            latexFormulaBody += "(v_"+arguments.get(argument.getKey())+" \\rightarrow (";
            for(int i = 0; i < attacks.size(); i++){
                IVecInt attack = attacks.get(i);
                if(attack.get(1) == argument.getKey()){ // El argumento es atacado
                    argumentIsAttacked = true;
                    clause = new VecInt();
                    clause.push(argument.getKey() * -1);
                    clause.push(attack.get(0) * -1);
                    clauses.push(clause);

                    latexFormulaBody += "\\neg v_"+arguments.get(attack.get(0))+" \\land ";
                }
            }

            if(!argumentIsAttacked){ // Si el argumento no es atacado por nadie, entonces puede recibir cualquier valor de verdad.
                clause = new VecInt();
                clause.push(argument.getKey());
                clause.push(argument.getKey() * -1);
                clauses.push(clause);
                latexFormulaBody += "\\top ";
            } else{
                latexFormulaBody = Utils.removeLastOperatorFromLatexFormula(latexFormulaBody, 6); // Eliminamos el ultimo and agregado cuando reconociamos ataques.
            }

            latexFormulaBody += ") ) \\land \\\\ ";
        }

        latexFormulaBody = Utils.removeLastOperatorFromLatexFormula(latexFormulaBody, 9);
        
        return clauses;
    }

    public Set<Set<String>> getExtensions() throws Exception{

        IVec<IVecInt> clauses = this.calculateReduction();
        ExtensionEnumerator extensionEnumerator = new ExtensionEnumerator(arguments, clauses);
        Set<Set<String>> extensions = extensionEnumerator.getExtensions();

        return extensions;
    }

}

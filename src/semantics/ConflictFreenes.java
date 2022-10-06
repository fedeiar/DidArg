package semantics;
import java.io.IOException;
import java.util.Map.Entry;

import javax.swing.text.Utilities;

import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import parser.AFDataStructures;
import parser.ParserException;
import util.Utils;

public class ConflictFreenes extends Semantics{

    public ConflictFreenes(AFDataStructures structures){
        super(structures);
        //TODO: latexGenericFormula
        latexFormulaHeader = "cf_{Ar, att} := ";
    }
    
    //TODO: esta bien?
    public IVec<IVecInt> calculateReduction(){
        VecInt clause;
        boolean argumentIsAttacked;
        
        for(Entry<Integer, String> argument : arguments.entrySet()){
            argumentIsAttacked = false;
            latexFormulaBody += "(V_"+arguments.get(argument.getKey())+" \\rightarrow (";
            for(int i = 0; i < attacks.size(); i++){
                IVecInt attack = attacks.get(i);
                if(attack.get(1) == argument.getKey()){ // El argumento es atacado
                    argumentIsAttacked = true;
                    clause = new VecInt();
                    clause.push(argument.getKey() * -1);
                    clause.push(attack.get(0) * -1);
                    clauses.push(clause);

                    latexFormulaBody += "\\neg V_"+arguments.get(attack.get(0))+" \\land ";
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

}

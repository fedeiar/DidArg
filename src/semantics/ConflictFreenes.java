package semantics;
import java.io.IOException;
import java.util.Map.Entry;

import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import parser.AFDataStructures;
import parser.ParserException;

public class ConflictFreenes extends Semantic{

    public ConflictFreenes(AFDataStructures structures){
        super(structures);
    }
    
    //TODO: esta bien?
    public IVec<IVecInt> calculateReduction() throws IOException, ParserException{
        VecInt clause;
        boolean argumentIsAttacked;
        for(Entry<Integer, String> argument : arguments.entrySet()){
            argumentIsAttacked = false;
            
            for(int i = 0; i < attacks.size(); i++){
                IVecInt attack = attacks.get(i);
                if(attack.get(1) == argument.getKey()){ // El argumento es atacado
                    argumentIsAttacked = true;
                    clause = new VecInt();
                    clause.push(argument.getKey() * -1);
                    clause.push(attack.get(0) * -1);
                    clauses.push(clause);

                    latexFormula += "(V_"+arguments.get(attack.get(1))+" \\rightarrow \\neg V_"+arguments.get(attack.get(0))+") \\land \\\\";
                }
            }

            if(!argumentIsAttacked){ // Si el argumento no es atacado por nadie, entonces puede recibir cualquier valor de verdad.
                clause = new VecInt();
                clause.push(argument.getKey());
                clause.push(argument.getKey() * -1);
                clauses.push(clause);
                latexFormula += "(V_"+argument.getValue()+" \\rightarrow \\top) \\land \\\\";
            }
        }

        final int LAST_AND_LONGITUDE = 8;
        latexFormula = latexFormula.substring(0, latexFormula.length() - LAST_AND_LONGITUDE);
        
        return clauses;
    }

}

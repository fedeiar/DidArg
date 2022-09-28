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
    
    //TODO: puede que sea incorrecto? ya que si hay un nodo que no recibe ataques, no estaría siendo considerado.
    public IVec<IVecInt> calculateReduction() throws IOException, ParserException{
        
        for(int i = 0; i < attacks.size(); i++){

            /* for(Entry<Integer, String> argument : arguments.entrySet()){

            } */


            IVecInt attack = attacks.get(i);
            VecInt clause = new VecInt();
            clause.push(attack.get(0) * -1);
            clause.push(attack.get(1) * -1);
            clauses.push(clause);

            if(i < attacks.size() - 1){
                latexFormula += "(V_"+arguments.get(attack.get(0))+" \\rightarrow \\neg V_"+arguments.get(attack.get(1))+") \\land \\\\";
            } else{
                latexFormula += "(V_"+arguments.get(attack.get(0))+" \\rightarrow \\neg V_"+arguments.get(attack.get(1))+")";
            }
        }
        return clauses;
    }

}

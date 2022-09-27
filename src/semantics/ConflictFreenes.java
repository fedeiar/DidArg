package semantics;
import java.io.IOException;
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
            IVecInt attack = attacks.get(i);
            int[] array = {attack.get(0) * -1, attack.get(1) * -1};
            clauses.push(new VecInt(array));

            if(i < attacks.size() - 1){
                latexFormula += "(V_"+arguments.get(attack.get(0))+" \\rightarrow \\neg V_"+arguments.get(attack.get(1))+") \\land \\\\";
            } else{
                latexFormula += "(V_"+arguments.get(attack.get(0))+" \\rightarrow \\neg V_"+arguments.get(attack.get(1))+")";
            }
        }
        return clauses;
    }

}

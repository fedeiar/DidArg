package semantics;

import java.io.IOException;
import java.util.Map.Entry;

import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import parser.AFDataStructures;
import parser.ParserException;

public class Admisibility extends Semantic{
    

    protected ConflictFreenes conflictFreenes;

    public Admisibility(AFDataStructures structures){
        super(structures);
        conflictFreenes = new ConflictFreenes(structures);
    }

    public IVec<IVecInt> calculateReduction() throws IOException, ParserException{
        clauses = conflictFreenes.calculateReduction();
        latexFormula = conflictFreenes.getLatexFormula();

        //TODO: esta bien iterar por cada argumento? o podemos hacer algo como hicimos en ConflictFreenes. si quiero que la formula quede tal cual como en HOFA, entonces creo que hay que iterar por cada argumento y corregir ConflictFreenes
        for(Entry<Integer, String> argument : arguments.entrySet()){
            for(int i = 0; i < attacks.size(); i++){
                IVecInt attack = attacks.get(i);
                if(attack.get(1) == argument.getKey()){ // significa que el argumento es atacado
                    int attackerOfArgument = attack.get(0);

                }
            }
        }
    }
}

package semantics;

import java.io.IOException;
import java.util.Map.Entry;

import org.sat4j.core.VecInt;
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
        //latexFormula = conflictFreenes.getLatexFormula();
        VecInt clause;
        boolean argumentIsAttacked;
        //TODO: esta bien iterar por cada argumento? o podemos hacer algo como hicimos en ConflictFreenes. si quiero que la formula quede tal cual como en HOFA, entonces creo que hay que iterar por cada argumento y corregir ConflictFreenes
        for(Entry<Integer, String> argument : arguments.entrySet()){
            //clause = new VecInt();
            //clause.push(argument.getKey() * -1);
            argumentIsAttacked = false;
            for(int i = 0; i < attacks.size(); i++){
                IVecInt attack1 = attacks.get(i);
                if(attack1.get(1) == argument.getKey()){ // significa que el argumento es atacado
                    clause = new VecInt();
                    clause.push(argument.getKey() * -1);
                    argumentIsAttacked = true;
                    int attackerOfArgument = attack1.get(0);
                    for(int j = 0; i < attacks.size(); i++){
                        IVecInt attack2 = attacks.get(j);
                        if(attack2.get(1) == attackerOfArgument){ // significa que el argumento es defendido por attack2.get(0)
                            clause.push(attack2.get(0)); // agregamos al defensor del argumento
                        }
                    }
                    clauses.push(clause); // tenemos una clausula para un atacante.
                }
            }
            if(!argumentIsAttacked){ // si el argumento no es atacado por nadie, entonces puede recibir cualquier valor de verdad.
                clause = new VecInt();
                clause.push(argument.getKey() * -1);
                clause.push(argument.getKey());
                clauses.push(clause);
            }
            //clauses.push(clause);
        }

        return clauses;
    }
}

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

    //TODO: esta bien?
    public IVec<IVecInt> calculateReduction() throws IOException, ParserException{
        clauses = conflictFreenes.calculateReduction();
        latexFormula = conflictFreenes.getLatexFormula();
        latexFormula += "\\land \\\\"; // Agregamos el and y salto de linea que sacamos en ConflictFreenes
        VecInt clause;
        boolean argumentIsAttacked;
        boolean argumentIsDefendedFromAttack;
        
        for(Entry<Integer, String> argument : arguments.entrySet()){
            latexFormula += "(V_"+argument.getValue()+" \\rightarrow ( ";
            argumentIsAttacked = false;
            for(int i = 0; i < attacks.size(); i++){ // buscamos los atacantes del argumento
                IVecInt attack1 = attacks.get(i);
                if(attack1.get(1) == argument.getKey()){ // significa que el argumento es atacado
                    latexFormula += "( ";
                    clause = new VecInt();
                    clause.push(argument.getKey() * -1);
                    argumentIsAttacked = true;
                    int attackerOfArgument = attack1.get(0);
                    argumentIsDefendedFromAttack = false;
                    for(int j = 0; j < attacks.size(); j++){ // buscamos los defensores del argumento
                        IVecInt attack2 = attacks.get(j);
                        if(attack2.get(1) == attackerOfArgument){ // significa que el argumento es defendido de ese ataque por attack2.get(0)
                            argumentIsDefendedFromAttack = true;
                            clause.push(attack2.get(0)); // agregamos al defensor del argumento
                            latexFormula += "V_"+arguments.get(attack2.get(0))+" \\lor ";
                        }
                    }
                    if(!argumentIsDefendedFromAttack){
                        latexFormula += "\\bot ";
                    }
                    latexFormula += ") \\land ";
                    clauses.push(clause); // tenemos una clausula para un atacante.
                }
            }
            if(!argumentIsAttacked){ // si el argumento no es atacado por nadie, entonces puede recibir cualquier valor de verdad.
                clause = new VecInt();
                clause.push(argument.getKey() * -1);
                clause.push(argument.getKey());
                clauses.push(clause);
                latexFormula += "\\top ";
            }
            latexFormula += ") ) \\land \\\\ ";
        }

        return clauses;
    }
}

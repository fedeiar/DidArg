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

        latexGenericFormula = "comp_{Ar, att} := \\underset{a \\in Ar}{\\bigwedge} ( ( v_a \\rightarrow \\underset{(b, a) \\in att}{\\bigwedge} \\neg v_b) \\land (v_a \\leftrightarrow \\underset{(b, a) \\in att}{\\bigwedge} (\\underset{(c, b) \\in att}{\\bigvee} v_c)))"; // TODO
        latexFormulaHeader = "comp_{Ar, att} := ";
        
        explanation = "In adittion to having to be admissible, a complete extension must also include every argument it defends. The formula is very similar to the admissible one, but while the admissibility formula allows us to accept an argument if it is defended by the extension (implication relation in the formula), the complete formula forces us to accept an argument if it is defended by the extension (if and only if relation in the formula).";
    }

    //TODO: esta bien? la reduccion que calcula es a la fbf admisible, pero tuve que copiar y pegar para cambiar el rightarrow.
    public IVec<IVecInt> calculateReduction() {
        clauses = conflictFreenes.calculateReduction();
        latexFormulaBody = conflictFreenes.getLatexFormulaBody();
        latexFormulaBody += "\\land \\\\"; // Agregamos el and y salto de linea que sacamos en ConflictFreenes
        VecInt clause;
        boolean argumentIsAttacked;
        boolean argumentIsDefendedFromAttack;
        
        for(Entry<Integer, String> argument : arguments.entrySet()){
            latexFormulaBody += "(v_"+argument.getValue()+" \\leftrightarrow ( ";
            argumentIsAttacked = false;
            for(int i = 0; i < attacks.size(); i++){ // buscamos los atacantes del argumento
                IVecInt attack1 = attacks.get(i);
                if(attack1.get(1) == argument.getKey()){ // significa que el argumento es atacado
                    latexFormulaBody += "( ";
                    clause = new VecInt();
                    clause.push(argument.getKey() * -1); // agregamos al argumento negado por la regla de equivalencia con la implicacion
                    argumentIsAttacked = true;
                    int attackerOfArgument = attack1.get(0);
                    argumentIsDefendedFromAttack = false;
                    for(int j = 0; j < attacks.size(); j++){ // buscamos los defensores del argumento
                        IVecInt attack2 = attacks.get(j);
                        if(attack2.get(1) == attackerOfArgument){ // significa que el argumento es defendido de ese ataque por attack2.get(0)
                            argumentIsDefendedFromAttack = true;
                            clause.push(attack2.get(0)); // agregamos al defensor del argumento
                            latexFormulaBody += "v_"+arguments.get(attack2.get(0))+" \\lor ";
                        }
                    }
                    if(!argumentIsDefendedFromAttack){
                        latexFormulaBody += "\\bot ";
                    } else{
                        latexFormulaBody = Utils.removeLastOperatorFromLatexFormula(latexFormulaBody, 5); // Eliminamos el ultimo or agregado cuando reconociamos defensores.
                    }
                    latexFormulaBody += ") \\land ";
                    clauses.push(clause); // tenemos una clausula para un atacante.
                }
            }
            if(!argumentIsAttacked){ // si el argumento no es atacado por nadie, entonces puede recibir cualquier valor de verdad.
                clause = new VecInt();
                clause.push(argument.getKey() * -1);
                clause.push(argument.getKey());
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
            if(attack1.get(1) == argument.getKey()){ // Si el argumento es atacado, vemos si alguien de la extension lo defiende.
                int attacker = attack1.get(0);
                boolean defendedFromThisAttack = false;
                for(int j = 0; j < attacks.size(); j++){
                    IVecInt attack2 = attacks.get(j);
                    if(attack2.get(1) == attacker && extension.contains(arguments.get(attack2.get(0))) ){ // Si alguien de la extension ataca al atacante, entonces defiende al argumento de ese ataque.
                        defendedFromThisAttack = true;
                        break;
                    }
                }
                if(!defendedFromThisAttack){
                    isDefendedByExtension = false; // Si nadie de la extension lo defiende de ese ataque, entonces el argumento no es defendido por la extension.
                    break;
                }
            }
        }

        return isDefendedByExtension;
    }

}
